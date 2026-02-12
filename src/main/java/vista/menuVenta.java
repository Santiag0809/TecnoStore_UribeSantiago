package vista;

import controlador.GestionarVentaImpl;
import controlador.ventaControlador;
import controlador.GestionarClienteImpl;
import controlador.clienteControlador;
import controlador.GestionarCelularImpl;
import controlador.celularControlador;
import controlador.validaciones;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import modelo.celular;
import modelo.cliente;
import modelo.detalleVenta;
import modelo.venta;

public class menuVenta {

    private ventaControlador gestor = new GestionarVentaImpl();
    private clienteControlador gestorCliente = new GestionarClienteImpl();
    private celularControlador gestorCelular = new GestionarCelularImpl();

    public void menuVentana() {

        int op;

        do {
            op = validaciones.validacionEnteroSwitch("""
                    ======= GESTIÓN VENTAS =======
                    1. Registrar venta
                    2. Actualizar venta
                    3. Borrar venta
                    4. Buscar venta por ID
                    5. Listar ventas
                    6. Salir
                    """, 1, 6);

            switch (op) {
                case 1 ->
                    registrar();
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

    private void registrar() {

        venta v = new venta();
        ArrayList<detalleVenta> items = new ArrayList<>();

        System.out.println("Ingrese el id del cliente");
        int idCliente = new Scanner(System.in).nextInt();

        cliente cl = gestorCliente.buscarPorId(idCliente);

        if (cl == null) {
            System.out.println("No existe dicho cliente");
            return;
        }

        v.setCliente(cl);
        v.setFecha(LocalDate.now());

        int op;

        do {
            System.out.println("""
                               ======= AGREGAR CELULAR A LA VENTA =======
                               1. Agregar celular
                               2. Terminar venta
                               """);

            op = new Scanner(System.in).nextInt();

            while (op < 1 || op > 2) {
                System.out.println("Error, opcion no valida");
                op = new Scanner(System.in).nextInt();
            }

            if (op == 1) {

                System.out.println("Ingrese el id del celular");
                int idCel = new Scanner(System.in).nextInt();

                celular ce = gestorCelular.buscar(idCel);

                if (ce == null) {
                    System.out.println("No existe dicho celular");
                    continue;
                }

                System.out.println("Ingrese la cantidad");
                int cantidad = new Scanner(System.in).nextInt();

                if (cantidad <= 0) {
                    System.out.println("Cantidad inválida");
                    continue;
                }

                if (cantidad > ce.getStock()) {
                    System.out.println("Stock insuficiente. Stock actual: " + ce.getStock());
                    continue;
                }

                detalleVenta dv = new detalleVenta();
                dv.setCelular(ce);
                dv.setCantidad(cantidad);
                dv.setSubtotal(ce.getPrecio() * cantidad);

                items.add(dv);

                System.out.println("Celular agregado a la venta!");

            }

        } while (op != 2);

        if (items.isEmpty()) {
            System.out.println("La venta debe tener al menos un item.");
            return;
        }

        v.setItems(items);

        gestor.registrar(v);
    }

    private void actualizar() {

        System.out.println("Ingrese el id de la venta a actualizar");
        int id = new Scanner(System.in).nextInt();

        venta v = gestor.buscarPorId(id);

        if (v == null) {
            System.out.println("No existe dicho venta");
            return;
        }

        System.out.println("VENTA ENCONTRADA");
        System.out.println("ID: " + v.getId());
        System.out.println("Cliente ID: " + v.getCliente().getId());
        System.out.println("Fecha: " + v.getFecha());
        System.out.println("Total: " + v.getTotal());
        System.out.println("----------------------------------");

        System.out.println("""
                           Ingrese lo quiere modificar
                           1.   Cliente
                           2.   Items
                           """);

        int op = new Scanner(System.in).nextInt();
        while (op < 1 || op > 2) {
            System.out.println("Error, opcion no valida");
            op = new Scanner(System.in).nextInt();
        }

        switch (op) {
            case 1 -> {
                System.out.println("Ingrese el nuevo id del cliente");
                int idCliente = new Scanner(System.in).nextInt();

                cliente cl = gestorCliente.buscarPorId(idCliente);

                if (cl == null) {
                    System.out.println("No existe dicho cliente");
                    return;
                }

                v.setCliente(cl);
                v.setFecha(LocalDate.now());
                break;
            }

            case 2 -> {

                ArrayList<detalleVenta> items = new ArrayList<>();

                int op2;

                do {
                    System.out.println("""
                               ======= AGREGAR CELULAR A LA VENTA =======
                               1. Agregar celular
                               2. Terminar edición
                               """);

                    op2 = new Scanner(System.in).nextInt();

                    while (op2 < 1 || op2 > 2) {
                        System.out.println("Error, opcion no valida");
                        op2 = new Scanner(System.in).nextInt();
                    }

                    if (op2 == 1) {

                        System.out.println("Ingrese el id del celular");
                        int idCel = new Scanner(System.in).nextInt();

                        celular ce = gestorCelular.buscar(idCel);

                        if (ce == null) {
                            System.out.println("No existe dicho celular");
                            continue;
                        }

                        System.out.println("Ingrese la cantidad");
                        int cantidad = new Scanner(System.in).nextInt();

                        if (cantidad <= 0) {
                            System.out.println("Cantidad inválida");
                            continue;
                        }

                        detalleVenta dv = new detalleVenta();
                        dv.setCelular(ce);
                        dv.setCantidad(cantidad);
                        dv.setSubtotal(ce.getPrecio() * cantidad);

                        items.add(dv);

                        System.out.println("Celular agregado!");

                    }

                } while (op2 != 2);

                if (items.isEmpty()) {
                    System.out.println("La venta debe tener al menos un item.");
                    return;
                }

                v.setItems(items);
                v.setFecha(LocalDate.now());
                break;
            }
        }

        gestor.actualizar(v, id);
    }

    private void listar() {

        ArrayList<venta> ventas = gestor.listar();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        for (venta v : ventas) {
            System.out.println("ID: " + v.getId());
            System.out.println("Cliente ID: " + v.getCliente().getId());
            System.out.println("Fecha: " + v.getFecha());
            System.out.println("Total: " + v.getTotal());
            System.out.println("----------------------------------");
        }
    }

    private void buscarPorId() {

        System.out.println("Ingrese el id de la venta a buscar");
        int id = new Scanner(System.in).nextInt();

        venta v = gestor.buscarPorId(id);

        if (v != null) {

            System.out.println("ID: " + v.getId());
            System.out.println("Cliente ID: " + v.getCliente().getId());
            System.out.println("Fecha: " + v.getFecha());
            System.out.println("Total: " + v.getTotal());
            System.out.println("----------------------------------");

            if (v.getItems() != null && !v.getItems().isEmpty()) {

                System.out.println("======= ITEMS =======");

                for (detalleVenta dv : v.getItems()) {

                    System.out.println("Celular ID: " + dv.getCelular().getId());
                    System.out.println("Cantidad: " + dv.getCantidad());
                    System.out.println("Subtotal: " + dv.getSubtotal());
                    System.out.println("----------------------------------");
                }
            }

        } else {
            System.out.println("No existe ese id");
        }
    }

    private void eliminar() {

        listar();

        int id = validaciones.validacionEnteros("Ingrese el id de la venta a eliminar");
        venta v = gestor.buscarPorId(id);

        if (v == null) {
            System.out.println("Venta no encontrada.");
            return;
        }

        System.out.println("VENTA ENCONTRADA");
        System.out.println("ID: " + v.getId());
        System.out.println("Cliente ID: " + v.getCliente().getId());
        System.out.println("Fecha: " + v.getFecha());
        System.out.println("Total: " + v.getTotal());
        System.out.println("----------------------------------");

        String resp = validaciones.validarTexto("Escriba SI para confirmar eliminación:");

        if (resp.equalsIgnoreCase("SI")) {
            gestor.eliminar(id);
        } else {
            System.out.println("Operación cancelada.");
        }
    }

}
