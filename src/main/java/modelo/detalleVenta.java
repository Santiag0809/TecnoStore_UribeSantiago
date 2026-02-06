package modelo;

public class detalleVenta {

    private int id;
    private celular celular;
    private int cantidad;
    private double subtotal;

    public detalleVenta(int id, celular celular, int cantidad, double subtotal) {
        this.id = id;
        this.celular = celular;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public celular getCelular() {
        return celular;
    }

    public void setCelular(celular celular) {
        this.celular = celular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    
    
}
