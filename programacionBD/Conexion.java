package programacionBD;

/**
 *
 * @author EmilioMendez
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Conexion se encaga de gestionar el uso de la base de datos
 *
 * @author EmilioMendez
 */
public class Conexion {

    private String urldb;
    private Connection conn;

    /**
     * Constructor de conexion con la base de datos
     *
     * @param url String ubicacion del archivo sqlite
     */
    public Conexion(String urldb) {
        this.urldb = urldb;
    }

    /**
     * Getter para la clase Conexion
     *
     * @return Conexion
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * establece la conexion a la base de datos
     */
    public void conectar() {

        conn = null;

        try {
            String url = "jdbc:sqlite:" + urldb;
            conn = DriverManager.getConnection(url);
            System.out.println("Conexion establecida.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finaliza una conexion a la base de datos
     */
    public void desconectar() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection to SQLite has been close.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Devuelve el numero de tablas de una base de datos
     *
     * @return
     */
    public int numOfTables() {
        String sql = "SELECT name FROM  sqlite_master  WHERE type ='table' AND name NOT LIKE 'sqlite_%';";
        int count = 0;
        conectar();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            count = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            desconectar();
        }
        return count;
    }

}
