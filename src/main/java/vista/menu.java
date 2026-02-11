package vista;

import java.util.Scanner;

public class menu {

    public void Menu_Principal() {
        int op = 0;
        menuCliente menuCliente = new menuCliente();
        menuCelular menuCelular = new menuCelular();
        menuMarca menuMarca = new menuMarca();
        do {
            System.out.println("""
                           ******************************
                           1.   Gestionar Cliente.
                           2.   Gestionar Celulalres.
                           3.   Gestionar Marcas.
                           4.   Gestionar Ventas.
                           5.   Salir.
                           ******************************
                           """);
            op = new Scanner(System.in).nextInt();
            while (op < 1 || op > 5) {
                System.out.println("Error, opcion no valida");
                op = new Scanner(System.in).nextInt();
            }
            switch (op) {
                case 1:
                     menuCliente.menuCliente();
                    break;
                case 2:
                    menuCelular.menuCel();
                    break;
                case 3:
                    menuMarca.menuMarcos();
                    break;
                case 4:
                    System.out.println("Ventas");
                    break;
                case 5:
                    System.out.println("Gracias por usar el sistema adioooos");
                    break;
            }
        } while (op != 5);
    }
}
