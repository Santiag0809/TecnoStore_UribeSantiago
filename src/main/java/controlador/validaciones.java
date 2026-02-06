package controlador;

import java.util.Scanner;

public class validaciones {

    private static final Scanner sc = new Scanner(System.in);

    public static int validacionEnteroSwitch(String mensaje, int minimo, int maximo) {
        int op;

        while (true) {
            try {
                System.out.println(mensaje);
                op = Integer.parseInt(sc.nextLine());

                if (op < minimo || op > maximo) {
                    System.out.println("Error, opción inválida");
                } else {
                    return op;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error, solo se aceptan números enteros");
            }
        }
    }

    public static int validacionEnteros(String mensaje) {
        while (true) {
            try {
                System.out.println(mensaje);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error, solo se aceptan números enteros");
            }
        }
    }

    public static String validarTexto(String mensaje) {
        System.out.println(mensaje);
        return sc.nextLine();
    }
}
