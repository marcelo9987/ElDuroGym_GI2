package basedatos;

import aplicacion.*;
import aplicacion.FachadaAplicacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author basesdatos
 */
public final class DAOProfesor extends AbstractDAO {
    public DAOProfesor(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public java.util.List<Integer> consultarGrupos(int idProfesor) {
        java.util.List<Integer> gruposResultado = new java.util.ArrayList<>();
        Connection con = null;
        PreparedStatement stmGrupos = null;
        ResultSet rsGrupos = null;

        try {
            con = this.getConexion();
            stmGrupos = con.prepareStatement("SELECT id_grupo FROM grupo_tiene_profesor WHERE id_profesor = ?");
            stmGrupos.setInt(1, idProfesor);
            rsGrupos = stmGrupos.executeQuery();

            while (rsGrupos.next()) {
                int idGrupo = rsGrupos.getInt("id_grupo");
                gruposResultado.add(idGrupo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (rsGrupos != null) {
                    rsGrupos.close();
                }
                if (stmGrupos != null) {
                    stmGrupos.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.out.println("Imposible cerrar conexión");
//                }
//            }
        }
        return gruposResultado;
    }

    @Deprecated
    public java.util.List<Sesion> consultarSesiones(Integer id_profesor) {
        java.util.List<Sesion> resultado = new java.util.ArrayList<>();
        Connection con = null;
        PreparedStatement stmSesiones = null;
        ResultSet rsSesiones = null;

        java.util.List<Integer> id_grupos = new java.util.ArrayList<>();
        try {
            // Obtener los identificadores de los grupos asociados al profesor
            id_grupos = consultarGrupos(id_profesor);

            // Realizar consulta para obtener las sesiones asociadas a los grupos
            con = this.getConexion();
            for (Integer idGrupo : id_grupos) {
                stmSesiones = con.prepareStatement("SELECT s.id_reserva\n" +
                        "     , s.fecha_hora_inicio\n" +
                        "     , s.fecha_hora_fin\n" +
                        "     , s.descripcion as descripcion_sesion\n" +
                        "     , s.id_aula\n" +
                        "     , a.nombre as nombre_aula\n" +
                        "     , a.aforo as aforo_aula\n" +
                        "     , s.id_grupo\n" +
                        "     , g.id_actividad\n" +
                        "     , ac.nombre as nombre_actividad\n" +
                        "     , ac.descripcion as descripcion_actividad\n" +
                        "     , ac.tipo as tipo_actividad\n" +
                        "\n" +
                        "FROM sesion as s\n" +
                        "         left join aula a\n" +
                        "                   ON s.id_aula = a.id_aula\n" +
                        "         left join grupo g\n" +
                        "                   ON s.id_grupo = g.id_grupo\n" +
                        "         left join actividad ac\n" +
                        "                   on g.id_actividad = ac.id_actividad\n" +
                        "\n" +
                        "WHERE s.id_grupo = ?");
                stmSesiones.setInt(1, idGrupo);
                rsSesiones = stmSesiones.executeQuery();

                while (rsSesiones.next()) {
                    //Creo el objeto aula que se añadirá a al sesión
                    Aula aula = new Aula(
                            rsSesiones.getInt("id_aula")
                            , rsSesiones.getString("nombre_aula")
                            , rsSesiones.getInt("aforo_aula")
                    );

                    // Crear objeto Actividad que añadiré al aún no creado grupo
                    Actividad actividad = new Actividad(
                            rsSesiones.getInt("id_actividad")
                            , rsSesiones.getString("nombre_actividad")
                            , rsSesiones.getString("descripcion_actividad")
                            , rsSesiones.getString("tipo_actividad")
                    );

                    // Crear objeto Grupo y añadirle la actividad
                    Grupo grupo = new Grupo(
                            rsSesiones.getInt("id_grupo")
                            , actividad
                    );


                    // Crear objeto Sesion y añadirlo a la lista resultado
                    Sesion sesionActual = new Sesion(
//                            rsSesiones.getInt("id_aula")
//                            , rsSesiones.getInt("id_grupo")
//                            , rsSesiones.getInt("id_reserva")
                            aula
                            , grupo
                            , rsSesiones.getInt("id_reserva")
                            , rsSesiones.getTimestamp("fecha_hora_inicio").toLocalDateTime()
                            , rsSesiones.getTimestamp("fecha_hora_fin").toLocalDateTime()
                            , rsSesiones.getString("descripcion")
                    );
                    resultado.add(sesionActual);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (rsSesiones != null) {
                    rsSesiones.close();
                }
                if (stmSesiones != null) {
                    stmSesiones.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.out.println("Imposible cerrar conexión");
//                }
//            }
        }
        return resultado;
    }

    public List<SesionProfesor> obtenerSesionesProfesor(String nickname, String nombreActividad, String nombreAula, String fecha, String hora, String descripcion) {
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
//                            "CONCAT(TO_CHAR(EXTRACT(HOUR FROM S.Fecha_hora_inicio), 'FM00'), ':', TO_CHAR(EXTRACT(MINUTE FROM S.fecha_hora_inicio), 'FM00')) as hora, "+
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
            if (!fecha.isEmpty()) {
                consulta += "AND DATE(S.fecha_hora_inicio) = ? ";
            }
            if (!hora.isEmpty()) {
                consulta //+= "AND TO_CHAR(EXTRACT(HOUR FROM S.Fecha_hora_inicio), 'FM00') || ':' || TO_CHAR(EXTRACT(MINUTE FROM S.Fecha_hora_inicio), 'FM00') = ? ";
                        += "AND ? between S.fecha_hora_inicio::time and S.fecha_hora_fin::time";
            }
            if (!descripcion.isEmpty()) {
                consulta += "AND AC.descripcion = ? ";
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
            if (!fecha.isEmpty()) {
                // Suponiendo que 'fecha' está en formato "yyyy-MM-dd"
                Date fechaDate = Date.valueOf(fecha);
                stmSesionesProfesor.setDate(index++, fechaDate);
            }
            if (!hora.isEmpty()) {
                //ver si hay dos : en la hora
                if (hora.split(":").length < 3) {
                    hora += ":00";
                }
                stmSesionesProfesor.setTime(index++, Time.valueOf(hora));
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

    public int obtenerIdProfesorPorNombre(String nombreProfesor) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int idProfesor = -1; // Valor predeterminado en caso de que no se encuentre el profesor

        try {
            con = this.getConexion();

            // Consulta SQL para obtener el id_profesor por su nombre
            String consulta = "SELECT id_persona FROM persona WHERE nickname = ?";
            pstmt = con.prepareStatement(consulta);
            pstmt.setString(1, nombreProfesor);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Obtener el id_profesor de la consulta
                idProfesor = rs.getInt("id_persona");
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
        return idProfesor;
    }

    public List obtenerProfesores() {
        Connection con = null;

        ResultSet rsProfesores = null;

        List<Profesor> profesores = new ArrayList<>();
//        Statement stmProfesores = null;
        PreparedStatement stmProfesores = null;
        try {
            con = this.getConexion();
//            stmProfesores = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//            rsProfesores = stmProfesores.executeQuery("SELECT * FROM Profesor left join Persona on Profesor.id_profesor = Persona.id_persona");
            String consulta = "SELECT * FROM Profesor left join Persona on Profesor.id_profesor = Persona.id_persona ORDER BY Persona.nombre, Persona.id_persona";
            stmProfesores = con.prepareStatement(consulta);
            rsProfesores = stmProfesores.executeQuery();
            while (rsProfesores.next()) {


                Profesor profesor = new Profesor(
                        Integer.toString(rsProfesores.getInt("id_profesor"))
                        , rsProfesores.getString("contrasenha")
                        , rsProfesores.getString("nombre")
                        , rsProfesores.getString("domicilio")
                        , rsProfesores.getString("correo")
                        , rsProfesores.getString("dni")
                        , rsProfesores.getString("nickname")

                );
                profesores.add(profesor);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmProfesores != null) {
                    stmProfesores.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Imposible cerrar conexión");
//            }
        }
        return profesores;
    }

    public Profesor obtenerProfesorGrupo(int idGrupo) {
        Connection con = null;
        PreparedStatement stmProfesor = null;
        ResultSet rsProfesor = null;
        Profesor profesor = null;
        try {
            con = this.getConexion();
            stmProfesor = con.prepareStatement
                    (
                            "SELECT * " +
                                    "FROM " +
                                    "   Profesor LEFT JOIN persona " +
                                    "       ON Profesor.id_profesor = persona.id_persona" +
                                    " WHERE " +
                                    "   id_profesor = (" +
                                    "                       SELECT " +
                                    "                           id_profesor " +
                                    "                       FROM " +
                                    "                           grupo_tiene_profesor " +
                                    "                       WHERE " +
                                    "                           id_grupo = ?" +
                                    "                 )");
            stmProfesor.setInt(1, idGrupo);
            rsProfesor = stmProfesor.executeQuery();
            if (rsProfesor.next()) {
                profesor = new Profesor(
                        Integer.toString(rsProfesor.getInt("id_profesor"))
                        , rsProfesor.getString("contrasenha")
                        , rsProfesor.getString("nombre")
                        , rsProfesor.getString("domicilio")
                        , rsProfesor.getString("correo")
                        , rsProfesor.getString("dni")
                        , rsProfesor.getString("nickname")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (rsProfesor != null) {
                    rsProfesor.close();
                }
                if (stmProfesor != null) {
                    stmProfesor.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return profesor;
    }
}
