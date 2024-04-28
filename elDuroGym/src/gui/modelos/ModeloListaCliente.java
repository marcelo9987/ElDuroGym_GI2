package gui.modelos;

import aplicacion.Cliente;

import java.lang.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public final class ModeloListaCliente extends AbstractListModel {
    public List<Cliente> clientes;

    public ModeloListaCliente() {
        super();
        clientes = new ArrayList<>();

    }

    public ModeloListaCliente(List<Cliente> clientes) {
        super();
        this.clientes = new ArrayList<>(clientes);
    }

    @Override
    public int getSize() {
        return clientes.size();
    }

    @Override
    public String getElementAt(int index) {
        Cliente clienteEnCuestion = clientes.get(index);
        return clienteEnCuestion.getNombre();
    }

    public void nuevoElemento(Cliente cliente)
    {
        this.clientes.add(cliente);
        fireIntervalAdded(this, this.clientes.size() - 1, this.clientes.size() - 1);
    }

    public void borrarElemento(int i)
    {
//        Cliente cliente = this.clientes.get(i);
        this.clientes.remove(i);
        fireIntervalRemoved(this, i, i);
    }

    public Cliente getClienteAt(int index) {
        return clientes.get(index);
    }

    public ArrayList<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        fireContentsChanged(this, 0, clientes.size() - 1);
    }
}
