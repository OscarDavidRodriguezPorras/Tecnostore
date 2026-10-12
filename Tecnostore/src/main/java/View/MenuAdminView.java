package View;

import Model.Usuario;
import java.sql.SQLException;
import util.Validaciones;

public class MenuAdminView {
    
    private final Validaciones v;
    private final CelularView celularView;
    private final ClienteView clienteView;
    private final ReporteView reporteView;

    public MenuAdminView(Validaciones v, CelularView celularView, ClienteView clienteView, ReporteView reporteView) {
        this.v = v;
        this.celularView = celularView;
        this.clienteView = clienteView;
        this.reporteView = reporteView;
    }
    
    public void mostrar(Usuario admin) throws SQLException{
        int op;
        do{
            System.out.println("""
                               -- Menu de admin --
                               1. Gestion celulares
                               2. Gestion clientes
                               3. Reportes
                               0. Cerar sesion
                               """);
            op = v.validarEntero("Opcion: ");
            switch(op){
                case 1 -> celularView.mostrarMenu();
                case 2 -> clienteView.mostrarMenu();
                case 3 -> reporteView.Menu();
                case 0 -> System.out.println("Sesion Cerrada");
                default -> System.out.println("Opcion invalida");
            }
        }while(op != 0);
    }    
}
