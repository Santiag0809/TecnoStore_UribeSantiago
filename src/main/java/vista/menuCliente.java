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
                    2. Actualizar clientes
                    3. Borrar clientes
                    4. Buscar cliente por ID
                    5. Listar clientes
                    6. Salir
                    """, 1, 6);

            switch (op) {
                case 1 -> registrar();
                case 2 -> actualizar();
                case 3 -> eliminar();
                case 4 -> buscarPorId();
                case 5 -> listar();
                case 6 -> System.out.println("Saliendo al menu principaaaal");
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
        System.out.println("Falta implementar");
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
