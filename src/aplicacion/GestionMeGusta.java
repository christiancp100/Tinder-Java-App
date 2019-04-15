
package aplicacion;

import gui.FachadaGui;
import baseDatos.FachadaBaseDatos;
import java.util.ArrayList;

/**
 *
 * @author Palmiro
 */
public class GestionMeGusta {

    FachadaGui fgui;
    FachadaBaseDatos fbd;

    public GestionMeGusta(FachadaGui fgui, FachadaBaseDatos fbd) {
        this.fgui = fgui;
        this.fbd = fbd;
    }

    
    //Lista de usuarios con orientación, localización... compatibles con el interesado
    //que no se hayan visto aún
    public ArrayList<Cliente> consultarUsuariosCompatibles(Cliente interesado){
        return fbd.consultarUsuariosCompatibles(interesado);
    }
    
    //Inserta MeGusta o NoMeGusta y crea Match si es necesario
    public void insertarGusta(Cliente dador, Cliente receptor, boolean gusta) {
        fbd.insertarGusta(dador, receptor, gusta);
    }

    //Inserta Superlike y crea Match
    public void insertarSuperlike(Cliente dador, Cliente receptor) {
        fbd.insertarSuperlike(dador, receptor);
    }

    //False si no le quedan Superlikes hoy
    public boolean puedeDarSuperlike(Cliente u){
        return fbd.puedeDarSuperlike(u);
    }
    
    //Elimina el último MeGusta dado
    public void deshacerMeGusta(Cliente u){
        fbd.deshacerMeGusta(u);
    }
}
