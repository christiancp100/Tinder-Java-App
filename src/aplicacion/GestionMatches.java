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
public class GestionMatches {
    
    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionMatches(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }
    
    public ArrayList<Cliente> consultarMatches(Usuario usuario){
        return fbd.consultarMatches(usuario);
    }
    
    
}
