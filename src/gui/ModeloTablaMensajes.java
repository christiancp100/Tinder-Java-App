/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Mensaje;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author christiancp
 */
public class ModeloTablaMensajes extends AbstractTableModel{
    private java.util.List<Mensaje> mensajes;

    public ModeloTablaMensajes(){
        this.mensajes=new java.util.ArrayList<>();
    }

    @Override
    public int getColumnCount (){
        //Si ponemos esto a 3 enseñaria el id del mensaje, pero no nos interesa
        return 2;
    }

    @Override
    public int getRowCount(){
        return mensajes.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "usuario"; break;
            case 1: nombre= "mensaje"; break;
            case 2: nombre = "id"; break;
            case 3: nombre = "usuario1"; break;
            case 4: nombre = "usuario2"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase= java.lang.String.class; break;
            case 2: clase = java.lang.Integer.class; break;
            case 3: clase = java.lang.String.class; break;
            case 4: clase = java.lang.String.class; break;
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
            case 0: resultado= mensajes.get(row).getUsuarioAutor();break;
            case 1: resultado= mensajes.get(row).getTexto();break;
            case 2: resultado= mensajes.get(row).getId(); break;
            case 3: resultado= mensajes.get(row).getUsuario1(); break;
            case 4: resultado= mensajes.get(row).getUsuario2(); break;
        }   
        return resultado;
    }

    public void setFilas(java.util.List<Mensaje> mensajes){
        this.mensajes=mensajes;
        fireTableDataChanged();
    }

    public Mensaje obtenerMensaje(int i){
        return this.mensajes.get(i);
    }
}
