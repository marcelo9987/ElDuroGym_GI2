package controladores;

import aplicacion.Actividad;
import basedatos.FachadaBaseDatos;

import java.util.List;

public class GestionActividades {
    FachadaBaseDatos fbd;

    public GestionActividades(FachadaBaseDatos fbd){
        this.fbd=fbd;
    }

    public List<Actividad> consultarActividades()
    {
        return fbd.consultarActividades();
    }

    public void eliminarActividad(Actividad actividad)
    {
        fbd.eliminarActividad(actividad);
    }

    public void modificarActividad(Actividad actividad, String nombre, String descripcion, String tipo) {
        fbd.modificarActividad(actividad, nombre, descripcion, tipo);
    }

    public boolean existeActividad(String nombre) {
        return fbd.existeActividad(nombre);
    }

    public void crearActividad(String nombre, String descripcion, String tipo) {
        fbd.crearActividad(nombre, descripcion, tipo);
    }
}
