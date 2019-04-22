/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

/**
 *
 * @author christiancp
 */
public class Mensaje {
    private String usuario1, usuario2;
    private String usuarioAutor;
    private String texto;
    private int id;

    public Mensaje(String usuario1, String usuario2, String usuarioAutor, String texto, int id) {
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.usuarioAutor = usuarioAutor;
        this.texto = texto;
        this.id = id;
    }
    
    public String getUsuario1(){
        return this.usuario1;
    }
    
    public String getUsuario2(){
        return this.usuario2;
    }

    public String getUsuarioAutor() {
        return usuarioAutor;
    }

    public void setUsuarioAutor(String usuarioAutor) {
        this.usuarioAutor = usuarioAutor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
