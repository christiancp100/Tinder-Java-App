/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import aplicacion.Cliente;
import aplicacion.Reporte;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author alumnogreibd
 */
public class ModeloTablaRevisar extends AbstractTableModel{
    private java.util.List<Reporte> reportes;

    public ModeloTablaRevisar(){
        this.reportes=new java.util.ArrayList<>();
    }

    @Override
    public int getColumnCount (){
        return 4;
    }

    @Override
    public int getRowCount(){
        return reportes.size();
    }

    @Override
    public String getColumnName(int col){
        String nombre="";

        switch (col){
            case 0: nombre= "denunciante"; break;
            case 1: nombre= "reportado"; break;
            case 2: nombre= "fecha"; break;
            case 3: nombre="motivo"; break;
        }
        return nombre;
    }

    @Override
    public Class getColumnClass(int col){
        Class clase=null;

        switch (col){
            case 0: clase= java.lang.String.class; break;
            case 1: clase= java.lang.String.class; break;
            case 2: clase= java.sql.Date.class; break;
            case 3: clase=java.lang.String.class; break;
        }
        return clase;
    }

    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    public Object getValueAt(int row, int col){
        Object resultado=null;

        switch (col){
            //TODO: Cambiar email por foto y arriba string por lo que sea
            case 0: resultado= reportes.get(row).getDenunciante(); break;
            case 1: resultado = reportes.get(row).getReportado();break;
            case 2: resultado= reportes.get(row).getFecha(); break;
            case 3: resultado=reportes.get(row).getMotivo();break;
        }
        return resultado;
    }

    public void setFilas(java.util.List<Reporte> reportes){
        this.reportes=reportes;
        fireTableDataChanged();
    }

    public Reporte obtenerReporte(int i){
        return this.reportes.get(i);
    }    
}
