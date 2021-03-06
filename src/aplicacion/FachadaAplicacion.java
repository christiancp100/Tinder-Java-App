/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import baseDatos.Listener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.ImageIcon;


/**
 *
 * @author basesdatos
 */
public class FachadaAplicacion {

    gui.FachadaGui fgui;
    baseDatos.FachadaBaseDatos fbd;
    GestionUsuarios cu;
    GestionMatches gMatches;
    GestionMensajes gMensajes;
    GestionMeGusta gm;


    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new baseDatos.FachadaBaseDatos(this);
        cu = new GestionUsuarios(fgui, fbd);
        gMatches = new GestionMatches(fgui, fbd);
        gMensajes = new GestionMensajes(fgui, fbd);
        gm = new GestionMeGusta(fgui, fbd);
    }
    
    public baseDatos.FachadaBaseDatos getFbd(){
        return this.fbd;
    }

    public void iniciaInterfazUsuario(){
        fgui.iniciaVista();
    }

    public void muestraExcepcion(String e) {
        fgui.muestraExcepcion(e);
    }
    
    public void muestraMensaje(String e) {
        fgui.muestraMensaje(e);
    }
    
    public Usuario validarUsuario(String idUsuario, String clave){
        Usuario u = cu.validarUsuario(idUsuario, clave);
        //Si no existe un usuario ya se devuelve un fallo en VAutentificacion
        //if(u == null)
        //    this.muestraExcepcion("Has introducido mal tus credenciales");
        return u;
    }
    
    //True si hay un reporte aprobado contra u
    public boolean estaBaneado(Usuario u){
        return cu.estaBaneado(u);
    }
    
    public void registrarInicio(String usuario){
        fbd.registrarInicio(usuario);
    }
    
    //Devuelve los matches de un usuario
    public ArrayList<Cliente> consultarMatches(Usuario u){
        ArrayList<Cliente> matches;
        matches = gMatches.consultarMatches(u);
        for(Cliente cl : matches){
            cl.setFotos(this.obtenerFotos(cl));
            if(cl.getFotos().isEmpty()){
                ImageIcon img = new ImageIcon(getClass().getResource("/Imagenes/no_image.png"));
                Foto foto = new Foto("no image", img);
                cl.setFotos(foto);
 
            }
 
        }
        return matches;
    }
    
    public void eliminarMatch(String usuario1, String usuario2){
        gMatches.deshacerMatch(usuario1, usuario2);
    }
    
    //Devuelve los mensajes intercambiados por 2 usuarios
        
    /**
     *
     * @param u1 Este es el nombre del usuario que tiene la sesion iniciada
     * @param u2 Este es el nombre del usuario al que entramos en su chat
     * @return lista de mensajes intercambiados entre los usuarios
     */
    public ArrayList<Mensaje> consultarMensajes(String u1, String u2){
        return gMensajes.consultarMensajes(u1, u2);
    }
    
    /**
     * 
     * @param autor del mensaje enviado
     * @param receptor del mensaje
     * @param mensaje que se desea enviar
     */
    public void enviarMensaje(String autor, String receptor, String mensaje){
        gMensajes.enviarMensaje(autor, receptor, mensaje);
    }
    
    /**
     * 
     * @param usuario1 Hay que respetar el orden de la tabla de sql
     * @param usuario2 Hay que respetar el orden de la tabla de sql
     * @param id del mensaje
     */
    public void eliminarMensaje(String usuario1, String usuario2, String autor, Integer id){
        gMensajes.eliminarMensaje(usuario1, usuario2, autor, id);
    }

    //Lista de fotos de un cliente
    public ArrayList<Foto> obtenerFotos(Cliente c){
        return cu.obtenerFotos(c);
    }
    
    //Lista de usuarios con orientación, localización... compatibles con el interesado
    //que no se hayan visto aún
    public ArrayList<Cliente> consultarUsuariosCompatibles(Cliente interesado){
        return gm.consultarUsuariosCompatibles(interesado);
    }
    
    //Inserta MeGusta o NoMeGusta y crea Match si es necesario
    public void insertarGusta(Cliente dador, Cliente receptor, boolean gusta) {
        gm.insertarGusta(dador, receptor, gusta);
    }

    //Inserta Superlike y crea Match
    public void insertarSuperlike(Cliente dador, Cliente receptor) {
        gm.insertarSuperlike(dador, receptor);
    }

    //False si no le quedan Superlikes hoy
    public boolean puedeDarSuperlike(Cliente u){
        return gm.puedeDarSuperlike(u);
    }
    
    //Elimina el último MeGusta dado
    //True si se ha deshecho el MeGusta (no hay un match ya)
    public boolean deshacerMeGusta(Cliente u){
        return gm.deshacerMeGusta(u);
    }
    //Devuelve el código de condirmación para el inicio de sesión
    public String obtenerCodigo(String usuario){
        return this.fbd.obtenerCodigo(usuario);
    }
    public void insertarReporte(String denunciante,String reportado,String descripcion){
        this.fbd.insertarReporte(denunciante, reportado, descripcion);
    }
    public ArrayList<Reporte> consultarReportes(){
        return this.fbd.consultarReportes();
    }
    public void insertarRevision(Reporte reporte,String admin,boolean resolucion){
        this.fbd.insertarRevision(reporte, admin, resolucion);
    }
    
    //Estadisticas
    
    public int numPersonasBaneadas(){
        return this.fbd.numPersonasBaneadas();
    }
    public int numReporterAprobadosAUnaPersona(String nombre){
        return this.fbd.numReporterAprobadosAUnaPersona(nombre);
    }
    
    public Time tiempoHastaPrimerMensaje(){
        return this.fbd.tiempoHastaPrimerMensaje();
    }
    
    public ArrayList<String> usuariosPorOrientacionBaneados(){
        return this.fbd.usuariosPorOrientacionBaneados();
    }

    public ArrayList<String> iniciosConversacionConPalabra(String palabra){
        return this.fbd.iniciosConversacionConPalabra(palabra);
    }
    
    public ArrayList<String> personasQueConsiguierenMatchMientrasPagaban(){
        return this.fbd.personasQueConsiguierenMatchMientrasPagaban();
    }
}
