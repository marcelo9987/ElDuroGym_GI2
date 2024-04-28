package controladores;

import aplicacion.Actividad;
import aplicacion.Grupo;
import aplicacion.Profesor;
import basedatos.FachadaBaseDatos;

import java.util.List;

public class GestionGrupos {
    FachadaBaseDatos fbd;

    public GestionGrupos(FachadaBaseDatos fbd) {
        this.fbd = fbd;
    }

    public List<Grupo> obtenerGrupos() {
        return fbd.obtenerGrupos();
    }

    public void modificarGrupo(Grupo grupo, Actividad actividad, Profesor profesor) {
        fbd.modificarGrupo(grupo, actividad, profesor);
    }

    public Integer crearGrupo(int id_actividad, int id_profesor) {
        Integer id_grupo =  fbd.crearGrupo(id_actividad);

        if (id_grupo != null) {
            fbd.insertarGrupoTieneProfesor(id_grupo, id_profesor);
        }

        return id_grupo;
    }

    public void borrarGrupo(Grupo grupo) {
        fbd.borrarGrupo(grupo);
    }
}
