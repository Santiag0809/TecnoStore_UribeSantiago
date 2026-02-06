package vista;

import controlador.conexion;

public class TecnoStore {

    public static void main(String[] args) {
        conexion c = new conexion();
        c.conectar();

        menu m = new menu();
        m.Menu_Principal();

    }
}
