/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author alumnogreibd
 */
public class Reporte {
    private String denunciante;
    private String reportado;
    private Timestamp fecha; //mirar el tipo de dato que maneja la fecha
    private String motivo;

    public Reporte(String denunciante, String reportado, Timestamp fecha, String motivo) {
        this.denunciante = denunciante;
        this.reportado = reportado;
        this.fecha = fecha;
        this.motivo = motivo;
    }

    public String getDenunciante() {
        return denunciante;
    }

    public void setDenunciante(String denunciante) {
        this.denunciante = denunciante;
    }

    public String getReportado() {
        return reportado;
    }

    public void setReportado(String reportado) {
        this.reportado = reportado;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

  
    
    
    
}
