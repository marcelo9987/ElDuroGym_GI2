/*
 * The MIT License
 *
 * Copyright 2024 alumnogreibd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package basedatos;

import java.sql.Connection;

import aplicacion.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumnogreibd
 */
public class DAOSesion extends AbstractDAO {
    public DAOSesion(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }


    public Aula obtenerAulaPorNombre(String nombre) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Aula aula = null;

        try {
            con = this.getConexion();

            // Consulta SQL para obtener un aula por su nombre
            String consulta = "SELECT id_aula, aforo FROM Aula WHERE nombre = ?";
            pstmt = con.prepareStatement(consulta);
            pstmt.setString(1, nombre);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Construir el objeto Aula con los datos obtenidos de la consulta
                aula = new Aula(
                        rs.getInt("id_aula"),
                        nombre,
                        rs.getInt("aforo")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el aula por nombre: " + e.getMessage());
            // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores: " + e.getMessage());
            }
        }
        return aula;
    }

    public Actividad obtenerActividadPorId(int idActividad) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Actividad actividad = null;

        try {
            con = this.getConexion();

            // Consulta SQL para obtener una actividad por su identificador
            String consulta = "SELECT id_actividad, nombre, descripcion, tipo FROM Actividad WHERE id_actividad = ?";
            pstmt = con.prepareStatement(consulta);
            pstmt.setInt(1, idActividad);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Construir el objeto Actividad con los datos obtenidos de la consulta
                actividad = new Actividad(
                        rs.getInt("id_actividad"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("tipo")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return actividad;
    }

    public Grupo obtenerGrupoPorId(int idGrupo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Grupo grupo = null;

        try {
            con = this.getConexion();

            // Consulta SQL para obtener un grupo por su identificador
            String consulta = "SELECT id_grupo, id_actividad FROM Grupo WHERE id_grupo = ?";
            pstmt = con.prepareStatement(consulta);
            pstmt.setInt(1, idGrupo);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                Actividad actividad = obtenerActividadPorId(rs.getInt("id_actividad"));
                // Construir el objeto Grupo con los datos obtenidos de la consulta
                grupo = new Grupo(
                        rs.getInt("id_grupo"),
                        actividad
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return grupo;
    }

    public boolean haySesionesEnAula(int idAula, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean haySesiones = false;

        try {
            con = this.getConexion();

            // Consulta SQL para verificar si hay sesiones en un aula para el rango de fechas y horas especificado
            String consulta = "SELECT COUNT(*) AS count FROM Sesion WHERE id_aula = ? AND fecha_hora_inicio <= ? AND fecha_hora_fin >= ?";
            pstmt = con.prepareStatement(consulta);
            pstmt.setInt(1, idAula);
            pstmt.setTimestamp(2, Timestamp.valueOf(fechaHoraInicio));
            pstmt.setTimestamp(3, Timestamp.valueOf(fechaHoraFin));

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Si el resultado es mayor que 0, significa que hay sesiones en el aula para el rango de fechas y horas especificado
                haySesiones = rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return haySesiones;
    }

    public List<SesionProfesor> obtenerSesionesProfesorVentana(String nickname, String nombreActividad, String nombreAula, String descripcion) {
    Connection con = null;
    PreparedStatement stmSesionesProfesor = null;
    ResultSet rsSesionesProfesor = null;

    List<SesionProfesor> resultado = new ArrayList<>();
    try {
        con = this.getConexion();

        // Preparar la consulta SQL
        String consulta =
                "SELECT A.Nombre as nombre_aula, " +
                        " AC.Nombre as nombre_actividad, " +
                        "to_char(DATE(S.fecha_hora_inicio),'dd-mm-yyyy') as fecha, " +
                        "to_char(S.fecha_hora_inicio,'HH:SS') as hora, " +
                        "AC.descripcion " +
                        "FROM Sesion as S " +
                        "JOIN Grupo as G on S.id_grupo = G.id_grupo " +
                        "JOIN Grupo_tiene_profesor as GP on G.id_grupo = GP.id_grupo " +
                        "JOIN Profesor as PR on GP.id_profesor = PR.id_profesor " +
                        "JOIN Persona as P on PR.id_profesor = P.id_persona " +
                        "JOIN Actividad as AC on G.id_actividad = AC.id_actividad " +
                        "JOIN Aula as A on S.id_aula = A.id_aula  " +
                        "WHERE P.nickname = ? ";

        if (!nombreActividad.isEmpty()) {
            consulta += "AND AC.nombre = ? ";
        }
        if (!nombreAula.isEmpty()) {
            consulta += "AND A.nombre = ? ";
        }
        if (!descripcion.isEmpty()) {
            consulta += "AND S.descripcion = ? ";
        }

        stmSesionesProfesor = con.prepareStatement(consulta);

        // Asignar parámetros
        int index = 1;
        stmSesionesProfesor.setString(index++, nickname);
        if (!nombreActividad.isEmpty()) {
            stmSesionesProfesor.setString(index++, nombreActividad);
        }
        if (!nombreAula.isEmpty()) {
            stmSesionesProfesor.setString(index++, nombreAula);
        }
        if (!descripcion.isEmpty()) {
            stmSesionesProfesor.setString(index++, descripcion);
        }

        rsSesionesProfesor = stmSesionesProfesor.executeQuery();

        while (rsSesionesProfesor.next()) {
            SesionProfesor sesion = new SesionProfesor(
                    rsSesionesProfesor.getString("nombre_actividad"),
                    rsSesionesProfesor.getString("nombre_aula"),
                    rsSesionesProfesor.getString("fecha"),
                    rsSesionesProfesor.getString("hora"),
                    rsSesionesProfesor.getString("descripcion")
            );
            resultado.add(sesion);
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
    } finally {
        try {
            // Cerrar recursos
            if (rsSesionesProfesor != null) {
                rsSesionesProfesor.close();
            }
            if (stmSesionesProfesor != null) {
                stmSesionesProfesor.close();
            }
        } catch (SQLException e) {
            System.out.println("Imposible cerrar cursores");
        }
    }
    return resultado;
}

    public boolean crearSesionParaProfesor(int idAula, int idGrupo, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String descripcion) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean exito = false;

        try {
            con = this.getConexion();

            // Consulta SQL para insertar una nueva sesión
            String consulta = "INSERT INTO Sesion (id_aula, id_grupo, fecha_hora_inicio, fecha_hora_fin, descripcion) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(consulta);
            pstmt.setInt(1, idAula);
            pstmt.setInt(2, idGrupo);
            pstmt.setObject(3, fechaHoraInicio);
            pstmt.setObject(4, fechaHoraFin);
            pstmt.setString(5, descripcion);

            // Ejecutar la consulta y comprobar si se insertó correctamente
            int filasInsertadas = pstmt.executeUpdate();
            exito = filasInsertadas > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return exito;
    }

    public boolean insertarGrupoTieneProfesor(int idGrupo, int idProfesor) {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean exito = false;

        try {
            con = this.getConexion();

            // Consulta SQL para insertar un nuevo registro en grupo_tiene_profesor
            String consulta = "INSERT INTO Grupo_tiene_profesor (id_grupo, id_profesor) VALUES (?, ?)";
            pstmt = con.prepareStatement(consulta);
            pstmt.setInt(1, idGrupo);
            pstmt.setInt(2, idProfesor);

            // Ejecutar la consulta y comprobar si se insertó correctamente
            int filasInsertadas = pstmt.executeUpdate();
            exito = filasInsertadas > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return exito;
    }

    public boolean borrarSesionesDeProfesor(int idProfesor, int idGrupo) {
        Connection con = null;
        PreparedStatement pstmtSesion = null;
        PreparedStatement pstmtGrupoTieneProfesor = null;
        boolean exito = false;

        try {
            con = this.getConexion();

            // Consulta SQL para borrar las sesiones del profesor en el grupo especificado
            String consultaSesion = "DELETE FROM Sesion WHERE id_grupo = ? AND id_aula IN (SELECT id_aula FROM Grupo WHERE id_grupo = ?)";
            pstmtSesion = con.prepareStatement(consultaSesion);
            pstmtSesion.setInt(1, idGrupo);
            pstmtSesion.setInt(2, idGrupo);
            pstmtSesion.executeUpdate();

            // Consulta SQL para borrar la relación del profesor con el grupo
            String consultaGrupoTieneProfesor = "DELETE FROM Grupo_tiene_profesor WHERE id_grupo = ? AND id_profesor = ?";
            pstmtGrupoTieneProfesor = con.prepareStatement(consultaGrupoTieneProfesor);
            pstmtGrupoTieneProfesor.setInt(1, idGrupo);
            pstmtGrupoTieneProfesor.setInt(2, idProfesor);
            pstmtGrupoTieneProfesor.executeUpdate();

            // Si se llega a este punto sin excepciones, se considera exitosa la operación
            exito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (pstmtSesion != null) {
                    pstmtSesion.close();
                }
                if (pstmtGrupoTieneProfesor != null) {
                    pstmtGrupoTieneProfesor.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return exito;
    }

    public boolean grupoTieneSesiones(Grupo grupo) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean tieneSesiones = false;

        try {
            con = this.getConexion();

            // Consulta SQL para verificar si un grupo tiene sesiones
            String consulta = "SELECT COUNT(*) AS count FROM Sesion WHERE id_grupo = ?";
            pstmt = con.prepareStatement(consulta);
            pstmt.setInt(1, grupo.getIdGrupo());

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Si el resultado es mayor que 0, significa que el grupo tiene sesiones
                tieneSesiones = rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return tieneSesiones;
    }
}
