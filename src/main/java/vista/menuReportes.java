package vista;

import controlador.GestionarCelularImpl;
import controlador.GestionarVentaImpl;
import controlador.celularControlador;
import controlador.validaciones;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import modelo.celular;
import modelo.venta;
import utilidades.ReporteUtils;

public class menuReportes {

    private celularControlador gestorCelular = new GestionarCelularImpl();
    private GestionarVentaImpl gestorVenta = new GestionarVentaImpl();

    public void menuReportes() {

        int op;

        do {
            op = validaciones.validacionEnteroSwitch("""
                    ======= REPORTES =======
                    1. Celulares con stock bajo (<5)
                    2. Top 3 celulares más vendidos
                    3. Ventas totales por mes
                    4. Generar reporte_ventas.txt
                    5. Salir
                    """, 1, 5);

            switch (op) {
                case 1 ->
                    reporteStockBajo();
                case 2 ->
                    reporteTop3Vendidos();
                case 3 ->
                    reporteVentasPorMes();
                case 4 ->
                    generarTxt();
                case 5 ->
                    System.out.println("Saliendo al menu principaaaal");
            }

        } while (op != 5);
    }

    private void reporteStockBajo() {

        ArrayList<celular> celulares = gestorCelular.listar();
        List<celular> bajos = ReporteUtils.celularesStockBajo(celulares);

        if (bajos.isEmpty()) {
            System.out.println("No hay celulares con stock bajo.");
            return;
        }

        for (celular c : bajos) {
            System.out.println("ID: " + c.getId());
            System.out.println("Marca: " + c.getId_marca().getMarca());
            System.out.println("Modelo: " + c.getModelo());
            System.out.println("Stock: " + c.getStock());
            System.out.println("----------------------------------");
        }
    }

    private void reporteTop3Vendidos() {

        ArrayList<venta> ventas = gestorVenta.listarConDetalles();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        var top = ReporteUtils.topCelularesVendidos(ventas);

        if (top.isEmpty()) {
            System.out.println("No hay detalle de ventas para calcular top.");
            return;
        }

        for (var e : top) {

            celular ce = gestorCelular.buscar(e.getKey());

            System.out.println("Celular ID: " + e.getKey());
            if (ce != null) {
                System.out.println("Modelo: " + ce.getModelo());
                System.out.println("Marca: " + ce.getId_marca().getMarca());
            }
            System.out.println("Cantidad vendida: " + e.getValue());
            System.out.println("----------------------------------");
        }
    }

    private void reporteVentasPorMes() {

        List<venta> ventas = gestorVenta.listar();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        ReporteUtils.ventasPorMes(ventas)
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("%s -> $%,.2f%n", e.getKey(), e.getValue()));
    }

    private void generarTxt() {

        ArrayList<venta> ventas = gestorVenta.listarConDetalles();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }

        ReporteUtils.generarReporteVentas(ventas);
    }

    private List<venta> ventasCompletas() {

        List<venta> base = gestorVenta.listar();
        List<venta> completas = new ArrayList<>();

        for (venta v : base) {
            venta full = gestorVenta.buscarPorId(v.getId());
            if (full != null) {
                completas.add(full);
            }
        }

        return completas;
    }
}
