/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Reporte;
import aplicacion.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author alumnogreibd
 */
public class DAOReportes extends AbstractDAO{
    public DAOReportes (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    
    public void insertarReporte(String denunciante,String reportado,String descripcion){
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;

        con=this.getConexion();
        
        try {
            
        stmUsuario=con.prepareStatement("INSERT INTO reporte(denunciante,reportado,motivo) VALUES (?,?,?)");
        stmUsuario.setString(1, denunciante);//esto sirve para darle los valores a las interrogaciones
        stmUsuario.setString(2, reportado);//esto sirve para darle los valores a las interrogaciones
        stmUsuario.setString(3, descripcion);//esto sirve para darle los valores a las interrogaciones
        stmUsuario.executeUpdate();
        
        }catch(SQLException e){
            
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            
        }finally {
            try {
                stmUsuario.close(); //Cierra cursores
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
    }
    
    public ArrayList<Reporte> consultarReportes(){
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;
        ArrayList<Reporte> resultado=new ArrayList<>();

        con=this.getConexion();
        
        try {
            
        stmUsuario=con.prepareStatement("SELECT *\n" +
                               "FROM reporte\n" +
                            "WHERE reportado  NOT IN (SELECT reportado FROM revisar)\n" +
                            "	and denunciante NOT IN (SELECT denunciante FROM revisar)\n" +
                            "	and fecha NOT IN (SELECT fechareporte FROM revisar)");
        
        rsUsuario=stmUsuario.executeQuery();
        
        while(rsUsuario.next()){
            Reporte aux = new Reporte(rsUsuario.getString("denunciante"),rsUsuario.getString("reportado"),rsUsuario.getDate("fecha"),rsUsuario.getString("motivo"));
            resultado.add(aux);
        }
        
        }catch(SQLException e){
            
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            
        } finally {
            try {
                stmUsuario.close(); //Cierra cursores
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }  
        
        return resultado;
    }
}
