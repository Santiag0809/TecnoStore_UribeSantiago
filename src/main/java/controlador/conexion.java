package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    public Connection conectar() {
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostore", "root", "0809");
            //System.out.println("Conexion creada con exito");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}
