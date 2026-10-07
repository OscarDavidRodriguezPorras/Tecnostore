package dao;


import Model.SistemaOperativo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SistemaOperativoDAO {
    private SistemaOperativo mapear(ResultSet rs) throws SQLException{
        SistemaOperativo so = new SistemaOperativo();
        so.setId(rs.getInt("id"));
        so.setNombre(rs.getString("nombre"));
        return so;
    }
    
    public List<SistemaOperativo> listar(){
        List<SistemaOperativo> lista = new ArrayList<>();
        String sql = "Select id, nombre from sistemas_operativos";
        
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
    
    public SistemaOperativo buscarId(int id){
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
