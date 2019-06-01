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
 * Clase Conexion se encaga de gestionar la conexion con la base de datos
 *
 * @author EmilioMendez
 */
public class Conexion {

    private String urldb;
    private Connection conn;

    /**
     * Constructor de conexion con la base de datos
     *
     * @param urldb String ubicacion del archivo sqlite
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
}
