/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Cliente;
import aplicacion.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author christiancp
 */
public class DAOMatches extends AbstractDAO{
    
    public DAOMatches(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public ArrayList<Cliente> consultarMatches(Usuario usuario){
        Connection con;
        PreparedStatement stm = null;
        ResultSet rs;
        ArrayList<Cliente> listaMatches = new ArrayList<>();
        con = this.getConexion();

        try {
            //Busca usuarios afines
            stm = con.prepareStatement(
            "select u.usuario, u.nombre, u.email, u.descripcion, u.lenguaje_fav, u.so_favorito, u.fechanacimiento, u.sexo, u.orientacion, u.provincia " +
            "from matches as m " +
            "join (usuario join cliente on usuario.nombreusuario = cliente.usuario) as u " +
            "on u.nombreusuario = m.usuario2 " +
            "where m.usuario1 = ? " +
            "union " +
            "select u.usuario, u.nombre, u.email, u.descripcion, u.lenguaje_fav, u.so_favorito, u.fechanacimiento, u.sexo, u.orientacion, u.provincia " +
            "from matches as m " +
            "join (usuario join cliente on usuario.nombreusuario = cliente.usuario) as u " +
            "on u.nombreusuario = m.usuario1 " +
            "where m.usuario2 = ?");
            
            stm.setString(1, usuario.getNombreUsuario() );
            stm.setString(2, usuario.getNombreUsuario() );
            
            rs = stm.executeQuery();
            
            //Crea lista de los usuarios devueltos
            while(rs.next()){
                //String nombreUsuario, String nombre, String email, String descripcion, String lenguajeProgFav, String SOFav, Date fechaNacimiento, String sexo, String orientacionSexual, String provincia
                Cliente c = new Cliente(
                        rs.getString("usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("descripcion"),
                        rs.getString("lenguaje_fav"),
                        rs.getString("so_favorito"),
                        rs.getDate("fechanacimiento"),
                        rs.getString("sexo"),
                        rs.getString("orientacion"),
                        rs.getString("provincia")
                );
                listaMatches.add(c);
            }
            
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stm.close(); //Cierra cursores
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        
        return listaMatches;
    }
    
    
    public void eliminarMatch(String usuario1, String usuario2){
        
        String consultarMatch = "SELECT count(*) > 0 FROM matches WHERE "
                + " (usuario1 = ? OR usuario2 = ?) "
                + "AND (usuario1 = ? OR usuario2 = ?)";
        
        String consultaEliminar = "DELETE FROM matches WHERE"
                + " (usuario1 = ? OR usuario2 = ?) "
                + "AND (usuario1 = ? OR usuario2 = ?)";
        
        Connection con;
        Boolean hayMatch;
        PreparedStatement stm = null;
        ResultSet rs = null;
        con = this.getConexion();

        try {
            con.setAutoCommit(false);
            //Busca usuarios afines
            
            stm = con.prepareStatement(consultarMatch);
            stm.setString(1, usuario1);
            stm.setString(2, usuario1);
            stm.setString(3, usuario2);
            stm.setString(4, usuario2);
            rs = stm.executeQuery();
            rs.next();
            if(hayMatch = rs.getBoolean(1)){
                stm = con.prepareStatement(consultaEliminar);
                stm.setString(1, usuario1);
                stm.setString(2, usuario1);
                stm.setString(3, usuario2);
                stm.setString(4, usuario2);
                stm.executeUpdate();
            }
            
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException ex){
                this.getFachadaAplicacion().muestraExcepcion("Fallo en el RollBack");
            }
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stm.close(); //Cierra cursores
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
            try{
                con.setAutoCommit(true);
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            
        }
        
        
    }
}
