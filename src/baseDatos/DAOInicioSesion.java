/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Usuario;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author alumnogreibd
 */
public class DAOInicioSesion extends AbstractDAO{
    public DAOInicioSesion (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public void registrarInicio(String usuario){
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;

        con=this.getConexion();
        
        try {
            
        stmUsuario=con.prepareStatement("INSERT INTO iniciosesion(usuario) VALUES (?)");
        stmUsuario.setString(1, usuario);//esto sirve para darle los valores a las interrogaciones
        stmUsuario.executeUpdate();
        
        }catch(SQLException e){
            
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            
        }
    }
    
    public String obtenerCodigo(String usuario){
        String resultado=null;
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;

        con=this.getConexion();
        
        try {
            
        stmUsuario=con.prepareStatement("SELECT codigo FROM iniciosesion WHERE usuario=? ");
        stmUsuario.setString(1, usuario);//esto sirve para darle los valores a las interrogaciones
        rsUsuario=stmUsuario.executeQuery();
        
        if(rsUsuario.next()){
            resultado=rsUsuario.getString("codigo");
        }
        
        }catch(SQLException e){
            
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            
        }
        return resultado;
    }
}
