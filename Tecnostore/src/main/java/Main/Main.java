package Main;

import Controller.CelularController;
import Controller.ClienteController;
import Controller.LoginController;
import Controller.ReporteController;
import Controller.VentaController;
import Model.Usuario;
import View.CelularView;
import View.ClienteView;
import View.LoginView;
import View.MenuAdminView;
import View.MenuVendedorView;
import View.ReporteView;
import java.sql.SQLException;
import util.Validaciones;



public class Main {
    public static void main(String[] args) throws SQLException {
        
     Validaciones v = new Validaciones();
    LoginController lc = new LoginController();
    CelularController cc = new CelularController();
    ClienteController clienteC = new ClienteController();
    VentaController vc = new VentaController();
    ReporteController rc = new ReporteController();
    
    LoginView lv = new LoginView(lc, v);
    CelularView cv = new CelularView(cc, v);
    ClienteView ClienteV = new ClienteView(clienteC, v);
    ReporteView rv = new ReporteView(rc, v);
    MenuAdminView mav = new MenuAdminView(v, cv, ClienteV, rv);
    MenuVendedorView mvd = new MenuVendedorView(v, vc, clienteC, ClienteV, cv);
    
     boolean seguir = true;
        while (seguir) {
            Usuario u = lv.mostrar();
            if (u == null) {
                System.out.println("Demasiados intentos fallidos. Programa terminado.");
                break;
            }
            if (u.Administrador()) {
                mav.mostrar(u);
            } else {
                mvd.mostrar(u);
            }
            lc.logout();

            String salir = v.validarTexto("¿Desea cerrar el programa? (s/n):");
            seguir = !salir.equalsIgnoreCase("s");
        }
        System.out.println("¡Hasta pronto!");
    }
}
    
