package service;

import Impuesto.CalculoImpuesto;
import Model.Celular;
import Model.Cliente;
import Model.DetalleVenta;
import Model.Usuario;
import Model.Venta;
import dao.CelularDAO;
import dao.VentaDAO;
import exception.NegocioException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaService {
    
    private final VentaDAO ventaDAO = new VentaDAO();
    private final CelularDAO celularDAO = new CelularDAO();
    private final CalculoImpuesto impuesto;
    
    public VentaService(CalculoImpuesto impuesto){
        this.impuesto = impuesto;
    }
    
    public DetalleVenta crearDetalle(int celularId, int cantidad) throws SQLException{
        if(cantidad <= 0){
            throw new NegocioException("La cantidad debe ser mayor a 0");
        }
        Celular c = celularDAO.buscarId(celularId);
        if(c == null){
            throw new NegocioException("El celular no existe");
        }
        if(c.getStock()<cantidad){
            throw new NegocioException("Stock insuficiente de " + c.getModelo() + "(disponible: " + c.getStock() + ")");
        }
        DetalleVenta d = new DetalleVenta();
        d.setCelular(c);
        d.setCantidad(cantidad);
        d.setPreciounitario(c.getPrecio());
        d.setSubtotal(c.getPrecio()*cantidad);
        return d;
    }
    
    public Venta registrarVenta(Cliente cliente, Usuario vendedor, List<DetalleVenta> items) throws SQLException{
        if(cliente == null){
            throw new NegocioException("Debe seleccionar un cliente");
        }
        if(vendedor == null){
            throw new NegocioException("Vendedor no valido");
        }
        if(items == null || items.isEmpty()){
            throw new NegocioException("La venta debe tener al menos un celular");
        }
        
        List<DetalleVenta> detalles = new ArrayList<>();
        for(DetalleVenta item : items){
            detalles.add(crearDetalle(item.getCelular().getId(), item.getCantidad()));
        }
        
        double subtotal = redondear(detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum());
        double iva = redondear(impuesto.calcular(subtotal));
        double total = redondear(subtotal + iva);
        
        Venta v = new Venta();
        v.setCliente(cliente);
        v.setVendedor(vendedor);
        v.setFecha(LocalDateTime.now());
        v.setDetalles(detalles);
        v.setSubtotal(subtotal);
        v.setIva(iva);
        v.setTotal(total);
        
        ventaDAO.Agregar(v);
        return v;
    }
    
    public List<Venta> listar() throws SQLException{
        return ventaDAO.listar();
    }
    
    private double redondear(double valor){
        return Math.round(valor * 100.0)/100.0;
    }
}
