/*
 * The MIT License
 *
 * Copyright 2024 alumnogreibd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package gui.modelos;

import aplicacion.Equipamiento;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author alumnogreibd
 */
public class ModeloEquipamiento extends AbstractTableModel {

    private java.util.List<Equipamiento> equipamientos;

    public ModeloEquipamiento() {
        this.equipamientos = new java.util.ArrayList<>();
    }

    @Override
    public int getColumnCount() {
        return 2; // Dos columnas: Nombre y Descripción
    }

    @Override
    public int getRowCount() {
        return equipamientos.size();
    }

    @Override
    public String getColumnName(int col) {
        String nombre = "";

        switch (col) {
            case 0:
                nombre = "Nombre";
                break;
            case 1:
                nombre = "Descripción";
                break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col) {
        Class clase = null;

        switch (col) {
            case 0:
            case 1:
                clase = java.lang.String.class; // Ambas columnas son de tipo String
                break;
        }
        return clase;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object resultado = null;

        switch (col) {
            case 0:
                resultado = equipamientos.get(row).id_aula().getNombre();
                break;
            case 1:
                resultado = equipamientos.get(row).getDescripcion();
                break;
        }
        return resultado;
    }

    public void setFilas(java.util.List<Equipamiento> equipamientos) {
        this.equipamientos = equipamientos;
        fireTableDataChanged();
    }

    public Equipamiento obtenerEquipamiento(int i) {
        return this.equipamientos.get(i);
    }

}
