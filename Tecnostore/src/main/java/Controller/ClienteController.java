package Controller;

import Model.Cliente;
import java.sql.SQLException;
import java.util.List;
import service.ClienteService;

public class ClienteController {
    private final ClienteService service = new ClienteService();
    
    public Cliente registrar(String nombre, String identificacion, String correo, String telefono) throws SQLException{
        Cliente c = new Cliente();
        c.setNombre(nombre);
        c.setIdentificacion(identificacion);
        c.setCorreo(correo);
        c.setTelefono(telefono);
        service.registrar(c);
        return c;
    }
    
    public void actualizar(int id, String nombre, String identificacion, String correo, String telefono) throws SQLException{
        Cliente c = new Cliente();
        c.setId(id);
        c.setNombre(nombre);
        c.setIdentificacion(identificacion);
        c.setCorreo(correo);
        c.setTelefono(telefono);
        service.actualizar(c);
    }
    
    public List<Cliente> listar(){
        return service.listar();
    }
    
    public Cliente buscarIdentificacion(String identificacion){
        return service.BuscarPorIdentificacion(identificacion);
    }
    
}
