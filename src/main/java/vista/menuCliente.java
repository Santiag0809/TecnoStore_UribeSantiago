package vista;

import controlador.GestionarClienteImpl;
import controlador.clienteControlador;
import controlador.validaciones;
import modelo.cliente;

public class menuCliente {

    private clienteControlador gestor = new GestionarClienteImpl();

    public void menuCliente() {

        int op;

        do {
            op = validaciones.validacionEnteroSwitch("""
                    ======= GESTIÓN CLIENTES =======
                    1. Registrar cliente
                    2. Listar clientes
                    3. Buscar cliente por ID
                    4. Volver
                    """, 1, 4);

            switch (op) {
                case 1:
                    registrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscarPorId();
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
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

    private void listar() {
        System.out.println("falta implementar");
    }

    private void buscarPorId() {
        System.out.println("falta implementar");
    }
}
