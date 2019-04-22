/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDatos;

import aplicacion.Cliente;
import gui.VPrincipal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.postgresql.PGNotification;

/**
 *
 * @author christiancp
 */
public class Listener extends Thread{
    
    private Connection conn;
    private org.postgresql.PGConnection pgconn;
    private VPrincipal vp;

    public Listener(Connection conn, VPrincipal vp) throws SQLException {
            this.conn = conn;
            this.pgconn = (org.postgresql.PGConnection)conn;
            this.vp = vp;
            Statement stmt = conn.createStatement();
            stmt.execute("LISTEN mymessage");
            stmt.close();
    }

    @Override
    public void run() {

        while (true) {
            try {
                try ( // issue a dummy query to contact the backend
                // and receive any pending notifications.
                    Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT 1");
                    rs.close();
                    stmt.close();
                }catch(SQLException e){
                    System.out.println(e.getMessage());
                }
                
                org.postgresql.PGNotification notifications[] = pgconn.getNotifications();
                if (notifications != null) {
                    for (int i=0; i<notifications.length; i++) {
                        vp.actualizarMensajes();
                    }
                }

                // wait a while before checking again for new
                // notifications
                Thread.sleep(1000);
            } catch (SQLException sqle) {
                    sqle.printStackTrace();
            } catch (InterruptedException ie) {
                    ie.printStackTrace();
            }
        }
    }

}
