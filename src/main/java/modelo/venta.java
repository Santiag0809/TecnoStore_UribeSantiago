package modelo;

import java.time.LocalDate;
import java.util.List;

public class venta {

    private int id;
    private cliente cliente;
    private LocalDate fecha;
    private double total;
    private List<detalleVenta> items;

    public venta(int id, cliente cliente, LocalDate fecha, double total, List<detalleVenta> items) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<detalleVenta> getItems() {
        return items;
    }

    public void setItems(List<detalleVenta> items) {
        this.items = items;
    }
    
    
    
}
