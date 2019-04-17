/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.util.ArrayList;


/**
 *
 * @author basesdatos
 */
public class FachadaAplicacion {

    gui.FachadaGui fgui;
    baseDatos.FachadaBaseDatos fbd;
    GestionUsuarios cu;
    GestionMeGusta gm;


    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new baseDatos.FachadaBaseDatos(this);
        cu = new GestionUsuarios(fgui, fbd);
        gm = new GestionMeGusta(fgui, fbd);
    }

    public void iniciaInterfazUsuario() {
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
    
    public void registrar_inicio(String usuario){
        fbd.registrar_inicio(usuario);
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
    public void deshacerMeGusta(Cliente u){
        gm.deshacerMeGusta(u);
    }
}
