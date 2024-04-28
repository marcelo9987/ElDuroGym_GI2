package controladores;

import aplicacion.Cliente;
import aplicacion.Grupo;

import java.util.ArrayList;
import java.util.List;

public class GestionClientes
{
    private final basedatos.FachadaBaseDatos fbd;

    public GestionClientes(basedatos.FachadaBaseDatos fbd)
    {
        this.fbd = fbd;
    }

    public List<Cliente> obtenerClientes()
    {
        return fbd.obtenerClientes();
    }

    public List<Cliente> obtenerClientesGrupo(Grupo grupo)
    {
        return fbd.obtenerClientesGrupo(grupo);
    }

    public void setClientesGrupo(Grupo grupo, List<Cliente> arrayListAlumnos)
    {
        // en dos fases, primero borramos todos los alumnos del grupo
        fbd.borrarClientesGrupo(grupo);
        // y luego añadimos los nuevos
        for (Cliente c : arrayListAlumnos)
        {
            if(!fbd.alumnoTieneGrupo(c,grupo)) {
                fbd.setAlumnoGrupo(grupo, c);
            }
        }
    }

//    public void eliminarClientesGrupo(Grupo grupo, List<Cliente> arrayListAlumnosEliminar)
//    {
//        for (Cliente c : arrayListAlumnosEliminar)
//        {
//            if(fbd.alumnoTieneGrupo(c,grupo)) {
//                fbd.borrarClientesGrupo(grupo, arrayListAlumnosEliminar);
//            }
//        }
//    }
}
