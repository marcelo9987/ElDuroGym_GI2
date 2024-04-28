package controladores;

import aplicacion.Profesor;
import basedatos.FachadaBaseDatos;

import java.util.List;

public final class GestionProfesores {
    private final FachadaBaseDatos fbd;

    public GestionProfesores(FachadaBaseDatos fbd) {
        this.fbd = fbd;
    }


    public List<Profesor> obtenerProfesores()
    {
        return fbd.obtenerProfesores();
    }

    public Profesor obtenerProfesorGrupo(int idGrupo)
    {
        return fbd.obtenerProfesorGrupo(idGrupo);
    }
}
