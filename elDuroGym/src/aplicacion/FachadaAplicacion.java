/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package aplicacion;

import controladores.GestionActividades;
import controladores.GestionAulas;
import controladores.GestionUsuarios;

import java.util.List;

/**
 *
 * @author alumnogreibd
 */
public class FachadaAplicacion {

    gui.FachadaGui fgui;
    basedatos.FachadaBaseDatos fbd;
    GestionUsuarios cu;
    TipoUsuario nivelAcceso;
    GestionActividades ca;
    GestionAulas ga;
    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new basedatos.FachadaBaseDatos(this);
        cu = new GestionUsuarios(fgui,fbd);
        ca = new GestionActividades(fbd);
        ga = new GestionAulas(fbd);
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

    public List<Sesion> obtenerSesiones(Integer numero, String aula, String fecha, String grupo, String profesor)
    {
        return cu.obtenerSesiones(numero, aula, fecha, grupo, profesor);
    }
    
    public List<SesionCliente> obtenerSesionesCliente (String nickname, String nombreActividad, String nombreAula, String fecha, String hora){
        return cu.obtenerSesionesCliente(nickname, nombreActividad, nombreAula, fecha, hora);
    }

    public List<SesionProfesor> obtenerSesionesProfesor (String nickname, String nombreActividad, String nombreAula, String fecha, String hora, String descripcion){
        return cu.obtenerSesionesProfesor(nickname, nombreActividad, nombreAula, fecha, hora, descripcion);
    }

    public List<Actividad> consultarActividades()
    {
        return ca.consultarActividades();
    }

    public void eliminarActividad(Actividad actividad)
    {
        if(fbd.ActividadTieneGrupos(actividad)){
            fgui.muestraExcepcion("La actividad tiene grupos asociados, no se puede eliminar");
            return;
        }
        ca.eliminarActividad(actividad);
    }

    public void modificarActividad(Actividad actividad, String nombre, String descripcion, String tipo) {
        ca.modificarActividad(actividad, nombre, descripcion, tipo);
    }
    
    public List<Aula> obtenerAulasPorNombre(String nombre){
        return ga.obtenerAulasPorNombre(nombre);
    }
}
