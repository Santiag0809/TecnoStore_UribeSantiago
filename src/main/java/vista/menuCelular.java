package vista;

import controlador.celularControlador;
import controlador.validaciones;
import java.util.ArrayList;
import modelo.celular;

public class menuCelular {
    
    public void menuCliente() {

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

    
    private celularControlador gcl = new celularControlador() {
        @Override
        public void registrar(celular ce) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void actualizar(celular ce, int id) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void eliminar(int id) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public ArrayList<celular> listar() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public celular buscar(int id) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    
}
