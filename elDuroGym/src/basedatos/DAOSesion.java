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
    
    //Función para obtener una clase aula por su nombre
    public Aula obtenerAulaPorNombre(String nombre) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Aula aula = null;

    try {
        con = this.getConexion();

        // Consulta SQL para obtener un aula por su nombre
        String consulta = "SELECT id_aula, nombre, aforo FROM Aula WHERE nombre = ?";
        pstmt = con.prepareStatement(consulta);
        pstmt.setString(1, nombre);

        rs = pstmt.executeQuery();

        if (rs.next()) {
            // Construir el objeto Aula con los datos obtenidos de la consulta
            aula = new Aula(
                rs.getInt("id_aula"),
                rs.getString("nombre"),
                rs.getInt("aforo")
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

    public int obtenerSiguienteIdReserva() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int siguienteId = 1; // Valor por defecto si no hay registros en la tabla

        try {
            con = this.getConexion();

            // Consulta SQL para obtener el máximo id_reserva de la tabla de sesiones
            String consulta = "SELECT MAX(id_reserva) AS max_id FROM Sesion";
            pstmt = con.prepareStatement(consulta);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Obtener el máximo id_reserva y sumarle 1 para obtener el siguiente id
                siguienteId = rs.getInt("max_id") + 1;
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
        return siguienteId;
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

    public boolean idGrupoRelacionadoConProfesor(int idGrupo, int idProfesor) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = this.getConexion();

            // Consulta SQL para verificar si la relación existe en la tabla grupo_tiene_profesor
            String consulta = "SELECT COUNT(*) FROM grupo_tiene_profesor WHERE id_grupo = ? AND id_profesor = ?";
            pstmt = con.prepareStatement(consulta);
            pstmt.setInt(1, idGrupo);
            pstmt.setInt(2, idProfesor);

            rs = pstmt.executeQuery();

            // Si se encuentra al menos una fila, significa que la relación existe
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
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
        return false; // La relación no existe o ha ocurrido un error
    }

}
