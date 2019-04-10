/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Usuario;
import java.sql.*;

/**
 *
 * @author Palmiro
 */
public class DAOMeGusta extends AbstractDAO {

    public DAOMeGusta(Connection conexion, aplicacion.FachadaAplicacion fa) {
        super.setConexion(conexion);
        super.setFachadaAplicacion(fa);
    }

    //Inserta MeGusta o NoMeGusta y crea Match si es necesario
    public void insertarGusta(Usuario dador, Usuario receptor, boolean gusta) {
        Connection con;
        PreparedStatement stmGusta = null;
        PreparedStatement stmGustado = null;
        PreparedStatement stmMatch = null;
        ResultSet rs;

        con = this.getConexion();

        try {
            con.setAutoCommit(false); //Deshabilita autocommit
            //Añade MeGusta o NoMeGusta
            stmGusta = con.prepareStatement("insert into megusta(usuario1,usuario2,gusta,esSuperlike) "
                    + "values (?,?,?,false)");
            stmGusta.setString(0, dador.getNombreUsuario());
            stmGusta.setString(1, receptor.getNombreUsuario());
            stmGusta.setBoolean(2, gusta);
            stmGusta.executeUpdate();

            //Si es MeGusta y el dador fue gustado crea un match
            if (gusta) {
                //Busca si el dador fue gustado
                stmGustado = con.prepareStatement("select count(*) from megusta where usuario1=? "
                        + "and usuario2=? and gusta=true");
                stmGustado.setString(0, receptor.getNombreUsuario());
                stmGustado.setString(1, dador.getNombreUsuario());
                rs = stmGustado.executeQuery();
                rs.next();
                //Si fue gustado crea un match
                if (rs.getInt(0) > 0) {
                    stmMatch = con.prepareStatement("insert into matches(usuario1,usuario2) "
                            + "values (?,?)");
                    stmMatch.setString(0, dador.getNombreUsuario());
                    stmMatch.setString(1, receptor.getNombreUsuario());
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
    public void insertarSuperlike(Usuario dador, Usuario receptor) {
        Connection con;
        PreparedStatement stmGusta = null;
        PreparedStatement stmMatch = null;

        con = this.getConexion();

        try {
            con.setAutoCommit(false); //Deshabilita autocommit
            //Añade Superlike
            stmGusta = con.prepareStatement("insert into megusta(usuario1,usuario2,gusta,esSuperlike) "
                    + "values (?,?,true,true)");
            stmGusta.setString(0, dador.getNombreUsuario());
            stmGusta.setString(1, receptor.getNombreUsuario());
            stmGusta.executeUpdate();

            //Crea match (al ser Superlike se crea siempre)
            stmMatch = con.prepareStatement("insert into matches(usuario1,usuario2) "
                    + "values (?,?)");
            stmMatch.setString(0, dador.getNombreUsuario());
            stmMatch.setString(1, receptor.getNombreUsuario());
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
}
