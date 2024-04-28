package gui.modelos;

import aplicacion.Profesor;

import java.lang.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public final class ModeloListaProfesor extends AbstractListModel {
    public List<Profesor> profesores;

    public ModeloListaProfesor() {
        super();
        profesores = new ArrayList<>();

    }

    public ModeloListaProfesor(List<Profesor> profesores) {
        super();
        this.profesores = new ArrayList<>(profesores);
    }

    @Override
    public int getSize() {
        return profesores.size();
    }

    @Override
    public String getElementAt(int index) {
        aplicacion.Profesor profEnCuestion = profesores.get(index);
        return profEnCuestion.getNombre();
    }

    public void setFilas(List<Profesor> profesores) {
        this.profesores = profesores;
        fireContentsChanged(this, 0, profesores.size() - 1);
    }

    public Profesor getProfesorAt(int selectedIndex) {
        return profesores.get(selectedIndex);
    }

}
