package utilidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.celular;
import modelo.detalleVenta;
import modelo.venta;

public class ReporteUtils {

    public static List<celular> celularesStockBajo(List<celular> celulares) {

        return celulares.stream()
                .filter(c -> c.getStock() < 5)
                .collect(Collectors.toList());
    }

    public static List<Map.Entry<Integer, Integer>> topCelularesVendidos(List<venta> ventas) {

        Map<Integer, Integer> conteo = ventas.stream()
                .flatMap(v -> v.getItems().stream())
                .collect(Collectors.groupingBy(
                        dv -> dv.getCelular().getId(),
                        Collectors.summingInt(detalleVenta::getCantidad)
                ));

        return conteo.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(3)
                .collect(Collectors.toList());
    }

    public static Map<Month, Double> ventasPorMes(List<venta> ventas) {

        return ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFecha().getMonth(),
                        Collectors.summingDouble(venta::getTotal)
                ));
    }

    public static void generarReporteVentas(List<venta> ventas) {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte de ventas");
        chooser.setSelectedFile(new File("reporte_ventas.txt"));
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos TXT", "txt"));

        java.awt.Frame frame = new java.awt.Frame();
        frame.setAlwaysOnTop(true);
        int res = chooser.showSaveDialog(frame);
        frame.dispose();

        if (res != JFileChooser.APPROVE_OPTION) {
            System.out.println("Operación cancelada.");
            return;
        }

        File file = chooser.getSelectedFile();

        if (!file.getName().toLowerCase().endsWith(".txt")) {
            file = new File(file.getAbsolutePath() + ".txt");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            bw.write("===== REPORTE DE VENTAS =====");
            bw.newLine();
            bw.newLine();

            for (venta v : ventas) {

                double subtotal = 0;

                if (v.getItems() != null) {
                    for (detalleVenta dv : v.getItems()) {
                        subtotal += dv.getSubtotal();
                    }
                }

                double iva = subtotal * 0.19;
                double total = subtotal + iva;

                bw.write("Venta ID: " + v.getId());
                bw.newLine();

                String nombreCliente = "N/A";
                if (v.getCliente() != null && v.getCliente().getNombre() != null) {
                    nombreCliente = v.getCliente().getNombre();
                }

                bw.write("Cliente: " + nombreCliente);
                bw.newLine();
                bw.write("Cliente ID: " + (v.getCliente() != null ? v.getCliente().getId() : 0));
                bw.newLine();
                bw.write("Fecha: " + v.getFecha());
                bw.newLine();
                bw.write(String.format("Subtotal: $%,.2f", subtotal));
                bw.newLine();
                bw.write(String.format("IVA (19%%): $%,.2f", iva));
                bw.newLine();
                bw.write(String.format("Total: $%,.2f", total));
                bw.newLine();

                if (v.getItems() != null) {
                    for (detalleVenta dv : v.getItems()) {
                        bw.write("  Celular ID: " + (dv.getCelular() != null ? dv.getCelular().getId() : 0)
                                + " | Cantidad: " + dv.getCantidad()
                                + " | Subtotal: " + String.format("$%,.2f", dv.getSubtotal()));
                        bw.newLine();
                    }
                }

                bw.write("----------------------------------");
                bw.newLine();
            }

            System.out.println("Reporte generado en: " + file.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error generando reporte: " + e.getMessage());
        }
    }

}
