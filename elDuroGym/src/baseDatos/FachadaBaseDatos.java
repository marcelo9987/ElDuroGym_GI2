/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baseDatos;

import aplicacion.*;
import aplicacion.FachadaAplicacion;
import aplicacion.Sesion;
import aplicacion.SesionCliente;
import aplicacion.SesionProfesor;
import aplicacion.Usuario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author alumnogreibd
 */
public class FachadaBaseDatos {
    private final FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private DAOUsuarios daoUsuarios;
    private DAOClientes daoClientes;
    private DAOProfesor daoProfesor;

    public FachadaBaseDatos (FachadaAplicacion fa){
        
        Properties configuracion = new Properties();
        this.fa=fa;
        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();
     

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion=java.sql.DriverManager.getConnection("jdbc:"+gestor+"://"+
                    configuracion.getProperty("servidor")+":"+
                    configuracion.getProperty("puerto")+"/"+
                    configuracion.getProperty("baseDatos"),
                    usuario);

        this.daoUsuarios = new DAOUsuarios(conexion,fa);

        this.daoClientes = new DAOClientes(conexion,fa);

        this.daoProfesor = new DAOProfesor(conexion,fa);

        } catch (FileNotFoundException f){
            System.out.println(f.getMessage());
            fa.muestraExcepcion(f.getMessage());
        } catch (IOException i){
            System.out.println(i.getMessage());
            fa.muestraExcepcion(i.getMessage());
        } 
        catch (java.sql.SQLException e){
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());
        }
        
        
        
    }
    public Usuario validarUsuario(String idUsuario, String clave){
        return daoUsuarios.validarUsuario(idUsuario, clave);
    }
    
    
    
    
    
    
    public java.util.List<Usuario> consultarUsuarios(String id, String nombre){
        return daoUsuarios.consultarUsuarios(id,nombre);
    }


    public List<Sesion> obtenerSesiones(Integer numero, String aula, String fecha, String grupo, String profesor) {
        return daoUsuarios.obtenerSesiones(numero, aula, fecha, grupo, profesor);
    }
    
    public List<SesionCliente> obtenerSesionesCliente (String nickname, String nombreActividad, String nombreAula, String fecha, String hora){
        return daoClientes.obtenerSesionesCliente(nickname, nombreActividad, nombreAula, fecha, hora);
    }

    public List<SesionProfesor> obtenerSesionesProfesor (String nickname, String nombreActividad, String nombreAula, String fecha, String hora, String descripcion){
        return daoProfesor.obtenerSesionesProfesor(nickname, nombreActividad, nombreAula, fecha, hora, descripcion);
    }
}
