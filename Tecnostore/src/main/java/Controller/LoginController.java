package Controller;

import Model.Usuario;
import service.LoginService;

public class LoginController {
    private final LoginService ls = new LoginService();
    private Usuario usuarioActual;
    
    public Usuario login(String usuario, String password){
        usuarioActual = ls.login(usuario, password);
        return usuarioActual;
    }
    
    public Usuario getUsuarioActual(){
        return usuarioActual;
    }
    
    public void logout(){
        usuarioActual = null;
    }
}
