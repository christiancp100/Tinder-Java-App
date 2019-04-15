/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


/**
 *
 * @author alumno
 */
public class FachadaGui {

    aplicacion.FachadaAplicacion fa;
    VPrincipal vp;

    public FachadaGui(aplicacion.FachadaAplicacion fa) {
        this.fa = fa;
        this.vp = new VPrincipal();
    }

    public void iniciaVista() {
        VAutentificacion va;
        va = new VAutentificacion(vp, true, fa,vp);
        va.setVisible(true);
    }

    public void muestraExcepcion(String txtExcepcion) {
        VAviso va;

        va = new VAviso(vp, true, txtExcepcion);
        va.setVisible(true);
    }
    
    public void muestraMensaje(String mensaje) {
        VAviso va;

        va = new VAviso(vp, true, mensaje, false);
        va.setVisible(true);
    }

    
}