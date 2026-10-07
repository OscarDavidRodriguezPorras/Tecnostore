package Model;

public class DetalleVenta {
    private int id;
    private Celular celular;
    int Cantidad;
    private double preciounitario;
    private double subtotal;

    public DetalleVenta(int id, Celular celular, int Cantidad, double preciounitario, double subtotal) {
        this.id = id;
        this.celular = celular;
        this.Cantidad = Cantidad;
        this.preciounitario = preciounitario;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Celular getCelular() {
        return celular;
    }

    public void setCelular(Celular celular) {
        this.celular = celular;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public double getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(double preciounitario) {
        this.preciounitario = preciounitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    
    
}
