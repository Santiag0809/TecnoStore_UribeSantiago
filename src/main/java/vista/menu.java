package vista;

import java.util.Scanner;

public class menu {

    public void Menu_Principal() {
        int op = 0;
        menuCliente menuCliente = new menuCliente();
        menuCelular menuCelular = new menuCelular();
        menuMarca menuMarca = new menuMarca();
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("""
                       ******************************
                       1.   Gestionar Cliente.
                       2.   Gestionar Celulares.
                       3.   Gestionar Marcas.
                       4.   Gestionar Ventas.
                       5.   Salir.
                       ******************************
                       """);

            try {
                op = Integer.parseInt(sc.nextLine());

                while (op < 1 || op > 5) {
                    System.out.println("Error, opción no válida");
                    op = Integer.parseInt(sc.nextLine());
                }

            } catch (NumberFormatException e) {
                System.out.println("Error, debes ingresar un número.");
                continue; 
            }

            switch (op) {
                case 1 -> menuCliente.menuCliente();
                case 2 -> menuCelular.menuCel();
                case 3 -> menuMarca.menuMarcos();
                case 4 -> System.out.println("Ventas");
                case 5 -> System.out.println("Gracias por usar el sistema adioooos");
            }

        } while (op != 5);
    }
}
