/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

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
        stmUsuario=con.prepareStatement("select nombreusuario, nombre, email, fechanacimiento, genero, orientacion," +
                                        " provincia, so_favorito, lenguaje_fav, descripcion "+
                                        "from usuario, cliente c "+
                                        "where nombreusuario = ? and contraseña = crypt(?,contraseña) "+
                                        "AND nombreusuario = c.usuario ");
        stmUsuario.setString(1, idUsuario);//esto sirve para darle los valores a las interrogaciones
        stmUsuario.setString(2, clave);
        rsUsuario=stmUsuario.executeQuery();
         
        if (rsUsuario.next())
        {
      
            resultado = new Usuario(
                    rsUsuario.getString("nombreusuario"),
                    rsUsuario.getString("nombre"), 
                    rsUsuario.getString("email"),
                    //rsUsuario.getString("contraseña"),
                    rsUsuario.getString("descripcion"),
                    rsUsuario.getString("lenguaje_fav"),
                    rsUsuario.getString("so_favorito"),
                    rsUsuario.getDate("fechanacimiento"),
                    rsUsuario.getString("genero"),
                    rsUsuario.getString("orientacion"),
                    rsUsuario.getString("provincia")
            );

        }
        else{
            System.out.println("nada majo");
        }
        } catch (SQLException e){
          System.out.println(e.getMessage());
          this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        }finally{
          try {stmUsuario.close();} catch (SQLException e){System.out.println("Imposible cerrar cursores");}
        }
        return resultado;
    }
}
