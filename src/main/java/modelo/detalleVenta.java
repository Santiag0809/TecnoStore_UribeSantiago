package modelo;

public class detalleVenta {

    private int id;
    private celular celular;
    private int cantidad;
    private double subtotal;

    public detalleVenta(celular celular, int cantidad) {
        this.celular = celular;
        this.cantidad = cantidad;
        this.subtotal = celular.getPrecio() * cantidad;
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
        if (celular != null) {
            this.subtotal = celular.getPrecio() * cantidad;
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        if (celular != null) {
            this.subtotal = celular.getPrecio() * cantidad;
        }
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

}
