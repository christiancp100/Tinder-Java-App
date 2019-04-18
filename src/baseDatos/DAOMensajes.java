/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Cliente;
import aplicacion.Mensaje;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author christiancp
 */
public class DAOMensajes extends AbstractDAO{
    
    public DAOMensajes(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

        
    /**
     *
     * @param nombreUsuario Este es el nombre del usuario que tiene la sesion iniciada
     * @param nombreUsuario2 Este es el nombre del usuario al que entramos en su chat
     * @return lista de mensajes intercambiados entre los usuarios
     */
    public ArrayList<Mensaje> consultarMensajes(String nombreUsuario, String nombreUsuario2){
        Connection con;
        PreparedStatement stm = null;
        ResultSet rs;
        ArrayList<Mensaje> listaMensajes = new ArrayList<>();
        con = this.getConexion();

        try {
            //Busca usuarios afines
            stm = con.prepareStatement(
                    "select * from\n" +
                    "(select m.texto, m.autor, m.id " +
                    "from mensaje as m " +
                    "join (usuario join cliente on usuario.nombreusuario = cliente.usuario) as u on u.nombreusuario = m.usuario2 " +
                    "where m.usuario1 = ? AND m.usuario2 = ? " +
                    "union\n" +
                    "select m.texto, m.autor, m.id " +
                    "from mensaje as m " +
                    "join (usuario join cliente on usuario.nombreusuario = cliente.usuario) as u on u.nombreusuario = m.usuario1 " +
                    "where m.usuario2 = ? AND m.usuario1 = ?)  " +
                    "AS c " +
                    "ORDER BY c.id ASC"
            );
            stm.setString(1, nombreUsuario);
            stm.setString(2, nombreUsuario2);
            stm.setString(3, nombreUsuario);
            stm.setString(4, nombreUsuario2);
            rs = stm.executeQuery();
            
            //Crea lista de los mensajes devueltos
            while(rs.next()){
                    Mensaje m = new Mensaje(
                            rs.getString("autor"),
                            rs.getString("texto"),
                            rs.getInt("id")
                    );
                    
                    listaMensajes.add(m);
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
        
        return listaMensajes;
    }
}
