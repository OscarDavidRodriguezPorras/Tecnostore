package dao;

import Model.Rol;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Usuario mapear(ResultSet rs) throws SQLException {
        Rol rol = new Rol();
        rol.setId(rs.getInt("rol_id"));
        rol.setNombre(rs.getString("nombre_rol"));
        
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre_usuario"));
        u.setPassword(rs.getString("password"));
        u.setUsuario(rs.getString("usuario"));
        u.setRol(rol);
        return u;
    }
    
    public Usuario BuscarUsuario(String usuario){
        String sql = "select u.id, u.nombre as nombre_usuario, u. usuario, u.password,"
                     + "r.id ad rol_id, r.nombre as nombre_rol"
                     + "from usuarios u"
                     + "join roles r on u.id_rol = r.id"
                     + "where u.usuario = ?";
        try (Connection c = new Conexion().conexion();
                PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, usuario);
            
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                   return mapear(rs); 
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Error al buscar suario: " + e.getMessage(),e);
        }
        return null;
    }
}
