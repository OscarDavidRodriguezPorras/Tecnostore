package dao;

import Model.Gama;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GamaDAO {
    private Gama mapear(ResultSet rs) throws SQLException{
        Gama g = new Gama();
        g.setId(rs.getInt("id"));
        g.setNombre(rs.getString("nombre"));
        return g;
    }
    
    public List<Gama> listar(){
        List<Gama> lista = new ArrayList<>();
        String sql = "Select id, nombre from gamas";
        
        try (Connection c = new Conexion().conexion();
             PreparedStatement ps = c.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                lista.add(mapear(rs));
            }
        }catch (SQLException e){
            throw new RuntimeException("Erorr al listar marcas: " + e.getMessage(), e);
        }
        return lista;
    }
    
    public Gama buscarId(int id){
        String sql = "select id, nombre from gamas where id = ?";
        
        try(Connection c = new Conexion().conexion();
                PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return mapear(rs);
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al buscar marca: " + e.getMessage(), e);
        }
        return null;
    }
}