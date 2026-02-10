package vista;

import controlador.GestionarMarcaImpl;
import controlador.validaciones;
import java.util.ArrayList;
import modelo.marca;

public class menuMarca {

    private final GestionarMarcaImpl gestorMarca = new GestionarMarcaImpl();

    public void menuMarcos() {

        int op;

        do {
            op = validaciones.validacionEnteroSwitch("""
                    ======= GESTIÓN DE MARCAS =======
                    1. Registrar marca
                    2. Actualizar marca
                    3. Eliminar marca
                    4. Listar marcas
                    5. Salir
                    """, 1, 5);

            switch (op) {
                case 1 ->
                    registrar();
                case 2 ->
                    actualizar();
                case 3 ->
                    eliminar();
                case 4 ->
                    listar();
                case 5 ->
                    System.out.println("Volviendo al menú principal...");
            }

        } while (op != 5);
    }

    
    private void registrar() {
        String nombre = validaciones.validarTexto("Ingrese el nombre de la marca:");
        marca m = new marca(0, nombre);
        gestorMarca.registrar(m);
        System.out.println("Marca registrada correctamente.");
    }

    
    private void listar() {
        ArrayList<marca> marcas = gestorMarca.listar();

        if (marcas.isEmpty()) {
            System.out.println("No hay marcas registradas.");
            return;
        }

        System.out.println("Listado de marcas:");
        marcas.forEach(System.out::println);
    }

    
    private void actualizar() {
        listar();

        int id = validaciones.validacionEnteros("Ingrese el ID de la marca a actualizar:");
        marca m = gestorMarca.buscarPorId(id);

        if (m == null) {
            System.out.println("Marca no encontrada.");
            return;
        }

        String nuevoNombre = validaciones.validarTexto("Ingrese el nuevo nombre:");
        m.setMarca(nuevoNombre);

        gestorMarca.actualizar(m);
        System.out.println("Marca actualizada correctamente.");
    }

    
    private void eliminar() {
        listar();

        int id = validaciones.validacionEnteros("Ingrese el ID de la marca a eliminar:");
        marca m = gestorMarca.buscarPorId(id);

        if (m == null) {
            System.out.println("Marca no encontrada.");
            return;
        }

        gestorMarca.eliminar(id);
        System.out.println("Marca eliminada correctamente.");
    }
}
