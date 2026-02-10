package vista;

import controlador.GestionarCelularImpl;
import controlador.GestionarMarcaImpl;
import controlador.validaciones;
import java.util.ArrayList;
import modelo.celular;
import modelo.gama;
import modelo.marca;

public class menuCelular {

    private final GestionarMarcaImpl gestorMarca = new GestionarMarcaImpl();
    private final GestionarCelularImpl gestorCelular = new GestionarCelularImpl();

    public void menuCel() {

        int op;

        do {
            op = validaciones.validacionEnteroSwitch("""
                    ======= GESTIÓN CELULARES =======
                    1. Registrar celulares
                    2. Actualizar celulares
                    3. Borrar celulares
                    4. Buscar celulares por ID
                    5. Listar celulares
                    6. Salir
                    """, 1, 6);

            switch (op) {
                case 1 ->
                    registrarCelular();
                case 2 ->
                    actualizar();
                case 3 ->
                    eliminar();
                case 4 ->
                    buscarPorId();
                case 5 ->
                    listar();
                case 6 ->
                    System.out.println("Saliendo al menu principaaaal");
            }

        } while (op != 6);
    }

    
    // HAY UN ERROR EN LA MARCA//
    private void registrarCelular() {

        ArrayList<marca> marcas = gestorMarca.listar();

        if (marcas.isEmpty()) {
            System.out.println("No hay marcas registradas.");
            return;
        }

        System.out.println("Seleccione una marca:");
        for (marca m : marcas) {
            System.out.println(m);
        }

        int idMarca = validaciones.validacionEnteros("Ingrese el id de la marca:");
        marca m = gestorMarca.buscarPorId(idMarca);

        if (m == null) {
            System.out.println("Marca no válida");
            return;
        }

        celular c = new celular();

        c.setModelo(validaciones.validarTexto("Modelo:"));
        c.setId_marca(m);
        c.setPrecio(validaciones.validacionEnteros("Precio:"));
        c.setStock(validaciones.validacionEnteros("Stock:"));
        c.setSistema_operativo(validaciones.validarTexto("Sistema operativo:"));

        System.out.println("Seleccione gama:");
        System.out.println("1. Baja");
        System.out.println("2. Media");
        System.out.println("3. Alta");

        int g;
        do {
            g = validaciones.validacionEnteros("Opción (1-3):");
        } while (g < 1 || g > 3);

        c.setGama(gama.values()[g - 1]);

        gestorCelular.registrar(c);
    }

    private void actualizar() {

        int id = validaciones.validacionEnteros("Ingrese el ID del celular a actualizar:");
        celular c = gestorCelular.buscar(id);

        if (c == null) {
            System.out.println("Celular no encontrado.");
            return;
        }

        System.out.println("Celular actual:");
        System.out.println(c);

        
        ArrayList<marca> marcas = gestorMarca.listar();
        System.out.println("Seleccione nueva marca:");
        for (marca m : marcas) {
            System.out.println(m);
        }

        int idMarca = validaciones.validacionEnteros("ID marca:");
        marca m = gestorMarca.buscarPorId(idMarca);

        if (m == null) {
            System.out.println("Marca inválida.");
            return;
        }

        c.setModelo(validaciones.validarTexto("Nuevo modelo:"));
        c.setId_marca(m);
        c.setPrecio(validaciones.validacionEnteros("Nuevo precio:"));
        c.setStock(validaciones.validacionEnteros("Nuevo stock:"));
        c.setSistema_operativo(validaciones.validarTexto("Nuevo sistema operativo:"));

        System.out.println("Seleccione nueva gama:");
        System.out.println("1. Baja");
        System.out.println("2. Media");
        System.out.println("3. Alta");

        int g = validaciones.validacionEnteros("Opción:");
        c.setGama(gama.values()[g - 1]);

        gestorCelular.actualizar(c, id);
    }

    private void eliminar() {

        int id = validaciones.validacionEnteros("Ingrese el ID del celular a eliminar:");
        celular c = gestorCelular.buscar(id);

        if (c == null) {
            System.out.println("Celular no encontrado.");
            return;
        }

        System.out.println("Seguro que desea eliminar este celular?");
        System.out.println(c);

        String resp = validaciones.validarTexto("Escriba SI para confirmar:");

        if (resp.equalsIgnoreCase("SI")) {
            gestorCelular.eliminar(id);
            System.out.println("Celular eliminado.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void buscarPorId() {

        int id = validaciones.validacionEnteros("Ingrese el ID del celular:");
        celular c = gestorCelular.buscar(id);

        if (c == null) {
            System.out.println("Celular no encontrado.");
            return;
        }

        System.out.println("""
        ===== CELULAR ENCONTRADO =====
        ID: %d
        Marca: %s
        Modelo: %s
        Sistema Operativo: %s
        Gama: %s
        Precio: %.2f
        Stock: %d
        """.formatted(
                c.getId(),
                c.getId_marca().getMarca(),
                c.getModelo(),
                c.getSistema_operativo(),
                c.getGama(),
                c.getPrecio(),
                c.getStock()
        ));
    }

    private void listar() {

        ArrayList<celular> celulares = gestorCelular.listar();

        if (celulares.isEmpty()) {
            System.out.println("No hay celulares registrados.");
            return;
        }

        System.out.println("""
        ID | MARCA | MODELO | SO | GAMA | PRECIO | STOCK
        -------------------------------------------------
        """);

        for (celular c : celulares) {
            System.out.printf(
                    "%-3d %-10s %-15s %-10s %-6s $%-8.2f %-5d%n",
                    c.getId(),
                    c.getId_marca().getMarca(),
                    c.getModelo(),
                    c.getSistema_operativo(),
                    c.getGama(),
                    c.getPrecio(),
                    c.getStock()
            );
        }
    }

}
