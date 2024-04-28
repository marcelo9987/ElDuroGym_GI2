package controladores;

import aplicacion.Actividad;
import aplicacion.Aula;
import aplicacion.Equipamiento;
import aplicacion.Grupo;
import aplicacion.SesionProfesor;
import basedatos.FachadaBaseDatos;

import java.time.LocalDateTime;
import java.util.List;

public class GestionSesiones {

    FachadaBaseDatos fbd;
    public GestionSesiones(FachadaBaseDatos fbd){
        this.fbd=fbd;
    }

    public Grupo obtenerGrupoPorId(int idGrupo) {
        return fbd.obtenerGrupoPorId(idGrupo);
    }

    public boolean haySesionesEnAula(int idAula, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) {
        return fbd.haySesionesEnAula(idAula, fechaHoraInicio, fechaHoraFin);
    }

    
    public List<SesionProfesor> obtenerSesionesProfesorVentana(String nickname, String nombreActividad, String nombreAula, String descripcion) {
        return fbd.obtenerSesionesProfesorVentana(nickname, nombreActividad, nombreAula, descripcion);
    }

    public boolean crearSesionParaProfesor(int idAula, int idGrupo, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, String descripcion) {
        return fbd.crearSesionParaProfesor(idAula, idGrupo, fechaHoraInicio, fechaHoraFin, descripcion);
    }

    public Aula obtenerAulaPorNombre(String nombre){
        return fbd.obtenerAulaPorNombre(nombre);
    }

    public boolean insertarGrupoTieneProfesor(int idGrupo, int idProfesor) {
        return fbd.insertarGrupoTieneProfesor(idGrupo, idProfesor);
    }

    public boolean borrarSesionesDeProfesor(int idProfesor, int idGrupo) {
        return fbd.borrarSesionesDeProfesor(idProfesor, idGrupo);
    }
}
