
package baseDatos;

import aplicacion.Cliente;
import aplicacion.Foto;
import aplicacion.Usuario;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Palmiro
 */
public class DAOFotos extends AbstractDAO {

    public DAOFotos(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    //Lista de fotos de un cliente
    public ArrayList<Foto> obtenerFotos(Cliente c){
        Connection con;
        PreparedStatement stm = null;
        ResultSet rs;
        ArrayList<Foto> listaFotos = new ArrayList<>();

        con = this.getConexion();

        try {
            //Busca usuarios afines
            stm = con.prepareStatement("select id, descripcion, contenido " +
                    "from fotos as f " +
                    "where f.usuario = ? " + //0
                    "");
            stm.setString(1, c.getNombreUsuario());
            rs = stm.executeQuery();
            
            //Crea lista de fotos
            while(rs.next()){
                listaFotos.add(new Foto(
                        //rs.getInt("id"), //Por ahora no se almacena ID de foto
                        rs.getString("descripcion"),
                        rs.getBytes("contenido")));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stm.close(); //Cierra cursores
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        
        return listaFotos;
    }
    
}
