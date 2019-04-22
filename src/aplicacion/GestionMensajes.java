/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import baseDatos.FachadaBaseDatos;
import gui.FachadaGui;
import java.util.ArrayList;

/**
 *
 * @author christiancp
 */
public class GestionMensajes {
    
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionMensajes(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    /**
     *
     * @param u1 Este es el nombre del usuario que tiene la sesion iniciada
     * @param u2 Este es el nombre del usuario al que entramos en su chat
     * @return lista de mensajes intercambiados entre los usuarios
     */
    public ArrayList<Mensaje> consultarMensajes(String u1, String u2){
        return fbd.consultarMensajes(u1, u2);
    }
    
    public void enviarMensaje(String autor, String receptor, String mensaje){
        fbd.enviarMensaje(autor, receptor, mensaje);
    }
    
    public void eliminarMensaje(String usuario1, String usuario2, String autor, Integer id){
        fbd.eliminarMensaje(usuario1, usuario2, autor, id);
    }
    
}
