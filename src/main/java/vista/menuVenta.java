package vista;

import controlador.GestionarCelularImpl;
import controlador.GestionarClienteImpl;
import controlador.GestionarVentaImpl;
import controlador.validaciones;
import java.time.LocalDate;
import java.util.ArrayList;
import modelo.celular;
import modelo.cliente;
import modelo.detalleVenta;
import modelo.venta;

public class menuVenta {

    GestionarVentaImpl gestorVenta = new GestionarVentaImpl();
    GestionarClienteImpl gestorCliente = new GestionarClienteImpl();
    GestionarCelularImpl gestorCelular = new GestionarCelularImpl();
    validaciones validaciones = new validaciones();

    public void menuVenta() {

        int op = 0;

        do {
            System.out.println("""
                           ******************************
                           1.   Registrar Venta.
                           2.   Actualizar Venta.
                           3.   Eliminar Venta.
                           4.   Listar Ventas.
                           5.   Buscar Venta por ID.
                           6.   Salir.
                           ******************************
                           """);

            op = validaciones.validacionEnteros("Seleccione una opción:");

            while (op < 1 || op > 6) {
                System.out.println("Error, opcion no valida");
                op = validaciones.validacionEnteros("Seleccione una opción:");
            }

            switch (op) {
                case 1:
                    registrar();
                    break;
                case 2:
                    actualizar();
                    break;
                case 3:
                    eliminar();
                    break;
                case 4:
                    listar();
                    break;
                case 5:
                    buscar();
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
            }

        } while (op != 6);
    }

    private void registrar() {

        int idCliente = validaciones.validacionEnteros("Ingrese el ID del cliente:");
        cliente cl = gestorCliente.buscarPorId(idCliente);

        if (cl == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        ArrayList<detalleVenta> items = new ArrayList<>();

        while (true) {

            int idCel = validaciones.validacionEnteros("Ingrese el ID del celular (0 para no seguir agregando):");
            if (idCel == 0) {
                break;
            }

            celular ce = gestorCelular.buscar(idCel);

            if (ce == null) {
                System.out.println("Celular no encontrado.");
                continue;
            }

            int cant = validaciones.validacionEnteros("Cantidad:");

            if (cant <= 0) {
                System.out.println("Cantidad inválida.");
                continue;
            }

            if (cant > ce.getStock()) {
                System.out.println("Stock insuficiente. Stock actual: " + ce.getStock());
                continue;
            }

            detalleVenta dv = new detalleVenta();
            dv.setCelular(ce);
            dv.setCantidad(cant);
            dv.setSubtotal(ce.getPrecio() * cant);

            items.add(dv);
        }

        if (items.isEmpty()) {
            System.out.println("No se agregaron items a la venta.");
            return;
        }

        venta v = new venta(0, cl, LocalDate.now(), 0, items);
        gestorVenta.registrar(v);
    }

    private void actualizar() {

        int idVenta = validaciones.validacionEnteros("Ingrese el ID de la venta a actualizar:");
        venta vExistente = gestorVenta.buscarPorId(idVenta);

        if (vExistente == null) {
            System.out.println("Venta no encontrada.");
            return;
        }

        int idCliente = validaciones.validacionEnteros("Ingrese el ID del nuevo cliente:");
        cliente cl = gestorCliente.buscarPorId(idCliente);

        if (cl == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        ArrayList<detalleVenta> items = new ArrayList<>();

        while (true) {

            int idCel = validaciones.validacionEnteros("Ingrese el ID del celular (0 para terminar):");
            if (idCel == 0) {
                break;
            }

            celular ce = gestorCelular.buscar(idCel);

            if (ce == null) {
                System.out.println("Celular no encontrado.");
                continue;
            }

            int cant = validaciones.validacionEnteros("Cantidad:");

            if (cant <= 0) {
                System.out.println("Cantidad inválida.");
                continue;
            }

            detalleVenta dv = new detalleVenta();
            dv.setCelular(ce);
            dv.setCantidad(cant);
            dv.setSubtotal(ce.getPrecio() * cant);

            items.add(dv);
        }

        if (items.isEmpty()) {
            System.out.println("No se agregaron items.");
            return;
        }

        venta v = new venta(idVenta, cl, LocalDate.now(), 0, items);
        gestorVenta.actualizar(v, idVenta);
    }

    private void eliminar() {

        int idVenta = validaciones.validacionEnteros("Ingrese el ID de la venta a eliminar:");
        venta v = gestorVenta.buscarPorId(idVenta);

        if (v == null) {
            System.out.println("Venta no encontrada.");
            return;
        }

        System.out.println("Seguro que desea eliminar esta venta?");
        System.out.println("ID: " + v.getId());
        System.out.println("Cliente: " + v.getCliente().getNombre());
        System.out.println("Fecha: " + v.getFecha());
        System.out.println("Total: " + v.getTotal());
        System.out.println("----------------------------------");

        String resp = validaciones.validarTexto("Escriba SI para confirmar:");

        if (resp.equalsIgnoreCase("SI")) {
            gestorVenta.eliminar(idVenta);
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void listar() {

        ArrayList<venta> ventas = gestorVenta.listar();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        for (venta v : ventas) {
            System.out.println("ID: " + v.getId());
            System.out.println("Cliente: " + v.getCliente().getNombre());
            System.out.println("Fecha: " + v.getFecha());
            System.out.println("Total: " + v.getTotal());
            System.out.println("----------------------------------");
        }
    }

    private void buscar() {

        int idVenta = validaciones.validacionEnteros("Ingrese el ID de la venta:");
        venta v = gestorVenta.buscarPorId(idVenta);

        if (v == null) {
            System.out.println("Venta no encontrada.");
            return;
        }

        System.out.println("ID: " + v.getId());
        System.out.println("Cliente: " + v.getCliente().getNombre());
        System.out.println("Fecha: " + v.getFecha());
        System.out.println("Total: " + v.getTotal());
        System.out.println("Items:");

        for (detalleVenta dv : v.getItems()) {
            System.out.println("Celular: " + dv.getCelular().getModelo());
            System.out.println("Cantidad: " + dv.getCantidad());
            System.out.println("Subtotal: " + dv.getSubtotal());
            System.out.println("----------------------------------");
        }
    }
}
