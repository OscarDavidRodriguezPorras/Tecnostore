package Controller;

import Impuesto.Iva;
import Model.Cliente;
import Model.DetalleVenta;
import Model.Usuario;
import Model.Venta;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import service.VentaService;
import util.ArchivoUtils;

public class VentaController {
    private final VentaService service = new VentaService(new Iva());
    private final List<DetalleVenta> carrito = new ArrayList<>();
    
    public DetalleVenta agregarCarrito(int celularId, int cantidad) throws SQLException{
        int YaenCarrito = carrito.stream()
                .filter(x->x.getCelular().getId() == celularId)
                .mapToInt(DetalleVenta::getCantidad).sum();
        
        DetalleVenta d = service.crearDetalle(celularId, cantidad +  YaenCarrito);
        carrito.removeIf(x->x.getCelular().getId() == celularId);
        carrito.add(d);
        return d;
    }
    
    public List<DetalleVenta> getCarrito(){
        return carrito;
    }
    
    public double subtotalCarrito(){
        return carrito.stream().mapToDouble(DetalleVenta::getSubtotal).sum();
    }
    
    public Venta confirmarVenta(Cliente cliente, Usuario vendedor) throws SQLException{
        Venta v = service.registrarVenta(cliente, vendedor, new ArrayList<>(carrito));
        carrito.clear();
        generarReporte();
        return v;
    }
    
    public void generarReporte()throws SQLException{
        try{
            ArchivoUtils.generarReporte(service.listar());
        }catch(java.io.IOException e){
            System.out.println("No se pudo generar " + ArchivoUtils.ARCHIVO_REPORTE + ": " + e.getMessage());
        }
    }
    
    public void cancelarVenta(){
        carrito.clear();
    }
    
    public List<Venta> listar() throws SQLException{
        return service.listar();
    }
}
