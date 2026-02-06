package controlador;

import java.sql.Connection;

public class ventaControlador {

private Connection conexion;

    public ventaControlador(Connection conexion) {
        this.conexion = conexion;
    }    
}
