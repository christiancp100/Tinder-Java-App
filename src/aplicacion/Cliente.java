
package aplicacion;

import java.sql.Date;

/**
 *
 * @author Palmiro
 */
public class Cliente extends Usuario {
    private String descripcion;
    private String lenguajeProgFav;
    private String SOFav;
    private Date fechaNacimiento;
    private String sexo;
    private String orientacionSexual;
    private String provincia;

    public Cliente(String nombreUsuario, String nombre, String email, String descripcion, String lenguajeProgFav, String SOFav, Date fechaNacimiento, String sexo, String orientacionSexual, String provincia) {
        super(nombreUsuario, nombre, email);
        this.descripcion = descripcion;
        this.lenguajeProgFav = lenguajeProgFav;
        this.SOFav = SOFav;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.orientacionSexual = orientacionSexual;
        this.provincia = provincia;
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


    
    
}
