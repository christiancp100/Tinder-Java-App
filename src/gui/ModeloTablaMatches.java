/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import aplicacion.Cliente;
import javax.swing.table.*;

/**
 *
 * @author christiancp
 */
public class ModeloTablaMatches extends AbstractTableModel {
    private java.util.List<Cliente> matches;

    public ModeloTablaMatches(){
        this.matches=new java.util.ArrayList<>();
    }

    @Override
    public int getColumnCount (){
        return 5;
    }

    @Override
    public int getRowCount(){
        return matches.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "Foto"; break;
            case 1: nombre= "Usuario"; break;
            case 2: nombre= "Nombre"; break;
            case 3: nombre="Edad"; break;
            case 4: nombre="Provincia"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase= java.lang.String.class; break;
            case 2: clase= java.lang.String.class; break;
            case 3: clase=java.lang.Integer.class; break;
            case 4: clase=java.lang.String.class; break;
        }
        return clase;
    }

    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    @Override
    public Object getValueAt(int row, int col){
        Object resultado=null;

        switch (col){
            //TODO: Cambiar email por foto y arriba string por lo que sea
            case 0: resultado= matches.get(row).getEmail(); break;
            case 1: resultado = matches.get(row).getNombreUsuario();break;
            case 2: resultado= matches.get(row).getNombre(); break;
            case 3: resultado=matches.get(row).getEdad();break;
            case 4: resultado=matches.get(row).getProvincia(); break;
        }
        return resultado;
    }

    public void setFilas(java.util.List<Cliente> matches){
        this.matches=matches;
        fireTableDataChanged();
    }

    public Cliente obtenerMatch(int i){
        return this.matches.get(i);
    }

}
