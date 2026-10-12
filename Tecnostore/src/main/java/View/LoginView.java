package View;

import Controller.LoginController;
import Model.Usuario;
import exception.NegocioException;
import util.Validaciones;

public class LoginView {
    private final LoginController login;
    private final Validaciones v;
    
    public LoginView(LoginController login, Validaciones v){
        this.login = login;
        this.v = v;
    }
    
    public Usuario mostrar(){
        for (int i = 1; i <= 3; i++) {
            System.out.println("\n===Tecnostore - Inicio de sesion ===");
            String usuario = v.validarTexto("Usuario: ");
            String password = v.validarTexto("Contraseña");
            try{
                return login.login(usuario, password);
            }catch(NegocioException e){
                System.out.println(e.getMessage() + " (intento " + i + " de 3)");
            }catch (RuntimeException e){
                System.out.println("Error el sietema, intente mas tarde");
            }
        }
        return null;
    }
}
