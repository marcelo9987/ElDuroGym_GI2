
package gui.modelos;

import aplicacion.Grupo;

import java.util.List;

public final class ModeloListaGrupos extends javax.swing.AbstractListModel{
    private final java.util.List<Grupo> grupos;

    public ModeloListaGrupos() {
        this.grupos = new java.util.ArrayList<>();
    }

    @Override
    public int getSize() {
        return grupos.size();
    }


    @Override
    public Integer getElementAt(int index) {
        return grupos.get(index).getIdGrupo();
    }

    public Grupo getGrupoAt(int index) {
        return grupos.get(index);
    }



    public void setFilas(List<Grupo> grupos) {
        this.grupos.clear();
        this.grupos.addAll(grupos);
        fireContentsChanged(this, 0, grupos.size() - 1);
    }
}

