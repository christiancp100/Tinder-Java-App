/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import aplicacion.Usuario;
import java.util.ArrayList;

/**
 *
 * @author basesdatos
 */
public class FachadaBaseDatos {

    private aplicacion.FachadaAplicacion fa;
    private java.sql.Connection conexion;
    private DAOUsuarios daoUsuarios;
    private DAOMeGusta daoMeGusta;


    public FachadaBaseDatos(aplicacion.FachadaAplicacion fa) {

        Properties configuracion = new Properties();
        this.fa = fa;
        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            this.conexion = java.sql.DriverManager.getConnection("jdbc:" + gestor + "://"
                    + configuracion.getProperty("servidor") + ":"
                    + configuracion.getProperty("puerto") + "/"
                    + configuracion.getProperty("baseDatos"),
                    usuario);

            daoUsuarios = new DAOUsuarios(conexion, fa);
            daoMeGusta = new DAOMeGusta(conexion, fa);


        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
            fa.muestraExcepcion(f.getMessage());
        } catch (IOException i) {
            System.out.println(i.getMessage());
            fa.muestraExcepcion(i.getMessage());
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
            fa.muestraExcepcion(e.getMessage());
        }

    }
    
    public Usuario validarUsuario(String idUsuario, String clave){
        return daoUsuarios.validarUsuario(idUsuario, clave);
    }

    //Lista de usuarios con orientación, localización... compatibles con el interesado
    //que no se hayan visto aún
    public ArrayList<Usuario> consultarUsuariosCompatibles(Usuario interesado){
        return daoMeGusta.consultarUsuariosCompatibles(interesado);
    }
    
    //Inserta MeGusta o NoMeGusta y crea Match si es necesario
    public void insertarGusta(Usuario dador, Usuario receptor, boolean gusta) {
        daoMeGusta.insertarGusta(dador, receptor, gusta);
    }

    //Inserta Superlike y crea Match
    public void insertarSuperlike(Usuario dador, Usuario receptor) {
        daoMeGusta.insertarSuperlike(dador, receptor);
    }

    //False si no le quedan Superlikes hoy
    public boolean puedeDarSuperlike(Usuario u){
        return daoMeGusta.puedeDarSuperlike(u);
    }
    
    //Elimina el último MeGusta dado
    public void deshacerMeGusta(Usuario u){
        daoMeGusta.deshacerMeGusta(u);
    }
}
