/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import gui.FachadaGui;
import baseDatos.FachadaBaseDatos;
import java.util.ArrayList;

/**
 *
 * @author basesdatos
 */
public class GestionUsuarios {

    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionUsuarios(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    public Usuario validarUsuario(String idUsuario, String clave) {
        return fbd.validarUsuario(idUsuario, clave);
    }
    
    //Lista de fotos de un cliente
    public ArrayList<Foto> obtenerFotos(Cliente c) {
        return fbd.obtenerFotos(c);
    }
}
