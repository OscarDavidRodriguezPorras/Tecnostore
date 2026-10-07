package dao;

import Model.Marca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarcaDAO {
    private Marca mapear(ResultSet rs) throws SQLException{
        Marca m = new Marca();
        m.setId(rs.getInt("id"));
        m.setNombre(rs.getString("nombre"));
        return m;
    }
    
    public List<Marca> listar(){
        List<Marca> lista = new ArrayList<>();
        String sql = "Select id, nombre from marcas";
        
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
    
    public Marca buscarId(int id){
        String sql = "select id, nombre from marcas where id = ?";
        
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
