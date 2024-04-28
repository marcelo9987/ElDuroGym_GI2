package basedatos;

import aplicacion.Actividad;
import aplicacion.FachadaAplicacion;
import aplicacion.Grupo;
import aplicacion.Profesor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DAOGrupos extends AbstractDAO {
    private java.sql.Connection conexion;
    private FachadaAplicacion fa;

    public DAOGrupos(java.sql.Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    //Inserta un grupo en la base de datos
    public void insertarGrupo(Grupo g) {
        Connection con = null;
        PreparedStatement stmInsertar = null;

        try {
            con = this.getConexion();
            // TODO: No se puede insertar el id del grupo, hay que recibirlo de la base de datos
            stmInsertar = con.prepareStatement("INSERT INTO Grupo (id_grupo, id_actividad) VALUES (?, ?)");
            stmInsertar.setInt(1, g.getIdGrupo());
            stmInsertar.setInt(2, g.getActividad().getIdActividad());
            stmInsertar.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (stmInsertar != null) {
                    stmInsertar.close();
                }
            } catch (SQLException e) {
                System.out.println(IMPOSIBLE_CERRAR_CURSORES);
            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.out.println(IMPOSIBLE_CERRAR_CONEXION);
//                }
//            }
        }
    }

    public List<Grupo> obtenerGrupos() {
        java.sql.PreparedStatement stmGrupos = null;
        java.sql.ResultSet rsGrupos = null;
        Connection con;
        List<Grupo> grupos = new java.util.ArrayList<>();
        con = this.getConexion();
        String consulta = "SELECT "
                + "   * "
                + "FROM "
                + "   Grupo g LEFT JOIN actividad a "
                + "       ON g.id_actividad = a.id_actividad "
                + "ORDER BY g.id_grupo";
        try {
            stmGrupos = con.prepareStatement(consulta);
            rsGrupos = stmGrupos.executeQuery();
            while (rsGrupos.next()) {
                grupos.add(
                        new Grupo(
                                rsGrupos.getInt("id_grupo"),
                                new Actividad(
                                        rsGrupos.getInt("id_actividad")
                                        , rsGrupos.getString("nombre")
                                        , rsGrupos.getString("descripcion")
                                        , rsGrupos.getString("tipo")
                                )
                        )
                );

            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmGrupos != null) {
                    stmGrupos.close();
                }
                if (rsGrupos != null) {
                    rsGrupos.close();
                }
            } catch (SQLException e) {
                System.out.println(IMPOSIBLE_CERRAR_CURSORES);
            }
        }
        return grupos;
    }

    //Modifica la actividad a realizar de un determinado grupo
    public void modificarGrupo(Grupo grupo, Actividad actividad) {
        Connection con = null;
        PreparedStatement stmModificar = null;

        try {
            con = this.getConexion();
            stmModificar = con.prepareStatement("UPDATE Grupo SET id_actividad = ? WHERE id_grupo = ?");
            stmModificar.setInt(1, actividad.getIdActividad());
            stmModificar.setInt(2, grupo.getIdGrupo());
            stmModificar.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (stmModificar != null) {
                    stmModificar.close();
                }
            } catch (SQLException e) {
                System.out.println(IMPOSIBLE_CERRAR_CURSORES);
            }
        }
    }

    //Elimina un grupo según su identificador
    public void eliminarGrupo(int idGrupo) {
        Connection con = null;
        PreparedStatement stmEliminar = null;

        try {
            con = this.getConexion();
            stmEliminar = con.prepareStatement("DELETE FROM Grupo WHERE id_grupo = ?");
            stmEliminar.setInt(1, idGrupo);
            stmEliminar.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (stmEliminar != null) {
                    stmEliminar.close();
                }
            } catch (SQLException e) {
                System.out.println(IMPOSIBLE_CERRAR_CURSORES);
            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.out.println(IMPOSIBLE_CERRAR_CONEXION);
//                }
//            }
        }
    }


    public void cambiarProfesorGrupo(Grupo grupo, Profesor profesor) {
        Connection con = null;
        PreparedStatement stmModificar = null;

        try {
            con = this.getConexion();
            stmModificar = con.prepareStatement("UPDATE grupo_tiene_profesor SET id_profesor = ? WHERE id_grupo = ?");
            stmModificar.setInt(1, Integer.parseInt(profesor.getIdUsuario()));
            stmModificar.setInt(2, grupo.getIdGrupo());
            stmModificar.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (stmModificar != null) {
                    stmModificar.close();
                }
            } catch (SQLException e) {
                System.out.println(IMPOSIBLE_CERRAR_CURSORES);
            }
        }
    }

    public Integer crearGrupo(int id_actividad) {
        Connection con = null;
        PreparedStatement stmInsertar = null;
        java.sql.ResultSet rsGrupo = null;

        Integer id_grupo = null;

        try {
            con = this.getConexion();
            stmInsertar = con.prepareStatement("INSERT INTO Grupo (id_grupo, id_actividad) VALUES (DEFAULT, ?) RETURNING id_grupo");
            stmInsertar.setInt(1, id_actividad);
//            stmInsertar.executeUpdate();
            rsGrupo = stmInsertar.executeQuery();

            if (rsGrupo.next()) {
                id_grupo = rsGrupo.getInt(1);
            } else {
                id_grupo = null;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (stmInsertar != null) {
                    stmInsertar.close();
                }
            } catch (SQLException e) {
                System.out.println(IMPOSIBLE_CERRAR_CURSORES);
            }
        }
        return id_grupo;

    }


    public void borrarGrupo(Grupo grupo) {
        Connection con = null;
        PreparedStatement stmEliminar = null;

        try {
            con = this.getConexion();
            stmEliminar = con.prepareStatement("DELETE FROM Grupo WHERE id_grupo = ?");
            stmEliminar.setInt(1, grupo.getIdGrupo());
            stmEliminar.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (stmEliminar != null) {
                    stmEliminar.close();
                }
            } catch (SQLException e) {
                System.out.println(IMPOSIBLE_CERRAR_CURSORES);
            }
        }
    }
}


