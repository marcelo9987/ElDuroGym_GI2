
package gui.modelos;

import aplicacion.Actividad;

import java.util.ArrayList;
import java.util.List;

public class ModeloListaActividades extends javax.swing.AbstractListModel<String>{
    private final java.util.List<Actividad> actividades;

    public ModeloListaActividades() {
        this.actividades = new ArrayList<>();
    }

    @Override
    public int getSize() {
        return actividades.size();
    }


    @Override
    public String getElementAt(int index) {
        return actividades.get(index).getNombre();
    }

    public Actividad getActividadAt(int index) {
        return actividades.get(index);
    }



    public void setFilas(List<Actividad> actividades) {
        this.actividades.clear();
        this.actividades.addAll(actividades);
        fireContentsChanged(this, 0, actividades.size() - 1);
    }
}

