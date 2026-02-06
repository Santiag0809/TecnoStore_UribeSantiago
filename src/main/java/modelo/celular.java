package modelo;

public class celular {

    private int id,stock;
    private marca id_marca;
    private double precio;
    private String modelo,sistema_operativo;
    private enum gama{alta,media,baja};

    public celular(int id, int stock, marca id_marca, double precio, String modelo, String sistema_operativo) {
        this.id = id;
        this.stock = stock;
        this.id_marca = id_marca;
        this.precio = precio;
        this.modelo = modelo;
        this.sistema_operativo = sistema_operativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public marca getId_marca() {
        return id_marca;
    }

    public void setId_marca(marca id_marca) {
        this.id_marca = id_marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSistema_operativo() {
        return sistema_operativo;
    }

    public void setSistema_operativo(String sistema_operativo) {
        this.sistema_operativo = sistema_operativo;
    }
    
    
    
}
