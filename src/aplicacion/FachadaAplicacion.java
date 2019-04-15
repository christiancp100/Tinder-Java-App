/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;


/**
 *
 * @author basesdatos
 */
public class FachadaAplicacion {

    gui.FachadaGui fgui;
    baseDatos.FachadaBaseDatos fbd;
    GestionUsuarios cu;


    public FachadaAplicacion() {
        fgui = new gui.FachadaGui(this);
        fbd = new baseDatos.FachadaBaseDatos(this);
        cu = new GestionUsuarios(fgui, fbd);
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
    
}
