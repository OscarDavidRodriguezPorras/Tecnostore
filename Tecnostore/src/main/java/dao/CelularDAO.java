package dao;

import Model.Celular;
import Model.Gama;
import Model.Marca;
import Model.SistemaOperativo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CelularDAO {
    private static final String SELECT_BASE =
            "select c.id, c.modelo, c.precio, c.stock, "
            + "m.id as marca_id, m.nombre as nombre_marca, "
            + "so.id as so_id, so.nombre as nombre_so, "
            + "g.id as gama_id, g.nombre as nombre_gama "
            + "from celulares c "
            + "join marcas m on c.marca_id = m.id "
            + "join sistemas_operativos so on c.sistema_operativo_id = so.id "
            + "join gamas g on c.gama_id = g.id ";
    
    private Celular mapear(ResultSet rs) throws SQLException{
        Marca marca = new Marca();
        marca.setId((rs.getInt("marca_id")));
        marca.setNombre(rs.getString("nombre_marca"));
        
        SistemaOperativo so = new SistemaOperativo();
        so.setId(rs.getInt("so_id"));
        so.setNombre(rs.getString("nombre_so"));
        
        Gama gama = new Gama();
        gama.setId(rs.getInt("gama_id"));
        gama.setNombre(rs.getString("nombre_gama"));
        
        Celular c = new Celular();
        c.setId(rs.getInt("id"));
        c.setMarca(marca);
        c.setModelo(rs.getString("modelo"));
        c.setSistemaoperativo(so);
        c.setGama(gama);
        c.setPrecio(rs.getDouble("precio"));
        c.setStock(rs.getInt("stock"));
        return c;
    }
    
    public List<Celular> listar(){
        List<Celular> lista = new ArrayList<>();
        String sql = SELECT_BASE + "order by c.id";
        
        try (Connection c = new Conexion().conexion();
                PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            
            while(rs.next()){
                lista.add(mapear(rs));
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al listar celulares: "+e.getMessage());
        }
        return lista;
    }
    
    public void insertar(Celular c) throws SQLException{
        String sql = "insert into celulares (marca id, modelo, sistema_operativo_id, gama_id, precio, stock) "
                + "values (?, ?, ?, ?, ?, ?)";
        
        try(Connection con = new Conexion().conexion();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            ps.setInt(1, c.getMarca().getId());
            ps.setString(2, c.getModelo());
            ps.setInt(3, c.getSistemaoperativo().getId());
            ps.setInt(4, c.getGama().getId());
            ps.setDouble(5, c.getPrecio());
            ps.setInt(6, c.getStock());
            ps.executeUpdate();
            
            try(ResultSet Keys = ps.getGeneratedKeys()){
                if(Keys.next()){
                    c.setId(Keys.getInt(1));
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al insertar celular: " + e.getMessage());
        }
    }
    
    public void actualizar(Celular c) throws SQLException{
        String sql = "update celulares set marca marca_id = ?, modelo = ?, sistema_operativo_id = ?, "
                + "gama_id = ?, precio = ?, stock = ?, where id = ?";
        
        try (Connection con = new Conexion().conexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, c.getMarca().getId());
            ps.setString(2, c.getModelo());
            ps.setInt(3, c.getSistemaoperativo().getId());
            ps.setInt(4, c.getGama().getId());
            ps.setDouble(5, c.getPrecio());
            ps.setInt(6, c.getStock());
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("Error al atualizar el celular: " +e.getMessage());
        }
    }
    
    public void eliminar(int id) throws SQLException{
        String sql = "delete from celulares where id = ?";
        
        try (Connection c = new Conexion().conexion();
                PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("Error al eliminar el celular: "+ e.getMessage());
        }
    }
    
    public Celular buscarId(int id) throws SQLException{
        String sql = SELECT_BASE + "where c.id = ?";
        
        try(Connection c = new Conexion().conexion();
                PreparedStatement ps = c.prepareStatement(sql)){
          ps.setInt(1, id);
          
          try(ResultSet rs = ps.executeQuery()){
              if(rs.next()){
                  return mapear(rs);
              }
          }
        }catch (SQLException e){
            throw new RuntimeException("Eror al buscar el celular: " + e.getMessage());
        }
        return null;
    }
    
    public void actualizarStock(int id, int nuevoStock) throws SQLException{
        String sql = "update celulares set stock = ? where id = ?";
        
        try(Connection c = new Conexion().conexion();
                PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, nuevoStock);
            ps.setInt(2, id);
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException("Error al acutalizar el stock: " + e.getMessage());
        }
    }
}
