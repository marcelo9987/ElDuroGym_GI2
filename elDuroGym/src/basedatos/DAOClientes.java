package basedatos;

import aplicacion.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class DAOClientes extends AbstractDAO {
    public DAOClientes(Connection conexion, FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    public List<SesionCliente> obtenerSesionesCliente(String nickname, String nombreActividad, String nombreAula, String fecha, String hora) {
        Connection con;
        PreparedStatement stmSesionesCliente = null;
        ResultSet rsSesionesCliente;

        List<SesionCliente> resultado = new java.util.ArrayList<>();
        try {
            con = this.getConexion();
            int index = 1; // Índice inicial para los parámetros

            String consulta = "SELECT "
                    + " A.Nombre as nombre_aula"
                    + ", AC.Nombre as nombre_actividad"
                    + ", to_char(DATE(S.fecha_hora_inicio),'dd-mm-yyyy') as fecha"
                    + ", to_char(S.fecha_hora_inicio,'HH:SS') as hora "
                    + "FROM "
                    + "Sesion as S "
                    + "JOIN "
                    + "Grupo as G on S.id_grupo = G.id_grupo "
                    + "JOIN "
                    + "Grupo_tiene_cliente as GC on G.id_grupo = GC.id_grupo "
                    + "JOIN "
                    + "Cliente as C on GC.id_cliente = C.id_cliente "
                    + "JOIN "
                    + "Persona as U on C.id_cliente = U.id_persona "
                    + "JOIN "
                    + "Actividad as AC on G.id_actividad = AC.id_actividad "
                    + "JOIN "
                    + "Aula as A on S.id_aula = A.id_aula "
                    + "WHERE "
                    + "U.nickname = ? ";

            if (!nombreActividad.isBlank() || !nombreActividad.isEmpty()) {
                consulta += "AND AC.nombre = ? ";
            }
            if (!nombreAula.isBlank() || !nombreAula.isEmpty()) {
                consulta += "AND A.nombre = ? ";
            }
            if (!fecha.isBlank() || !fecha.isEmpty()) {
                consulta += "AND DATE(S.fecha_hora_inicio) = CAST(? AS DATE) ";
            }
            if (!hora.isBlank() || !hora.isEmpty()) {
                consulta += "AND TO_CHAR(EXTRACT(HOUR FROM S.Fecha_hora_inicio), 'FM00') || ':' || TO_CHAR(EXTRACT(MINUTE FROM S.Fecha_hora_inicio), 'FM00') = ? ";
            }


            stmSesionesCliente = con.prepareStatement(consulta);


            stmSesionesCliente.setString(index++, nickname);


            if (!nombreActividad.isBlank() || !nombreActividad.isEmpty()) {
                stmSesionesCliente.setString(index++, nombreActividad);
            }

            if (!nombreAula.isBlank() || !nombreAula.isEmpty()) {
                stmSesionesCliente.setString(index++, nombreAula);
            }
            if (!fecha.isBlank() || !fecha.isEmpty()) {
                stmSesionesCliente.setString(index++, fecha);
            }
            if (!hora.isBlank() || !hora.isEmpty()) {
                stmSesionesCliente.setString(index++, hora);
            }


            rsSesionesCliente = stmSesionesCliente.executeQuery();

            while (rsSesionesCliente.next()) {

                SesionCliente sesion = new SesionCliente(
                        rsSesionesCliente.getString("nombre_actividad"),
                        rsSesionesCliente.getString("nombre_aula"),
                        rsSesionesCliente.getString("fecha"),
                        rsSesionesCliente.getString("hora")
                );

                resultado.add(sesion);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmSesionesCliente.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public List<Cliente> obtenerClientes() {
        List<Cliente> resultado = new ArrayList<>();
        Cliente clienteActual;
        Connection con;
        PreparedStatement stmCliente = null;
        ResultSet rsClientes;

        boolean heHechoAlgo = false;

        con = this.getConexion();

        try {
            stmCliente = con.prepareStatement("select "
                    + "id_cliente "
                    + ", p.contrasenha "
                    + ", p.nombre "
                    + ", p.domicilio "
                    + ", p.correo "
                    + ", p.dni "
                    + ", p.nickname "
                    + "from cliente c left join persona p on c.id_cliente=p.id_persona");

            rsClientes = stmCliente.executeQuery();
            while (rsClientes.next()) {
                clienteActual = new Cliente(
                        rsClientes.getString("id_cliente")
                        , rsClientes.getString("contrasenha")
                        , rsClientes.getString("nombre")
                        , rsClientes.getString("domicilio")
                        , rsClientes.getString("correo")
                        , rsClientes.getString("dni")
                        , rsClientes.getString("nickname"));
                resultado.add(clienteActual);
            }

            heHechoAlgo = true;

        } catch (SQLException e) {
            System.out.println("ERRCRIT" + e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (!heHechoAlgo) {
                    System.out.println("No he hecho nada");
                } else {
                    if (stmCliente != null) {
                        stmCliente.close();
                    }
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
    }


    public List<Cliente> obtenerClientesGrupo(Grupo grupo) {
        Connection con;
        PreparedStatement stmClientes = null;
        ResultSet rsClientes;

        List<Cliente> resultado = new java.util.ArrayList<>();
        try {
            con = this.getConexion();
            int index = 1; // Índice inicial para los parámetros

            String consulta = "SELECT "
                    + " C.id_cliente"
                    + ", P.contrasenha"
                    + ", P.nombre"
                    + ", P.domicilio"
                    + ", P.correo"
                    + ", P.dni"
                    + ", P.nickname"
                    + " FROM "
                    + " Cliente as C "
                    + " JOIN "
                    + " Persona as P on C.id_cliente = P.id_persona "
                    + " JOIN "
                    + " Grupo_tiene_cliente as GC on C.id_cliente = GC.id_cliente "
                    + " JOIN "
                    + " Grupo as G on GC.id_grupo = G.id_grupo "
                    + " WHERE "
                    + " G.id_grupo = ? ";

            stmClientes = con.prepareStatement(consulta);
            stmClientes.setInt(index++, grupo.getIdGrupo());

            rsClientes = stmClientes.executeQuery();

            while (rsClientes.next()) {
                Cliente cliente = new Cliente(
                        rsClientes.getString("id_cliente")
                        , rsClientes.getString("contrasenha")
                        , rsClientes.getString("nombre")
                        , rsClientes.getString("domicilio")
                        , rsClientes.getString("correo")
                        , rsClientes.getString("dni")
                        , rsClientes.getString("nickname")
                );

                resultado.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmClientes != null) {
                    stmClientes.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return resultado;
    }

    public void borrarClientesGrupo(Grupo grupo)
    {
        Connection con;
        PreparedStatement stmBorrarClientes = null;

        con = this.getConexion();

        try {
            stmBorrarClientes = con.prepareStatement("DELETE FROM Grupo_tiene_cliente WHERE id_grupo = ?");
            stmBorrarClientes.setInt(1, grupo.getIdGrupo());
            stmBorrarClientes.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmBorrarClientes != null) {
                    stmBorrarClientes.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public void setAlumnoGrupo(Grupo grupo, Cliente c)
    {
        Connection con;
        PreparedStatement stmSetAlumno = null;

        con = this.getConexion();

        try {
            stmSetAlumno = con.prepareStatement("INSERT INTO Grupo_tiene_cliente VALUES (?, ?)");
            stmSetAlumno.setInt(1, grupo.getIdGrupo());
            stmSetAlumno.setInt(2, Integer.parseInt(c.getIdUsuario()));
            stmSetAlumno.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmSetAlumno != null) {
                    stmSetAlumno.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }

    public boolean alumnoTieneGrupo(Cliente c, Grupo g) {
        Connection con;
        PreparedStatement stmAlumnoTieneGrupo = null;
        ResultSet rsAlumnoTieneGrupo;

        boolean resultado = false;

        con = this.getConexion();

        try {
            stmAlumnoTieneGrupo = con.prepareStatement("SELECT count(*) FROM Grupo_tiene_cliente WHERE id_grupo = ? AND id_cliente = ?");
            stmAlumnoTieneGrupo.setInt(1, g.getIdGrupo());
            stmAlumnoTieneGrupo.setInt(2, Integer.parseInt(c.getIdUsuario()));

            rsAlumnoTieneGrupo = stmAlumnoTieneGrupo.executeQuery();

            if (rsAlumnoTieneGrupo.next()) {
                resultado = rsAlumnoTieneGrupo.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmAlumnoTieneGrupo != null) {
                    stmAlumnoTieneGrupo.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }

        return resultado;
    }

    public void borrarClientesGrupo(Grupo grupo, ArrayList<Cliente> arrayListAlumnosEliminar)
    {
        Connection con;
        PreparedStatement stmBorrarClientes = null;

        con = this.getConexion();

        try {
            stmBorrarClientes = con.prepareStatement("DELETE FROM Grupo_tiene_cliente WHERE id_grupo = ? AND id_cliente = ?");
            for (Cliente c : arrayListAlumnosEliminar) {
                stmBorrarClientes.setInt(1, grupo.getIdGrupo());
                stmBorrarClientes.setInt(2, Integer.parseInt(c.getIdUsuario()));
                stmBorrarClientes.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                if (stmBorrarClientes != null) {
                    stmBorrarClientes.close();
                }
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
}
