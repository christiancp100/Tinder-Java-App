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

/**
 *
 * @author alumnogreibd
 */
public class DAORevisar extends AbstractDAO{
    public DAORevisar (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public void insertarRevision(Reporte reporte,String admin,boolean resolucion){
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;

        con=this.getConexion();
        
        try {
            
        stmUsuario=con.prepareStatement("INSERT INTO revisar(administrador,denunciante,reportado,fechareporte,aprobado) VALUES (?,?,?,?,?)");
        stmUsuario.setString(1, admin);//esto sirve para darle los valores a las interrogaciones
        stmUsuario.setString(2, reporte.getDenunciante());//esto sirve para darle los valores a las interrogaciones
        stmUsuario.setString(3, reporte.getReportado());//esto sirve para darle los valores a las interrogaciones
        stmUsuario.setDate(4, reporte.getFecha());
        stmUsuario.setBoolean(5, resolucion);
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
}
