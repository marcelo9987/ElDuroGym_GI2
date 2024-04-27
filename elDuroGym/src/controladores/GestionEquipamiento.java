package controladores;

import aplicacion.Aula;
import aplicacion.Equipamiento;
import basedatos.FachadaBaseDatos;

import java.util.List;

public class GestionEquipamiento {
    FachadaBaseDatos fbd;

    public GestionEquipamiento(FachadaBaseDatos fbd){
        this.fbd=fbd;
    }

    public List<Equipamiento> obtenerEquipamientosPorAula(String nombreAula, String descripcionEquipamiento){
        return fbd.obtenerEquipamientosPorAula(nombreAula, descripcionEquipamiento);
    }
}
