package controladores;

import aplicacion.Actividad;
import aplicacion.Aula;
import aplicacion.Equipamiento;
import aplicacion.Grupo;
import basedatos.FachadaBaseDatos;

import java.time.LocalDateTime;
import java.util.List;

public class GestionSesiones {

    FachadaBaseDatos fbd;
    public GestionSesiones(FachadaBaseDatos fbd){
        this.fbd=fbd;
    }

    public Aula obtenerAulaPorNombre(String nombre) {
        return fbd.obtenerAulaPorNombre(nombre);
    }

    public Actividad obtenerActividadPorId(int idActividad) {
        return fbd.obtenerActividadPorId(idActividad);
    }

    public Grupo obtenerGrupoPorId(int idGrupo) {
        return fbd.obtenerGrupoPorId(idGrupo);
    }

    public int obtenerSiguienteIdReserva() {
        return fbd.obtenerSiguienteIdReserva();
    }

    public boolean haySesionesEnAula(int idAula, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        return fbd.haySesionesEnAula(idAula, fechaHoraInicio, fechaHoraFin);
    }
    public boolean idGrupoRelacionadoConProfesor(int idGrupo, int idProfesor) {
        return fbd.idGrupoRelacionadoConProfesor(idGrupo, idProfesor);
    }
}
