package Model;

public class Celular {
    private int id;
    private Marca marca;
    private String modelo;
    private SistemaOperativo sistemaoperativo;
    private Gama gama;
    private double precio;
    private int stock;
    
    public Celular(){
        
    }
    
    public Celular(int id, Marca marca, String modelo, SistemaOperativo sistemaoperativo, Gama gama, double precio, int stock) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.sistemaoperativo = sistemaoperativo;
        this.gama = gama;
        this.precio = precio;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public SistemaOperativo getSistemaoperativo() {
        return sistemaoperativo;
    }

    public void setSistemaoperativo(SistemaOperativo sistemaoperativo) {
        this.sistemaoperativo = sistemaoperativo;
    }

    public Gama getGama() {
        return gama;
    }

    public void setGama(Gama gama) {
        this.gama = gama;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    @Override
    public String toString(){
        return marca + " " + modelo + " | " + gama + " | " + precio + " | Stock: " + stock;
    }
}
