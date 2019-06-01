package programacionBD;

/**
 *
 * @author EmilioMendez
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que define los metodos  a traves de los cuales podemos operar sobre 
 * la base de datos con el uso de comandos SQL 
 *
 * @author EmilioMendez
 */
public class AlmacenSQL {

    private static AlmacenSQL openInstance;

    /**
     * Metodo getter para obtener instancia abierta de la clase AlmacenSQL desde
     * otra clase
     *
     * @return openInstance;
     */
    public static AlmacenSQL getOpenInstance() {
        return openInstance;
    }

    private final Conexion conexion;
    private ResultSet rs;

    /**
     * Contructor del Almacen
     *
     * @param conexion - conexion vinculada a la base de datos 
     */
    public AlmacenSQL(Conexion conexion) {
        this.conexion = conexion;

    }

    /**
     * Crea las tablas proveedores, categorias, productos en la base de datos
     *
     */
    //metodo crearTablas()
    public void crearTablas() {
        String sql1 = "CREATE TABLE IF NOT EXISTS proveedores (\n"
                + "	id_proveedor integer PRIMARY KEY,\n"
                + "	nom_proveedor text NOT NULL,\n"
                + "	tel_proveedor text NOT NULL\n"
                + ");";

        String sql2 = "CREATE TABLE IF NOT EXISTS localizacion (\n"
                + "	id_localizacion integer PRIMARY KEY,\n"
                + "	cod_estanteria text NOT NULL\n"
                + ");";

        String sql3 = "CREATE TABLE IF NOT EXISTS productos (\n"
                + "	id_producto integer PRIMARY KEY,\n"
                + "	descrip_producto text NOT NULL,\n"
                + "	id_localizacion integer NOT NULL REFERENCES localizacion (id_localizacion),\n"
                + "	id_proveedor integer NOT NULL REFERENCES proveedores (id_proveedor)\n"
                + ");";

        conexion.conectar();

        try {
            Statement stmt = conexion.getConn().createStatement();
            stmt.addBatch(sql1);
            stmt.addBatch(sql2);
            stmt.addBatch(sql3);
            stmt.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Inserta un nuevo proveedor en la base de datos
     *
     * @param nomPv - nombre de provedor proporcionado por el usuario
     * @param telPv - telefono de provedor proporcionado por el usuario
     *
     * @return int
     */
    //metodo para insertar un Provedor en la base de datos
    public int insertarProveedor(String nomPv, String telPv) {

        String sql = "INSERT INTO proveedores VALUES(null,?,?)";
        conexion.conectar();
        int rows = 0;
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setString(1, nomPv);
            pstmt.setString(2, telPv);

            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rows = -1;
        } finally {
            conexion.desconectar();
        }
        return rows;
    }

    /**
     * Modifica un proveedor en la base de datos
     *
     * @param nomPv - nombre de provedor proporcionado por el usuario
     * @param telPv - telefono de provedor proporcionado por el usuario
     * @param idPv - id de provedor proporcionado por el usuario
     *
     * @return int
     */
    //metodo para modificar un Provedor en la base de datos
    public int modificarProveedor(String nomPv, String telPv, int idPv) {
        int rows = 0;
        String sql = "UPDATE proveedores SET nom_proveedor = '" + nomPv + "',tel_proveedor='" + telPv + "' WHERE id_proveedor = '" + idPv + "';";
        conexion.conectar();
        try (Statement stmt = conexion.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            rows = -1;
        } finally {
            conexion.desconectar();
        }
        return rows;
    }

    /**
     * Borra un Proveedor de la base de datos
     *
     * @param idPv - id de provedor proporcionado por el usuario
     * @return int 
     */
    public int borrarProveedor(int idPv) {
        int rows = 0;
        String sql = "DELETE FROM proveedores WHERE id_proveedor = '" + idPv + "';";
        conexion.conectar();
        try (Statement stmt = conexion.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            rows = -1;
        } finally {
            conexion.desconectar();
        }
        return rows;
    }

    /**
     * Devuelve el resultado de una visualizacion de la tabla Proveedores
     *
     *
     * @return ArrayList Object[]
     */
    public ArrayList<Object[]> mostrarProveedor() {
        ArrayList<Object[]> proveedores = new ArrayList<>();
        ResultSet rst = null;
        String sql = "SELECT * FROM proveedores";
        conexion.conectar();
        try (Statement stmt = conexion.getConn().createStatement()) {
            rst = stmt.executeQuery(sql);
            while (rst.next()) {
                Object[] fila = new Object[3];
                fila[0] = rst.getInt(1);
                fila[1] = rst.getString(2);
                fila[2] = rst.getString(3);
                proveedores.add(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlmacenSQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.desconectar();
        }
        System.out.println(proveedores.size());
        return proveedores;
    }

    /**
     * Inserta una nueva localizacion en la base de datos
     *
     * @param codEs - codigo de estanteria proporcionado por el usuario
     *
     * @return int
     *
     */
     //metodo para insertar una Localizacion en la base de datos
    public int insertarLocalizacion(String codEs) {

        String sql = "INSERT INTO localizacion VALUES(null,?)";

        conexion.conectar();
        int rows = 0;
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setString(1, codEs);
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rows = -1;
        } finally {
            conexion.desconectar();
        }
        return rows;

    }

    /**
     * Modifica una Localizacion en la base de datos
     *
     * @param codEs - codigo de estanteria proporcionado por el usuario 
     * @param idLo - id de localizacion proporcionado por el usuario
     *
     * @return int
     */
    //metodo para modificar una Localizacion en la base de datos
    public int modificarLocalizacion(String codEs, int idLo) {

        int rows = 0;
        String sql = "UPDATE localizacion SET cod_estanteria = '" + codEs + "' WHERE id_localizacion = '" + idLo + "';";
        conexion.conectar();
        try (Statement stmt = conexion.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            rows = -1;
        } finally {
            conexion.desconectar();
        }
        return rows;
    }

    /**
     * Borra una Localizacion de la base de datos
     *
     * @param idLo - id de localizacion proporcionado por el usuario
     *
     * @return int
     */
    public int borrarLocalizacion(int idLo) {
        int rows = 0;
        String sql = "DELETE FROM localizacion WHERE id_localizacion = '" + idLo + "';";
        conexion.conectar();
        try (Statement stmt = conexion.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            rows = -1;
        } finally {
            conexion.desconectar();
        }
        return rows;
    }

    /**
     * Devuelve el resultado de una visualizacion de la tabla Localizacion
     *
     * @return ArrayList Object[]
     */
    public ArrayList<Object[]> mostrarLocalizacion() {
        ArrayList<Object[]> localizaciones = new ArrayList<>();
        ResultSet rst = null;
        String sql = "SELECT * FROM localizacion";
        conexion.conectar();
        try (Statement stmt = conexion.getConn().createStatement()) {
            rst = stmt.executeQuery(sql);
            while (rst.next()) {
                Object[] fila = new Object[2];
                fila[0] = rst.getInt(1);
                fila[1] = rst.getString(2);
                localizaciones.add(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlmacenSQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.desconectar();
        }
        System.out.println(localizaciones.size());
        return localizaciones;

    }

    /**
     * Inserta un nuevo producto en la base de datos
     *
     * @param desPd - descripcion del producto proporcionado por el usuario
     * @param idLo  - id de localizacion proporcionado por el usuario
     * @param idPv - id de proveedor proporcionado por el usuario
     *
     * @return int
     */
     //metodo para insertar un Producto en la base de datos
    public int insertarProducto(String desPd, int idLo, int idPv) {

        String sql = "INSERT INTO productos VALUES(null,?,?,?);";
        conexion.conectar();
        int rows = 0;
        try {
            PreparedStatement pstmt = conexion.getConn().prepareStatement(sql);
            pstmt.setString(1, desPd);
            pstmt.setInt(2, idLo);
            pstmt.setInt(3, idPv);
            rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            rows = -1;
        } finally {
            conexion.desconectar();
        }
        return rows;

    }

    /**
     * Modifica un Producto en la base de datos
     *
     * @param desPd - descripcion del producto proporcionado por el usuario
     * @param idLo - id de localizacion proporcionado por el usuario
     * @param idPv - id de proveedor proporcionado por el usuario
     * @param idPd - id de producto proporcionado por el usuario
     *
     * @return int
     */
    //metodo para modificar un Producto en la base de datos
    public int modificarProducto(String desPd, int idLo, int idPv, int idPd) {
        int rows = 0;
        String sql = "UPDATE productos SET descrip_producto = '" + desPd + "',id_localizacion='" + idLo + "',id_proveedor='" + idPv + "' WHERE id_producto = '" + idPd + "';";
        conexion.conectar();
        try (Statement stmt = conexion.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            rows = -1;
        } finally {
            conexion.desconectar();
        }
        return rows;
    }

    /**
     * Borra un Producto de la base de datos
     *
     * @param idPd - id de producto proporcionado por el usuario
     * @return int;
     */
    public int borrarProducto(int idPd) {
        int rows = 0;
        String sql = "DELETE FROM productos WHERE id_producto = '" + idPd + "';";
        conexion.conectar();
        try (Statement stmt = conexion.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            rows = -1;
        } finally {
            conexion.desconectar();
        }
        return rows;

    }

    /**
     * Devuelve el resultado de una visualizacion de la tabla Productos
     *
     * @return ArrayList Object[]
     */
    public ArrayList<Object[]> mostrarProductos() {
        ArrayList<Object[]> productos = new ArrayList<>();
        ResultSet rst = null;
        String sql = "SELECT * FROM productos";
        conexion.conectar();
        try (Statement stmt = conexion.getConn().createStatement()) {
            rst = stmt.executeQuery(sql);
            while (rst.next()) {
                Object[] fila = new Object[4];
                fila[0] = rst.getInt(1);
                fila[1] = rst.getString(2);
                fila[2] = rst.getString(3);
                fila[3] = rst.getString(4);
                productos.add(fila);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlmacenSQL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexion.desconectar();
        }
        System.out.println(productos.size());
        return productos;
    }

    /**
     * Metodo que vincula la instancia abierta de la clase AlmacenSQL para 
     * evitar constante creacion de objetos en la interfaz grafica
     *
     *
     */
    public void setOpenInstance() {
        AlmacenSQL.openInstance = this;
    }
}
