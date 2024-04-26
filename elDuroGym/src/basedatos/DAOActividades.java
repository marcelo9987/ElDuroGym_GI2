package basedatos;

import aplicacion.Actividad;
import aplicacion.FachadaAplicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DAOActividades extends AbstractDAO {
    FachadaAplicacion fa;

    public DAOActividades(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public List<Actividad> consultarActividades() {
        Connection conexion = this.getConexion();
        PreparedStatement stmActividades = null;
        ResultSet datosEntrada;
        List<Actividad> resultado = new java.util.ArrayList<>();

        String consulta =
                " SELECT *" +
                        " FROM actividad " +
                        "ORDER BY Nombre";
        try {
            stmActividades = conexion.prepareStatement(consulta);
            datosEntrada = stmActividades.executeQuery();
            while (datosEntrada.next()) {
                resultado.add(new Actividad(datosEntrada.getInt("id_actividad"), datosEntrada.getString("nombre"), datosEntrada.getString("descripcion"), datosEntrada.getString("tipo")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmActividades != null) {
                    stmActividades.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.getFachadaAplicacion().muestraExcepcion(IMPOSIBLE_CERRAR_CONEXION);
            }
        }

        return resultado;

    }

    public boolean ActividadTieneGrupos(Actividad actividad) {
        Connection conexion = this.getConexion();
        PreparedStatement stmActividades = null;
        ResultSet datosEntrada;
        boolean resultado = false;

        String consulta =
                " SELECT count(1)" +
                        " FROM grupo " +
                        " WHERE id_actividad = ? ";
        try {
            stmActividades = conexion.prepareStatement(consulta);
            stmActividades.setInt(1, actividad.getIdActividad());
            datosEntrada = stmActividades.executeQuery();
            if (datosEntrada.next()) {
                resultado = datosEntrada.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmActividades != null) {
                    stmActividades.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.getFachadaAplicacion().muestraExcepcion(IMPOSIBLE_CERRAR_CONEXION);
            }
        }

        return resultado;
    }

    public void eliminarActividad(Actividad actividad) {
        Connection conexion = this.getConexion();
        PreparedStatement stmActividades = null;

        String consulta =
                " DELETE" +
                        " FROM actividad " +
                        " WHERE id_actividad = ? ";
        try {
            stmActividades = conexion.prepareStatement(consulta);
            stmActividades.setInt(1, actividad.getIdActividad());
            stmActividades.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmActividades != null) {
                    stmActividades.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.getFachadaAplicacion().muestraExcepcion(IMPOSIBLE_CERRAR_CONEXION);
            }
        }
    }

    public void modificarActividad(Actividad actividad, String nombre, String descripcion, String tipo) {
        Connection conexion = this.getConexion();
        PreparedStatement stmActividades = null;

        String consulta =
                " UPDATE actividad" +
                        " SET nombre = ?, descripcion = ?, tipo = ?" +
                        " WHERE id_actividad = ? ";
        try {
            stmActividades = conexion.prepareStatement(consulta);
            stmActividades.setString(1, nombre);
            stmActividades.setString(2, descripcion);
            stmActividades.setString(3, tipo);
            stmActividades.setInt(4, actividad.getIdActividad());
            stmActividades.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmActividades != null) {
                    stmActividades.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.getFachadaAplicacion().muestraExcepcion(IMPOSIBLE_CERRAR_CONEXION);
            }
        }
    }
}
