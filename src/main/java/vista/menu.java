package vista;

import java.util.Scanner;

public class menu {

    public void Menu_Principal() {
        int op = 0;
        menuCliente menuCliente = new menuCliente();
        menuCelular menuCelular = new menuCelular();
        do {
            System.out.println("""
                           ******************************
                           1.   Gestionar Cliente.
                           2.   Gestionar Celulalres.
                           3.   Gestionar Ventas.
                           4.   Salir.
                           ******************************
                           """);
            op = new Scanner(System.in).nextInt();
            while (op < 1 || op > 4) {
                System.out.println("Error, opcion no valida");
                op = new Scanner(System.in).nextInt();
            }
            switch (op) {
                case 1:
                     menuCliente.menuCliente();
                    break;
                case 2:
                    menuCelular.menuCelular();
                    break;
                case 3:
                    System.out.println("Ventas");
                    break;
                case 4:
                    System.out.println("Gracias por usar el sistema adioooos");
                    break;
            }
        } while (op != 4);
    }
}
