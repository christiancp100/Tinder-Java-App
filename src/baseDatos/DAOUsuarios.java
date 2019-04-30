/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Administrador;
import aplicacion.Cliente;
import aplicacion.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alumnogreibd
 */
public class DAOUsuarios extends AbstractDAO{
    
    public DAOUsuarios (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    
    //funcion para validar si las credenciales de un usuario son las correctas
    public Usuario validarUsuario(String idUsuario, String clave){
        Usuario resultado=null;
        Connection con;
        PreparedStatement stmUsuario=null;
        ResultSet rsUsuario;

        con=this.getConexion();
        try {
        stmUsuario=con.prepareStatement("select nombreusuario, nombre, email, fechanacimiento, sexo, orientacion,"
                + " provincia, so_favorito, lenguaje_fav, descripcion, sexo IS NULL as esAdministrador " //Algunos valores son nulos solo para administradores
                + " from (usuario FULL JOIN cliente ON usuario.nombreusuario = cliente.usuario FULL JOIN "
                + " administrador ON usuario.nombreusuario = administrador.usuario) as t " + //Junta todas las tablas en una 
                    "where nombreusuario = ? and contraseña = crypt(?,contraseña) ");
        stmUsuario.setString(1, idUsuario);
        stmUsuario.setString(2, clave);
        rsUsuario=stmUsuario.executeQuery();
         
        if (rsUsuario.next())
        {
            if(rsUsuario.getBoolean("esAdministrador")){
                resultado = new Administrador(
                    rsUsuario.getString("nombreusuario"),
                    rsUsuario.getString("nombre"), 
                    rsUsuario.getString("email"));
            }
            else{
                resultado = new Cliente(
                    rsUsuario.getString("nombreusuario"),
                    rsUsuario.getString("nombre"), 
                    rsUsuario.getString("email"),
                    rsUsuario.getString("descripcion"),
                    rsUsuario.getString("lenguaje_fav"),
                    rsUsuario.getString("so_favorito"),
                    rsUsuario.getDate("fechanacimiento"),
                    rsUsuario.getString("sexo"),
                    rsUsuario.getString("orientacion"),
                    rsUsuario.getString("provincia"));
            }

        }
        else{
            //System.out.println("nada majo"); //Cosas de Christian
        }
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
    
    //True si hay un reporte aprobado contra u
    public boolean estaBaneado(Usuario u){
        Connection con;
        PreparedStatement stm = null;
        ResultSet rs;
        boolean resultado = false;

        con = this.getConexion();

        try {
            //Comprueba si hay reportes aprobados contra u
            stm = con.prepareStatement("select count(*) > 0 " +
                                        "from revisar " +
                                        "where reportado = ?" +
                                        "and aprobado = true");
            stm.setString(1, u.getNombreUsuario());
            rs = stm.executeQuery();
            rs.next();
            resultado = rs.getBoolean(1);
            
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
        
        return resultado;
    }
}
