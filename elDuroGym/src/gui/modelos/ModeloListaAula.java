package gui.modelos;

public class ModeloListaAula extends javax.swing.AbstractListModel<String> {
    private java.util.List<aplicacion.Aula> aulas;

    public ModeloListaAula() {
        this.aulas = new java.util.ArrayList<>();
    }

    public ModeloListaAula(java.util.List<aplicacion.Aula> aulas) {
        this.aulas = new java.util.ArrayList<>(aulas);
    }

    @Override
    public int getSize() {
        return aulas.size();
    }

    @Override
    public String getElementAt(int index) {
        aplicacion.Aula aulaEnCuestion = aulas.get(index);
        return aulaEnCuestion.getNombre();
    }

    public aplicacion.Aula getAulaAt(int index) {
        return aulas.get(index);
    }

    public void setFilas(java.util.List<aplicacion.Aula> aulas) {
        this.aulas = aulas;
        fireContentsChanged(this, 0, aulas.size() - 1);
    }
}
