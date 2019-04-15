package aplicacion;

import java.sql.Date;

/**
 *
 * @author Palmiro
 */
public class Usuario {

    private String nombreUsuario;
    private String nombre;
    private String email;
    //private String contraseña; //ía ser boa

    public Usuario(String nombreUsuario, String nombre, String email) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //Usuarios iguales si coincide su nick
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        return this.nombreUsuario.equals(other.getNombreUsuario());
    }
}
