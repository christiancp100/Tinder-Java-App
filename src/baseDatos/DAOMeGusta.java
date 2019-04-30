
package baseDatos;

import aplicacion.Cliente;
import aplicacion.Usuario;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Palmiro
 */
public class DAOMeGusta extends AbstractDAO {

    public DAOMeGusta(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    //Lista de usuarios con orientación, localización... compatibles con el interesado
    //que no se hayan visto aún
    public ArrayList<Cliente> consultarUsuariosCompatibles(Cliente interesado){
        Connection con;
        PreparedStatement stm = null;
        ResultSet rs;
        ArrayList<Cliente> listaUsuarios = new ArrayList<>();
        con = this.getConexion();

        try {
            //Busca usuarios afines
            stm = con.prepareStatement("select nombreusuario, nombre, descripcion, "
                    + "lenguaje_fav, so_favorito, "
                    //+ "date_part('year',age(now(),fechanacimiento)) as edad," //por si se quiere devolver edad en vez de fecha
                    + "fechanacimiento, orientacion, sexo, provincia " +
                    "from usuario as u, cliente as c " +
                    "where u.nombreusuario = c.usuario " +
                    "and u.nombreusuario not in " +
                    "   (select usuario2 from megusta where usuario1 = ?) " + //no se ha visto aún //1
                    "and u.nombreusuario not in " +
                    "   (select usuario1 from megusta where usuario2 = ? and essuperlike = true) " + //no se le ha dado superlike a interesado //2
                    "and c.provincia = ? " + //3
                    "and u.nombreusuario <> ? " + //4
                    "and case ?" + //5
                    "	when 'heterosexual' then " +
                    "		c.orientacion = 'heterosexual' and c.sexo <> ?" + //6
                    "	when 'homosexual' then " +
                    "		c.orientacion='homosexual' and c.sexo = ?" + //7
                    "	when 'bisexual' then c.orientacion = 'bisexual'" +
                    "end");
            stm.setString(1, interesado.getNombreUsuario());
            stm.setString(2, interesado.getNombreUsuario());
            stm.setString(3, interesado.getProvincia());
            stm.setString(4, interesado.getNombreUsuario());
            stm.setString(5, interesado.getOrientacionSexual());
            stm.setString(6, interesado.getSexo());
            stm.setString(7, interesado.getSexo());
            rs = stm.executeQuery();
            
            //Crea lista de los usuarios devueltos
            while(rs.next()){
                listaUsuarios.add(new Cliente(
                        rs.getString("nombreusuario"),
                        rs.getString("nombre"),
                        null, //email
                        rs.getString("descripcion"),
                        rs.getString("lenguaje_fav"),
                        rs.getString("so_favorito"),
                        rs.getDate("fechanacimiento"),
                        rs.getString("sexo"),
                        rs.getString("orientacion"),
                        rs.getString("provincia")
                ));
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
        
        return listaUsuarios;
    }
    
    //Inserta MeGusta o NoMeGusta y crea Match si es necesario
    public void insertarGusta(Cliente dador, Cliente receptor, boolean gusta) {
        Connection con;
        PreparedStatement stmGusta = null;
        PreparedStatement stmGustado = null;
        PreparedStatement stmMatch = null;
        ResultSet rs;

        con = this.getConexion();

        try {
            stmGusta = con.prepareStatement("insert into megusta(usuario1,usuario2,gusta,esSuperlike) "
                    + "values (?,?,?,false)");
            stmGustado = con.prepareStatement("select count(*) from megusta where usuario1=? "
                        + "and usuario2=? and gusta=true");
            stmMatch = con.prepareStatement("insert into matches(usuario1,usuario2) "
                            + "values (?,?)");
            
            con.setAutoCommit(false); //Deshabilita autocommit
            //Añade MeGusta o NoMeGusta
            stmGusta.setString(1, dador.getNombreUsuario());
            stmGusta.setString(2, receptor.getNombreUsuario());
            stmGusta.setBoolean(3, gusta);
            stmGusta.executeUpdate();

            //Si es MeGusta y el dador fue gustado crea un match
            if (gusta) {
                //Busca si el dador fue gustado
                stmGustado.setString(1, receptor.getNombreUsuario());
                stmGustado.setString(2, dador.getNombreUsuario());
                rs = stmGustado.executeQuery();
                rs.next();
                //Si fue gustado crea un match
                if (rs.getInt(1) > 0) {
                    stmMatch.setString(1, dador.getNombreUsuario());
                    stmMatch.setString(2, receptor.getNombreUsuario());
                    stmMatch.executeUpdate();
                }

            }
            con.commit(); //Compromete la transacción

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            if (con != null) {
                try {
                    //Se intenta deshacer la transacción
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
                }
            }
        } finally {
            try {
                stmGusta.close(); //Cierra cursores
                stmGustado.close();
                stmMatch.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
            try {
                con.setAutoCommit(true); //Vuelve a habilitar el autocommit
            } catch (SQLException e) {
                System.out.println("Imposible habilitar autocommit");
            }
        }
    }

    //Inserta Superlike y crea Match
    public void insertarSuperlike(Cliente dador, Cliente receptor) {
        Connection con;
        PreparedStatement stmGusta = null;
        PreparedStatement stmMatch = null;

        con = this.getConexion();

        try {
            stmGusta = con.prepareStatement("insert into megusta(usuario1,usuario2,gusta,esSuperlike) "
                    + "values (?,?,true,true)");
            stmMatch = con.prepareStatement("insert into matches(usuario1,usuario2) "
                    + "values (?,?)");
            con.setAutoCommit(false); //Deshabilita autocommit
            //Añade Superlike
            stmGusta.setString(1, dador.getNombreUsuario());
            stmGusta.setString(2, receptor.getNombreUsuario());
            stmGusta.executeUpdate();

            //Crea match (al ser Superlike se crea siempre)
            stmMatch.setString(1, dador.getNombreUsuario());
            stmMatch.setString(2, receptor.getNombreUsuario());
            stmMatch.executeUpdate();

            con.commit(); //Compromete la transacción

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
            if (con != null) {
                try {
                    //Se intenta deshacer la transacción
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                    this.getFachadaAplicacion().muestraExcepcion(ex.getMessage());
                }
            }
        } finally {
            try {
                stmGusta.close(); //Cierra cursores
                stmMatch.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
            try {
                con.setAutoCommit(true); //Vuelve a habilitar el autocommit
            } catch (SQLException e) {
                System.out.println("Imposible habilitar autocommit");
            }
        }
    }
    
    //False si no le quedan Superlikes hoy
    public boolean puedeDarSuperlike(Cliente u){
        Connection con;
        PreparedStatement stm = null;
        ResultSet rs;
        boolean resultado = false;

        con = this.getConexion();

        try {
            //Comprueba si los superlikes dados hoy son mayores que los superlikes permitidos
            //(1 si no se tiene premium)
            stm = con.prepareStatement("select count(*) < COALESCE ((select superlikesdiarios " +
                                "	from usuario as u, cliente as c, premium as p " +
                                "	where u.nombreusuario = c.usuario " +
                                "	and c.usuario = p.usuario " +
                                "	and c.usuario = ? " + //0
                                "	and p.fechafin > now() " + //aún dura el premium
                                "	), 1) " + //escoge los superlikes contratados o 1 si no hay premium
                                "from megusta " +
                                "where usuario1 = ? " + //1
                                "and essuperlike = true " +
                                "and age(cast(fecha as timestamp)) < interval '1' day");
            stm.setString(1, u.getNombreUsuario());
            stm.setString(2, u.getNombreUsuario());
            rs = stm.executeQuery();
            rs.next();
            resultado = rs.getBoolean(1);
            
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
        
        return resultado;
    }
    
    //Elimina el último MeGusta dado
    //True si se ha deshecho el MeGusta (no hay un match ya)
    public boolean deshacerMeGusta(Cliente u){
        Connection con;
        PreparedStatement stmMatch = null;
        PreparedStatement stmDelete = null;
        ResultSet rs;
        boolean haBorrado = false; 
        
        con = this.getConexion();

        try {
            stmMatch = con.prepareStatement("select count(*) > 0 " +
                    "from matches " +
                    "where (usuario1 = ? and usuario2 = (SELECT usuario2 FROM megusta WHERE usuario1 = ? and fecha = (SELECT MAX(fecha) FROM megusta WHERE usuario1 = ?))) " +
                    "or (usuario2 = ? and usuario1 = (SELECT usuario2 FROM megusta WHERE usuario1 = ? and fecha=(SELECT MAX(fecha) FROM megusta WHERE usuario1 = ?)))");
            stmDelete = con.prepareStatement("DELETE FROM megusta " +
                            "WHERE usuario1 = ? AND fecha = "
                        + "(SELECT MAX(fecha) FROM megusta WHERE usuario1 = ?)");
            //Consulta si el último MeGusta ya es Match
            stmMatch.setString(1, u.getNombreUsuario());
            stmMatch.setString(2, u.getNombreUsuario());
            stmMatch.setString(3, u.getNombreUsuario());
            stmMatch.setString(4, u.getNombreUsuario());
            stmMatch.setString(5, u.getNombreUsuario());
            stmMatch.setString(6, u.getNombreUsuario());
            rs = stmMatch.executeQuery();
            rs.next();
            //Si no hay un match
            if (haBorrado = !rs.getBoolean(1)) {
                //Elimina el último MeGusta o NoMeGusta dado
                stmDelete.setString(1, u.getNombreUsuario());
                stmDelete.setString(2, u.getNombreUsuario());
                stmDelete.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            this.getFachadaAplicacion().muestraExcepcion(e.getMessage());
        } finally {
            try {
                stmMatch.close(); //Cierra cursores
                stmDelete.close();
            } catch (SQLException e) {
                System.out.println("Imposible cerrar cursores");
            }
        }
        return haBorrado;
    }
}
