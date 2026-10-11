package service;

import Model.Usuario;
import dao.UsuarioDAO;
import exception.NegocioException;
import util.Validaciones;

public class LoginService {
    private final UsuarioDAO usuarioDAO =new UsuarioDAO();
    
    public Usuario login(String usuario, String password){
        if(!Validaciones.NoVacio(usuario) || !Validaciones.NoVacio(password)){
            throw new NegocioException("Usuario o contraseña incorrectos");
        }
        Usuario u = usuarioDAO.BuscarUsuario(usuario.trim());
        
        if (u==null || !u.getPassword().equals(password)){
            throw new NegocioException("Usuario o contraseña incorrectos");
        }
        return u;
    }
}
