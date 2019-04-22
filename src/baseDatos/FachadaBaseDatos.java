/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Cliente;
import aplicacion.Mensaje;
import aplicacion.Foto;
import aplicacion.Reporte;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import aplicacion.Usuario;
import java.sql.Connection;
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
    private DAOInicioSesion daoiniciosesion;
    private DAOMatches daoMatches;
    private DAOMensajes daoMensajes;
    private DAOFotos daoFotos;
    private DAOReportes daoReportes;
    private DAORevisar daoRevisar;


    public FachadaBaseDatos(aplicacion.FachadaAplicacion fa){

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
            daoMatches = new DAOMatches(conexion, fa);
            daoMensajes = new DAOMensajes(conexion, fa);
            daoFotos = new DAOFotos(conexion, fa);
            daoiniciosesion= new DAOInicioSesion(conexion,fa);
            daoReportes = new DAOReportes(conexion,fa);
            daoRevisar = new DAORevisar(conexion,fa);

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
    
    public Connection getConexion(){
        return this.conexion;
    }
    
    public Usuario validarUsuario(String idUsuario, String clave){
        return daoUsuarios.validarUsuario(idUsuario, clave);
    }

    //Lista de usuarios con orientación, localización... compatibles con el interesado
    //que no se hayan visto aún
    public ArrayList<Cliente> consultarUsuariosCompatibles(Cliente interesado){
        return daoMeGusta.consultarUsuariosCompatibles(interesado);
    }
    
    //Inserta MeGusta o NoMeGusta y crea Match si es necesario
    public void insertarGusta(Cliente dador, Cliente receptor, boolean gusta) {
        daoMeGusta.insertarGusta(dador, receptor, gusta);
    }

    //Inserta Superlike y crea Match
    public void insertarSuperlike(Cliente dador, Cliente receptor) {
        daoMeGusta.insertarSuperlike(dador, receptor);
    }

    //False si no le quedan Superlikes hoy
    public boolean puedeDarSuperlike(Cliente u){
        return daoMeGusta.puedeDarSuperlike(u);
    }
    
    //Elimina el último MeGusta dado
    public void deshacerMeGusta(Cliente u){
        daoMeGusta.deshacerMeGusta(u);
    }
    
    //Devuelve los matches de un usuario
    public ArrayList<Cliente> consultarMatches(Usuario u){
        return daoMatches.consultarMatches(u);
    }
    
    public void eliminarMatch(String usuario1, String usuario2){
        daoMatches.eliminarMatch(usuario1, usuario2);
    }
    
    //Devvuelve los mensajes de un usuario
    public ArrayList<Mensaje> consultarMensajes(String u1, String u2){
        return daoMensajes.consultarMensajes(u1, u2);
    }
    
    public void eliminarMensaje(String usuario1, String usuario2, String autor, Integer id){
        daoMensajes.eliminarMensaje(usuario1, usuario2, autor, id);
    }
    
    //Inserta un mensaje en la base de datos
    public void enviarMensaje(String autor, String receptor, String mensaje){
        daoMensajes.enviarMensaje(autor, receptor, mensaje);
    }
    
    //Función que escribe en el registro el usuario, la fecha que se escribe sola y el código utilizado
    public void registrarInicio(String usuario){
        daoiniciosesion.registrarInicio(usuario);
    }
    
    //Lista de fotos de un cliente
    public ArrayList<Foto> obtenerFotos(Cliente c){
        return daoFotos.obtenerFotos(c);
    }
    
    public String obtenerCodigo(String usuario){
        return this.daoiniciosesion.obtenerCodigo(usuario);
    }
    public void insertarReporte(String denunciante,String reportado,String descripcion){
        this.daoReportes.insertarReporte(denunciante, reportado, descripcion);
    }
    public ArrayList<Reporte> consultarReportes(){
        return this.daoReportes.consultarReportes();
    }
    public void insertarRevision(Reporte reporte,String admin,boolean resolucion){
        this.daoRevisar.insertarRevision(reporte, admin, resolucion);
    }
}
