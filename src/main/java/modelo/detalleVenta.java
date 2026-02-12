package modelo;

public class detalleVenta {

    private int id;
    private int id_venta;
    private celular celular;
    private int cantidad;
    private double subtotal;

    public detalleVenta(int id, int id_venta, celular celular, int cantidad, double subtotal) {
        this.id = id;
        this.id_venta = id_venta;
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

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
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

    public detalleVenta() {
    }

    

    
    
}
