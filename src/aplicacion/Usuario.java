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
    private String contraseña;
    //private String contrasena; //ía ser boa
    private String descripcion;
    private String lenguajeProgFav;
    private String SOFav;
    private Date fechaNacimiento;
    private String sexo;
    private String orientacionSexual;
    private String provincia;

    public Usuario(String nombreUsuario, String nombre, String email,String contraseña, String descripcion, String lenguajeProgFav, String SOFav, Date fechaNacimiento, String sexo, String orientacionSexual, String provincia) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.descripcion = descripcion;
        this.lenguajeProgFav = lenguajeProgFav;
        this.SOFav = SOFav;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.orientacionSexual = orientacionSexual;
        this.provincia = provincia;
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
    
        
    public String getContraseña(){
        return this.contraseña;
    }
    
    public void setContraseña(String contraseña){
        this.contraseña = contraseña;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLenguajeProgFav() {
        return lenguajeProgFav;
    }

    public void setLenguajeProgFav(String lenguajeProgFav) {
        this.lenguajeProgFav = lenguajeProgFav;
    }

    public String getSOFav() {
        return SOFav;
    }

    public void setSOFav(String SOFav) {
        this.SOFav = SOFav;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getOrientacionSexual() {
        return orientacionSexual;
    }

    public void setOrientacionSexual(String orientacionSexual) {
        this.orientacionSexual = orientacionSexual;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
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