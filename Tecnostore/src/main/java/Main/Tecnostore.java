package Main;


import Model.Cliente;
import Model.Usuario;
import dao.ClienteDAO;
import dao.UsuarioDAO;
import java.sql.SQLException;

public class Tecnostore {

    public static void main(String[] args) throws SQLException {
        Usuario u = new UsuarioDAO().BuscarUsuario("admin");
        System.out.println(u.getNombre()+ " - "+u.getRol());
        System.out.println(new UsuarioDAO().BuscarUsuario("xyz"));
        
        ClienteDAO cdao = new ClienteDAO();
        cdao.listar().forEach(System.out::println);
        System.out.println(cdao.buscarIdentificacion("1001001001"));
        System.out.println(cdao.buscarIdentificacion("000"));
        
        Cliente nuevo = new Cliente();
        nuevo.setNombre(("Prueba"));
        nuevo.setIdentificacion("987");
        nuevo.setCorreo("prueba2@gmail.com");
        nuevo.setTelefono("300000000000");
        cdao.insertar(nuevo);
        System.out.println("Id generado: " + nuevo.getId());
        
    }
}
