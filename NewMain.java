
import interfazGrafica.VentanaPrincipal;
import java.io.File;
import programacionBD.AlmacenSQL;
import programacionBD.Conexion;

/*
import interfazGrafica.VentanaPrincipal;
import interfazGrafica.VistaProveedores;
import programacionBD.AlmacenSQL;
import programacionBD.Conexion;

/**
 *
 * @author EmilioMendez
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Conexion con1 = new Conexion("src/recursos/baseDatos.db");

        AlmacenSQL almacen = new AlmacenSQL(con1);
        almacen.setOpenInstance();

        almacen.crearTablas();

        VentanaPrincipal ventanaP = new VentanaPrincipal();
        ventanaP.setVisible(true);
    }

}
