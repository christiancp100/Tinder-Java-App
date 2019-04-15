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
    
    public Boolean comprobarAutentificacion(String idUsuario, String clave){
        Boolean aut;
        if(!(aut = cu.comprobarAutentificacion(idUsuario, clave)))
            this.muestraExcepcion("Has introducido mal tus credenciales");
        return aut;

    }
    

    public void registrar_inicio(String usuario){
        fbd.registrar_inicio(usuario);
    }
    
    public Usuario validarUsuario(String idUsuario, String clave){
        return this.fbd.validarUsuario(idUsuario, clave);
    }
}
