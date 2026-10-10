package dao;

import Model.DetalleVenta;
import Model.Venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final CelularDAO celularDAO = new CelularDAO();
    
    private Venta mapear(ResultSet rs) throws SQLException{
        Venta v = new Venta();
        v.setId(rs.getInt("id"));
        v.setCliente(clienteDAO.buscarid(rs.getInt("cliente_id")));
        v.setVendedor(usuarioDAO.buscarId(rs.getInt("vendedor_id")));
        v.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        v.setSubtotal(rs.getDouble("subtotal"));
        v.setIva(rs.getDouble("iva"));
        v.setTotal(rs.getDouble("total"));
        v.setDetalles(listarDetalles(v.getId()));
        return v;
        
    }
    
    public void Agregar(Venta v) throws SQLException{
        String sqlVenta = "insert into ventas (Cliente_id, vendedor_id, fecha, subtotal, iva, total) "
                + "values (?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "insert into detalle_ventas (Venta_id, celular_id, cantidad, precio_unitario, subtotal) "
                + "values (?, ?, ?, ?, ?)";
        String sqlStock = "update celulares set stock = stock - ? where id = ? and stock >= ?";
        
        try(Connection c = new Conexion().conexion()){
            try(PreparedStatement psVenta = c.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psDetalle = c.prepareStatement(sqlDetalle);
                PreparedStatement psStock = c.prepareStatement(sqlStock)){
            c.setAutoCommit(false);
            
            LocalDateTime fecha = (v.getFecha() != null) ? v.getFecha() : LocalDateTime.now();
            psVenta.setInt(1, v.getCliente().getId());
            psVenta.setInt(2, v.getVendedor().getId());
            psVenta.setTimestamp(3, Timestamp.valueOf(fecha));
            psVenta.setDouble(4, v.getSubtotal());
            psVenta.setDouble(5, v.getIva());
            psVenta.setDouble(6, v.getTotal());
            psVenta.executeUpdate();
            
            try(ResultSet keys = psVenta.getGeneratedKeys()){
                if(keys.next()){
                    v.setId(keys.getInt(1));
                }
            }
            for(DetalleVenta d : v.getDetalles()){
                psDetalle.setInt(1, v.getId());
                psDetalle.setInt(2, d.getCelular().getId());
                psDetalle.setInt(3, d.getCantidad());
                psDetalle.setDouble(4, d.getPreciounitario());
                psDetalle.setDouble(5, d.getSubtotal());
                psDetalle.executeUpdate();
                
                psStock.setInt(1, d.getCantidad());
                psStock.setInt(2, d.getCelular().getId());
                psStock.setInt(3, d.getCantidad());
                
                int filas = psStock.executeUpdate();
                if(filas == 0 ){
                    throw new RuntimeException("Stock insuficiente para el celuar con id : " + d.getCelular().getId());
                }
            }
            c.commit();
        }catch (SQLException | RuntimeException e){
            c.commit();
            throw e;
        }
        }catch(SQLException e){
            throw new RuntimeException("Error al registrar la venta: " + e.getMessage());
        }
    }
    
    public List<Venta> listar() throws SQLException{
        List<Venta> lista = new ArrayList<>();
        String sql = "select id, cliente_id, vendedor_id, fecha, subtotal, iva, total "
                + "from ventas order by fecha";
        
        try(Connection c = new Conexion().conexion();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                lista.add(mapear(rs));
            }
        }catch(SQLException e){
            throw new RuntimeException("Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }
    
    public Venta buscarId(int id) throws SQLException{
        String sql = "select id, cliente, vendedor_id, fecha, subtotal, iva, total "
                + "from ventas where id = ? ";
        try(Connection c = new Conexion().conexion();
                PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, id);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return mapear(rs);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException("Eeror al  buscar venta: " + e.getMessage());
        }
        return null;
    }
    
    public List<DetalleVenta> listarDetalles(int ventaId) throws SQLException{
        List<DetalleVenta> detalles = new ArrayList<>();
        String sql = "select id, celular_id, cantidad, precio_unitario, subtotal "
                + "from detalle_ventas where venta_id = ?";
        
        try(Connection c = new Conexion().conexion();
                PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, ventaId);
            
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    DetalleVenta d = new DetalleVenta();
                    d.setId(rs.getInt("id"));
                    d.setCelular(celularDAO.buscarId(rs.getInt("Celular_id")));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPreciounitario(rs.getDouble("precio_unitario"));
                    d.setSubtotal(rs.getDouble("subtotal"));
                    detalles.add(d);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException("Error al listar detalles: " + e.getMessage());
        }
        return detalles;
    }
}
