/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Reporte;
import java.sql.Connection;

/**
 *
 * @author alumnogreibd
 */
public class DAORevisar extends AbstractDAO{
    public DAORevisar (Connection conexion, aplicacion.FachadaAplicacion fa){
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }
    
    public void insertar_revision(Reporte reporte, boolean x){
        
    }
}
