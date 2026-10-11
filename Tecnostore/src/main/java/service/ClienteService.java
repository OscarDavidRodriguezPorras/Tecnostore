package service;

import Model.Cliente;
import dao.ClienteDAO;
import exception.NegocioException;
import java.sql.SQLException;
import java.util.List;
import util.Validaciones;

public class ClienteService {
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    private void validar(Cliente c){
        if (c==null){
            throw new NegocioException("El cliente es obligatorio");
        }
        if(!Validaciones.NoVacio(c.getNombre())){
            throw new NegocioException("El nombre no peude estar vacio");
        }
        if(!Validaciones.CorreoValido(c.getCorreo())){
            throw new NegocioException("Correo invalido");
        }
        if(!Validaciones.IdValida(c.getIdentificacion())){
            throw new NegocioException("identificacion invalida");
        }
        if(!Validaciones.TelefonoValido(c.getTelefono())){
            throw new NegocioException("telefono invalido");
        }
    }
    
    private boolean correoExiste(String correo, int idActual){
        return clienteDAO.listar().stream().anyMatch(x->x.getCorreo().equalsIgnoreCase(correo) && x.getId() != idActual);
    }
    
    public void registrar(Cliente c) throws SQLException{
        validar(c);
        
        if(clienteDAO.buscarIdentificacion(c.getIdentificacion()) != null){
            throw new NegocioException("Identificacion ya registrada");
        }
        if(correoExiste(c.getCorreo(), 0)){
            throw new NegocioException("Correo ya registrado");
        }
        clienteDAO.insertar(c);
    }
    
    public void actualizar(Cliente c) throws SQLException{
        validar(c);
        
        Cliente existente = clienteDAO.buscarIdentificacion(c.getIdentificacion());
        if(existente != null && existente.getId() != c.getId()){
            throw new NegocioException("Identificacion ya registrada");
        }
        if(correoExiste(c.getCorreo(), c.getId())){
            throw new NegocioException("Correo ya registrado");
        }
        clienteDAO.actualizar(c);
    }
    
    public List<Cliente>listar(){
        return clienteDAO.listar();
    }
    
    public Cliente BuscarPorIdentificacion(String identificacion){
        return clienteDAO.buscarIdentificacion(identificacion);
    }
    
    
}
