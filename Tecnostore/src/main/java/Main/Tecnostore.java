package Main;


import Impuesto.Iva;
import Model.Cliente;
import Model.DetalleVenta;
import Model.Usuario;
import Model.Venta;
import dao.ClienteDAO;
import dao.UsuarioDAO;
import exception.NegocioException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import service.LoginService;
import service.VentaService;

public class Tecnostore {

    public static void main(String[] args) throws SQLException {
        VentaService vs = new VentaService(new Iva());
        Cliente cliente = new ClienteDAO().buscarid(1);
        Usuario vendedor = new UsuarioDAO().buscarId(2);
        
        try{
            List<DetalleVenta>carrito = new ArrayList<>();
            carrito.add(vs.crearDetalle(3, 1));
            
            Venta v = vs.registrarVenta(cliente, vendedor, carrito);
            
            System.out.println("Venta id: " + v.getId());
            System.out.println("Subtotal: " + v.getSubtotal());
            System.out.println("Iva: " + v.getIva());
            System.out.println("Total: " + v.getTotal());
        }catch (NegocioException e){
            System.out.println("Error de negocio: " + e.getMessage());
        }
        
        try{
            vs.crearDetalle(15, 99);
        }catch (NegocioException e){
            System.out.println("Esperado: " + e.getMessage());
        }
        
        
    }
}
