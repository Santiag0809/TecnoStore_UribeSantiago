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
                int valor = Integer.parseInt(sc.nextLine());
                
        if (valor < 0) {
                    System.out.println("Error: no se permiten números negativos.");
                } else {
                    return valor;
                }

            } catch (NumberFormatException e) {
                System.out.println("Error, solo se aceptan números enteros");
            }
        }
    
}


    public static String validarTexto(String mensaje) {
        while (true) {
            System.out.println(mensaje);
            String txt = sc.nextLine().trim();
            if (txt.isEmpty()) {
                System.out.println("Error, el campo no puede estar vacío.");
            } else {
                return txt;
            }
        }
    }

    public static boolean correoValidador(String correo) {
        if (correo == null) {
            return false;
        }
        correo = correo.trim();
        return correo.matches("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    }

}
