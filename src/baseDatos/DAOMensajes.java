/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

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
        PreparedStatement msgStm = null;
        ResultSet rs;
        ArrayList<Mensaje> listaMensajes = new ArrayList<>();
        con = this.getConexion();

        try {
            //Busca usuarios afines
            msgStm = con.prepareStatement(
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
            msgStm.setString(1, nombreUsuario);
            msgStm.setString(2, nombreUsuario2);
            msgStm.setString(3, nombreUsuario);
            msgStm.setString(4, nombreUsuario2);
            rs = msgStm.executeQuery();
            
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
                msgStm.close(); //Cierra cursores
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        
        return listaMensajes;
    }

    /**
     *
     * @param autor del mensaje
     * @param receptor del mensaje enviado
     * @param mensaje que se desea enviar
     */
    public void enviarMensaje(String autor, String receptor, String mensaje){
        Connection con;
        PreparedStatement msgStm = null;
        PreparedStatement stmCheck = null;
        ArrayList<String> usuariosInvolucrados = new ArrayList();
        con = this.getConexion();

        try {
            con.setAutoCommit(false);
            
            stmCheck = con.prepareStatement(
                    "SELECT usuario1, usuario2 "
                    + "FROM matches WHERE (usuario1 = ? OR usuario2 = ? ) "
                    + "AND (usuario1 = ? OR usuario2 = ?)"
                   
            );                    
            stmCheck.setString(1, autor);
            stmCheck.setString(2, autor);
            stmCheck.setString(3, receptor);
            stmCheck.setString(4, receptor);
            
            ResultSet rsOrdenUsuarios = stmCheck.executeQuery();
            while(rsOrdenUsuarios.next()){
                usuariosInvolucrados.add(rsOrdenUsuarios.getString("usuario1"));
                usuariosInvolucrados.add(rsOrdenUsuarios.getString("usuario2"));
                break;
            }
            
            try{

                //Busca usuarios afines
                msgStm = con.prepareStatement(
                       "INSERT INTO mensaje(usuario1, usuario2, fecha, autor, texto) "+
                       "VALUES (?, ?, now(), ?, ?);"
                );

                msgStm.setString(1, usuariosInvolucrados.get(0));
                msgStm.setString(2, usuariosInvolucrados.get(1));
                msgStm.setString(3, autor);
                msgStm.setString(4, mensaje);

                msgStm.executeUpdate();
                
                con.commit();

            }catch(SQLException e){
                System.out.println(e.getMessage());
                this.getFachadaAplicacion().muestraExcepcion(e.getMessage() + "\nSe produce RollBack");
                try{
                    con.rollback();
                }catch(SQLException ex){
                    this.getFachadaAplicacion().muestraExcepcion("Fallo en el RollBack");
                }
            }finally{
                try {
                    msgStm.close(); //Cierra cursores
                } catch (SQLException e) {
                    System.out.println("Imposible cerrar cursores");
                }
            }
     
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                con.setAutoCommit(false);
                stmCheck.close(); //Cierra cursores
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        
    }

}
