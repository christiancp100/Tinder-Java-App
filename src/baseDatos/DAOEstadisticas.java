/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Reporte;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alumnogreibd
 */
public class DAOEstadisticas extends AbstractDAO{
    public DAOEstadisticas (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public int numReporterAprobadosAUnaPersona(String nombre){
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;
        int resultado=0;

        con=this.getConexion();
        
        try {
            
        stmUsuario=con.prepareStatement("SELECT count(*)\n" +
                                        "FROM revisar\n" +
                                        "WHERE reportado=? and aprobado='true'");
        
        stmUsuario.setString(1,nombre);
        rsUsuario=stmUsuario.executeQuery();
        
        if(rsUsuario.next()){
            resultado=rsUsuario.getInt(1);//comprobar si va un 0 o un 1,hace referencia a la primera y única columna
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
    
    public int numPersonasBaneadas(){
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;
        int resultado=0;

        con=this.getConexion();
        
        try {
            
        stmUsuario=con.prepareStatement("SELECT count(*)\n" +
                                        "FROM revisar\n" +
                                        "WHERE aprobado='true'");
        
        rsUsuario=stmUsuario.executeQuery();
        
        if(rsUsuario.next()){
            resultado=rsUsuario.getInt(1);//comprobar si va un 0 o un 1,hace referencia a la primera y única columna
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
    
    public ArrayList<String> personasQueConsiguierenMatchMientrasPagaban(){
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;
        ArrayList<String> resultado=new ArrayList<>();

        con=this.getConexion();
        
        try {
            
        stmUsuario=con.prepareStatement("SELECT usuario\n" +
                                        "FROM premium\n" +
                                        "INNER JOIN matches ON premium.usuario=matches.usuario1\n" +
                                        "WHERE matches.fechamatch>premium.fechainicio and matches.fechamatch<premium.fechafin");
        
        rsUsuario=stmUsuario.executeQuery();
        
        while(rsUsuario.next()){
            String aux = rsUsuario.getString(1);
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
