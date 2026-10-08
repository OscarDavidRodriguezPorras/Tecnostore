package dao;

import Model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Cliente mapear(ResultSet rs) throws SQLException{
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNombre(rs.getString("nombre"));
        c.setIdentificacion(rs.getString("Identificacion"));
        c.setCorreo(rs.getString("correo"));
        c.setTelefono(rs.getString("telefono"));
        return c;
        
    }
    
    public List<Cliente> listar(){
        List<Cliente> lista = new ArrayList<>();
        String sql = "select id, nombre, identificacion, correo, telefono from clientes";
        
        try(Connection con = new Conexion().conexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                lista.add(mapear(rs));
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al listar clientes: " + e.getMessage(), e);
        }
        return lista;
    }
    
    
    public void insertar(Cliente c) throws SQLException{
       String sql = "insert into clientes (nombre, identificacion, correo, telefono) values (?, ?, ?, ?)";
       
       try (Connection co = new Conexion().conexion();
               PreparedStatement ps = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
           
           ps.setString(1, c.getNombre());
           ps.setString(2, c.getIdentificacion());
           ps.setString(3, c.getCorreo());
           ps.setString(4, c.getTelefono());
           ps.executeUpdate();
           
           try(ResultSet Keys = ps.getGeneratedKeys()){
               if(Keys.next()){
                   c.setId(Keys.getInt(1));   
               }
           }
       } catch (SQLException e){
           throw new RuntimeException("Error al insertar cliente: "+e.getMessage(), e);
       }
    }
    
    public void actualizar(Cliente c) throws SQLException{
        String sql = "update clientes set nombre = ?, identificacion = ?, correo = ?, telefono = ? where id = ?";
        
        try(Connection con = new Conexion().conexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getIdentificacion());
            ps.setString(3, c.getCorreo());
            ps.setString(4, c.getTelefono());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }
    
    public Cliente buscarid(int id){
        String sql = "select id, nombre, identificacion, correo, telefono from clientes where id = ?";
        
        try(Connection con = new Conexion().conexion();
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return mapear(rs);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al buscar cliente: " + e.getMessage(), e);
        }
        return null;
    }
    
    public Cliente buscarIdentificacion(String identificacion){
        String sql = "select id, nombre, identificacion, correo, telefono from clientes where identificacion = ?";
        
        try (Connection con = new Conexion().conexion();
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, identificacion);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return mapear(rs);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException("Error al buscar cliente por identificacion: "+e.getMessage(), e);
        }
        return null;
    }
    
}
