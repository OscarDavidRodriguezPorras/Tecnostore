package Main;

import Model.DetalleVenta;
import Model.Venta;
import dao.CelularDAO;
import dao.ClienteDAO;
import dao.UsuarioDAO;
import dao.VentaDAO;
import java.sql.SQLException;

public class Tecnostore {

    public static void main(String[] args) throws SQLException {
        Venta v = new Venta();
        v.setCliente(new ClienteDAO().buscarid(1));
        v.setVendedor(new UsuarioDAO().buscarId(2));
        
        DetalleVenta d = new DetalleVenta();
        d.setCelular(new CelularDAO().buscarId(3));
        d.setCantidad(1);
        d.setPreciounitario(699000);
        d.setSubtotal(699000);
        v.agregarDetalle(d);
        
        v.setSubtotal(699000);
        v.setIva(132810);
        v.setTotal(831810);
        
        VentaDAO vdao = new VentaDAO();
        vdao.Agregar(v);
        System.out.println("Venta id: " + v.getId());
        System.out.println(new CelularDAO().buscarId(3));
        vdao.listar().forEach(x -> System.out.println(x.getId()+ " - "+ x.getCliente() + " - "+x.getTotal()));
        
        
    }
}
