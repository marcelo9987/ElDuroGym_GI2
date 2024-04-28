/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basedatos;

import aplicacion.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

/**
 * @author alumnogreibd
 */
public class FachadaBaseDatos {
//    private final FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private DAOUsuarios daoUsuarios;
    private DAOClientes daoClientes;
    private DAOProfesor daoProfesor;
    private DAOActividades daoActividades;
    private DAOGrupos daoGrupos;
    private DAOAulas daoAulas;
    private DAOEquipamiento daoEquipamiento;
    private DAOSesion daoSesion;

    public FachadaBaseDatos(FachadaAplicacion fa) {

        Properties configuracion = new Properties();
//        this.fa = fa;


        try (
                FileInputStream arqConfiguracion = new FileInputStream("baseDatos.properties")
        ) {
            configuracion.load(arqConfiguracion);
//            arqConfiguracion.close();

            Properties usuario = new Properties();


            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion = java.sql.DriverManager.getConnection("jdbc:" + gestor + "://" +
                            configuracion.getProperty("servidor") + ":" +
                            configuracion.getProperty("puerto") + "/" +
                            configuracion.getProperty("baseDatos"),
                    usuario);

            this.daoUsuarios = new DAOUsuarios(conexion, fa);

            this.daoClientes = new DAOClientes(conexion, fa);

            this.daoProfesor = new DAOProfesor(conexion, fa);

            this.daoActividades = new DAOActividades(conexion, fa);

            this.daoGrupos = new DAOGrupos(conexion, fa);

            this.daoAulas = new DAOAulas(conexion, fa);

            this.daoEquipamiento = new DAOEquipamiento(conexion, fa);

            this.daoSesion = new DAOSesion(conexion, fa);

        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
            fa.muestraExcepcion(f.getMessage());
        } catch (IOException i) {
            System.out.println(i.getMessage());
            fa.muestraExcepcion(i.getMessage());
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());
        }


    }

    public Usuario validarUsuario(String idUsuario, String clave) {
        return daoUsuarios.validarUsuario(idUsuario, clave);
    }

    public List<Usuario> consultarUsuarios(String id, String nombre) {
        return daoUsuarios.consultarUsuarios(id, nombre);
    }


    public List<Sesion> obtenerSesiones(Integer numero, String aula, String fecha, String grupo, String profesor) {
        return daoUsuarios.obtenerSesiones(numero, aula, fecha, grupo, profesor);
    }

    public List<SesionCliente> obtenerSesionesCliente(String nickname, String nombreActividad, String nombreAula, String fecha, String hora) {
        return daoClientes.obtenerSesionesCliente(nickname, nombreActividad, nombreAula, fecha, hora);
    }

    public List<SesionProfesor> obtenerSesionesProfesor(String nickname, String nombreActividad, String nombreAula, String fecha, String hora, String descripcion) {
        return daoProfesor.obtenerSesionesProfesor(nickname, nombreActividad, nombreAula, fecha, hora, descripcion);
    }

    public List<Aula> obtenerAulas(String nombre, int aforo) {
        return daoAulas.obtenerAulas(nombre, aforo);
    }

    public List<Actividad> consultarActividades() {
        return daoActividades.consultarActividades();
    }

    public void eliminarActividad(Actividad actividad) {
        daoActividades.eliminarActividad(actividad);
    }

    public boolean actividadTieneGrupos(Actividad actividad) {
        return daoActividades.actividadTieneGrupos(actividad);
    }

    public void modificarActividad(Actividad actividad, String nombre, String descripcion, String tipo) {
        daoActividades.modificarActividad(actividad, nombre, descripcion, tipo);
    }

    public boolean existeActividad(String nombre) {
        return daoActividades.existeActividad(nombre);
    }

    public void crearActividad(String nombre, String descripcion, String tipo) {
        daoActividades.crearActividad(nombre, descripcion, tipo);
    }

    public void editarAforo(String nombreAula, int nuevoAforo) {
        daoAulas.editarAforo(nombreAula, nuevoAforo);
    }

    public List<Equipamiento> obtenerEquipamientosPorAula(String nombreAula, String descripcionEquipamiento) {
        return daoEquipamiento.obtenerEquipamientosPorAula(nombreAula, descripcionEquipamiento);
    }

    // INI GRUPOS

    public List<Grupo> obtenerGrupos() {
        return daoGrupos.obtenerGrupos();
    }

    public List<Profesor> obtenerProfesores() {
        return daoProfesor.obtenerProfesores();
    }

    public List<Cliente> obtenerClientes() {
        return daoClientes.obtenerClientes();
    }

    public List<Cliente> obtenerClientesGrupo(Grupo grupo) {
        return daoClientes.obtenerClientesGrupo(grupo);
    }

    public Profesor obtenerProfesorGrupo(int idGrupo) {
        return daoProfesor.obtenerProfesorGrupo(idGrupo);
    }

    public void modificarGrupo(Grupo grupo, Actividad actividad, Profesor profesor) {
        daoGrupos.modificarGrupo(grupo, actividad);
        daoGrupos.cambiarProfesorGrupo(grupo, profesor);
    }

    public void borrarClientesGrupo(Grupo grupo) {
        daoClientes.borrarClientesGrupo(grupo);
    }

//    public void borrarClientesGrupo(Grupo grupo, ArrayList<Cliente> arrayListAlumnosEliminar) {
//        daoClientes.borrarClientesGrupo(grupo, arrayListAlumnosEliminar);
//    }

    public void setAlumnoGrupo(Grupo grupo, Cliente c) {
        daoClientes.setAlumnoGrupo(grupo, c);
    }

    public boolean alumnoTieneGrupo(Cliente c, Grupo g) {
        return daoClientes.alumnoTieneGrupo(c, g);
    }
    // FIN GRUPOS

    // INI SESIONES

    public Grupo obtenerGrupoPorId(int idGrupo) {
        return daoSesion.obtenerGrupoPorId(idGrupo);
    }

    public boolean haySesionesEnAula(int idAula, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        return daoSesion.haySesionesEnAula(idAula, fechaHoraInicio, fechaHoraFin);
    }

    public List<SesionProfesor> obtenerSesionesProfesorVentana(String nickname, String nombreActividad, String nombreAula, String descripcion) {
        return daoSesion.obtenerSesionesProfesorVentana(nickname, nombreActividad, nombreAula, descripcion);
    }

    public boolean crearSesionParaProfesor(int idAula, int idGrupo, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String descripcion) {
        return daoSesion.crearSesionParaProfesor(idAula, idGrupo, fechaHoraInicio, fechaHoraFin, descripcion);
    }

    public Aula obtenerAulaPorNombre(String nombre) {
        return daoSesion.obtenerAulaPorNombre(nombre);
    }

    public boolean insertarGrupoTieneProfesor(int idGrupo, int idProfesor) {
        return daoSesion.insertarGrupoTieneProfesor(idGrupo, idProfesor);
    }

    public int obtenerIdProfesorPorNombre(String nombreProfesor) {
        return daoProfesor.obtenerIdProfesorPorNombre(nombreProfesor);
    }

    public boolean borrarSesionesDeProfesor(int idProfesor, int idGrupo) {
        return daoSesion.borrarSesionesDeProfesor(idProfesor, idGrupo);
    }

    public Integer crearGrupo(int id_actividad) {
        return daoGrupos.crearGrupo(id_actividad);
    }

    public void borrarGrupo(Grupo grupo) {
        daoGrupos.borrarGrupo(grupo);
    }

    public boolean grupoTieneSesiones(Grupo grupo) {
        return daoSesion.grupoTieneSesiones(grupo);
    }

    // FIN SESIONES

}
