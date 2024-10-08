/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aplicacion;

import controladores.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author alumnogreibd
 */
public class FachadaAplicacion {

    gui.FachadaGui fgui;
    basedatos.FachadaBaseDatos fbd;
    GestionUsuarios cu;
    TipoUsuario nivelAcceso;
    GestionActividades ca;
    GestionGrupos cg;
    GestionProfesores cp;
    GestionClientes cl;
    GestionAulas ga;
    GestionEquipamiento ge;
    GestionSesiones gs;

    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new basedatos.FachadaBaseDatos(this);
        cu = new GestionUsuarios(fgui, fbd);
        ca = new GestionActividades(fbd);
        cg = new GestionGrupos(fbd);
        cp = new GestionProfesores(fbd);
        cl = new GestionClientes(fbd);
        ga = new GestionAulas(fbd);
        ge = new GestionEquipamiento(fbd);
        gs = new GestionSesiones(fbd);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FachadaAplicacion fa;

        fa = new FachadaAplicacion();
        fa.iniciaInterfazUsuario();
    }

    public void iniciaInterfazUsuario() {
        fgui.iniciaVista();
    }

    public void muestraMensaje(String m) {
        fgui.muestraMensaje(m);
    }

    public void muestraExcepcion(String e) {
        fgui.muestraExcepcion(e);
    }

    public TipoUsuario comprobarAutentificacion(String nickName, String clave) {
        return cu.comprobarAutentificacion(nickName, clave);
    }

    public TipoUsuario getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(TipoUsuario nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public List<Sesion> obtenerSesiones(Integer numero, String aula, String fecha, String grupo, String profesor) {
        return cu.obtenerSesiones(numero, aula, fecha, grupo, profesor);
    }

    public List<SesionCliente> obtenerSesionesCliente(String nickname, String nombreActividad, String nombreAula, String fecha, String hora) {
        return cu.obtenerSesionesCliente(nickname, nombreActividad, nombreAula, fecha, hora);
    }

    public List<SesionProfesor> obtenerSesionesProfesor(String nickname, String nombreActividad, String nombreAula, String fecha, String hora, String descripcion) {
        return cu.obtenerSesionesProfesor(nickname, nombreActividad, nombreAula, fecha, hora, descripcion);
    }

    public List<Actividad> consultarActividades() {
        return ca.consultarActividades();
    }

    public void eliminarActividad(Actividad actividad) {
        if (fbd.actividadTieneGrupos(actividad)) {
            fgui.muestraExcepcion("La actividad tiene grupos asociados, no se puede eliminar");
            return;
        }
        ca.eliminarActividad(actividad);
    }

    public void modificarActividad(Actividad actividad, String nombre, String descripcion, String tipo) {
        ca.modificarActividad(actividad, nombre, descripcion, tipo);
    }

    public List<Aula> obtenerAulas(String nombre, int aforo) {
        return ga.obtenerAulas(nombre, aforo);
    }

    public void editarAforo(String nombreAula, int nuevoAforo) {
        ga.editarAforo(nombreAula, nuevoAforo);
    }

    public boolean existeActividad(String nombre) {
        return ca.existeActividad(nombre);
    }

    public void crearActividad(String nombre, String descripcion, String tipo) {
        ca.crearActividad(nombre, descripcion, tipo);
    }

    public List<Equipamiento> obtenerEquipamientosPorAula(String nombreAula, String descripcionEquipamiento) {
        return ge.obtenerEquipamientosPorAula(nombreAula, descripcionEquipamiento);
    }

    // INI GRUPOS

    public List<Grupo> obtenerGrupos() {
        return cg.obtenerGrupos();
    }

    public List<Profesor> obtenerProfesores() {
        return cp.obtenerProfesores();
    }

    public List<Cliente> obtenerClientes() {
        return cl.obtenerClientes();
    }

    public List<Cliente> obtenerClientesGrupo(Grupo grupo) {
        return cl.obtenerClientesGrupo(grupo);
    }

    public Profesor obtenerProfesorGrupo(int idGrupo) {
        return cp.obtenerProfesorGrupo(idGrupo);
    }

    public void modificarGrupo(Grupo grupo, Actividad actividad, Profesor profesor) {
        cg.modificarGrupo(grupo, actividad, profesor);
    }

    public void setAlumnosGrupo(Grupo grupo, List<Cliente> arrayListAlumnos) {
        cl.setClientesGrupo(grupo, arrayListAlumnos);
    }

//    public void eliminarAlumnosGrupo(Grupo grupo, ArrayList<Cliente> arrayListAlumnosEliminar) {
//        cl.eliminarClientesGrupo(grupo, arrayListAlumnosEliminar);
//    }

    // FIN GRUPOS

    // INI SESIONES

    public Grupo obtenerGrupoPorId(int idGrupo) {
        return gs.obtenerGrupoPorId(idGrupo);
    }


    public boolean haySesionesEnAula(int idAula, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        return gs.haySesionesEnAula(idAula, fechaHoraInicio, fechaHoraFin);
    }


    public List<SesionProfesor> obtenerSesionesProfesorVentana(String nickname, String nombreActividad, String nombreAula, String descripcion) {
        return gs.obtenerSesionesProfesorVentana(nickname, nombreActividad, nombreAula, descripcion);
    }

    public boolean crearSesionParaProfesor(int idAula, int idGrupo, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String descripcion) {
        return gs.crearSesionParaProfesor(idAula, idGrupo, fechaHoraInicio, fechaHoraFin, descripcion);
    }

    public Aula obtenerAulaPorNombre(String nombre) {
        return gs.obtenerAulaPorNombre(nombre);
    }

    public boolean insertarGrupoTieneProfesor(int idGrupo, int idProfesor) {
        return gs.insertarGrupoTieneProfesor(idGrupo, idProfesor);
    }

    public int obtenerIdProfesorPorNombre(String nombreProfesor) {
        return fbd.obtenerIdProfesorPorNombre(nombreProfesor);
    }

    public boolean borrarSesionesDeProfesor(int idProfesor, int idGrupo) {
        return gs.borrarSesionesDeProfesor(idProfesor, idGrupo);
    }

    public void crearGrupo(int id_actividad, int id_profesor) {
        cg.crearGrupo(id_actividad, id_profesor);
    }

    public void borrarGrupo(Grupo grupo) {
        cg.borrarGrupo(grupo);
    }

    public boolean grupoTieneSesiones(Grupo grupo) {
        return gs.grupoTieneSesiones(grupo);
    }

    // FIN SESIONES
}
