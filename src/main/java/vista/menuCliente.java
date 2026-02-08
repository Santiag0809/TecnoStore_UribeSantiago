package vista;

import controlador.GestionarClienteImpl;
import controlador.clienteControlador;
import controlador.validaciones;
import java.util.Scanner;
import modelo.cliente;

public class menuCliente {

    private clienteControlador gestor = new GestionarClienteImpl();

    public void menuCliente() {

        int op;

        do {
            op = validaciones.validacionEnteroSwitch("""
                    ======= GESTIÓN CLIENTES =======
                    1. Registrar cliente
                    2. Actualizar clientes
                    3. Borrar clientes
                    4. Buscar cliente por ID
                    5. Listar clientes
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

        } while (op != 4);
    }

    private void registrar() {

        cliente c = new cliente();

        c.setNombre(validaciones.validarTexto("Ingrese el nombre:"));
        c.setIdentificacion(validaciones.validarTexto("Ingrese la identificación:"));

        if (gestor.existeIdentificacion(c.getIdentificacion())) {
            System.out.println("La identificación ya existe");
            return;
        }

        c.setCorreo(validaciones.validarTexto("Ingrese el correo:"));
        c.setTelefono(validaciones.validarTexto("Ingrese el teléfono:"));

        gestor.registrar(c);
    }

    private void actualizar() {
        System.out.println("Ingrese el id del cliente a actualizar");
        int id = new Scanner(System.in).nextInt();
        cliente cl = gestor.buscarPorId(id);
        if (cl != null) {
            System.out.println("CLIENTE BUSCADO");
            System.out.println(cl);
            System.out.println("""
                               Ingrese lo quiere modificar
                               1.   Nombre
                               2.   Identificacion
                               3.   correo.
                               4.   telefono. 
                               """);
            int op = new Scanner(System.in).nextInt();
            while (op < 1 || op > 4) {
                System.out.println("Error, opcion no valida");
                op = new Scanner(System.in).nextInt();
            }
            switch (op) {
                case 1 -> {
                    System.out.println("Ingrese el nuevo nombre del cliente");
                    cl.setNombre(new Scanner(System.in).nextLine());
                    break;
                }
                case 2 -> {
                    System.out.println("Ingrese la nueva identificacion del cliente");
                    cl.setIdentificacion(new Scanner(System.in).nextLine());
                    break;
                }
                case 3 -> {
                    System.out.println("Ingrese el nuevo correo del cliente");
                    cl.setCorreo(new Scanner(System.in).nextLine());
                    break;
                }
                case 4 -> {
                    System.out.println("Ingrese el nuevo telefono del cliente");
                    cl.setTelefono(new Scanner(System.in).nextLine());
                    break;
                }
                
            }
            gestor.actualizar(cl , id);
        }
        else {
            System.out.println("No existe dicho empleado");
        }
    }

    private void listar() {
        System.out.println("falta implementar");
    }

    private void buscarPorId() {
        System.out.println("falta implementar");
    }

    private void eliminar() {
        System.out.println("falta implementar");
    }
}
