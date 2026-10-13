package service;

import Model.Celular;
import dao.CelularDAO;
import exception.NegocioException;
import java.sql.SQLException;
import java.util.List;
import util.Validaciones;

public class CelularService {
    private final CelularDAO celularDAO = new CelularDAO();
    
    private void validar(Celular c){
        if(c == null){
            throw new NegocioException("El celular es obligatorio");
        }
        if(!Validaciones.NoVacio(c.getModelo())){
            throw new NegocioException("El modelo no puede estar vacio");
        }
        if(c.getMarca() == null || c.getSistemaoperativo() == null || c.getGama() == null){
            throw new NegocioException("Marca, sistema operativo y gama son obligatorios");
        }
        if(!Validaciones.Positivo(c.getPrecio())){
            throw new NegocioException("El precio debe ser mayor a 0");
        }
        if(!Validaciones.PositivoInt(c.getStock())){
            throw new NegocioException("El stock no puede ser negativo");
        }
    }
    
    public List<Celular> listar(){
        return celularDAO.listar();
    }
    
    public Celular buscarId(int id) throws SQLException{
        return celularDAO.buscarId(id);
    }
    
    public void registrar(Celular c) throws SQLException{
        validar(c);
        celularDAO.insertar(c);
    }
    
    public void actualizar(Celular c) throws SQLException{
        validar(c);
        if(celularDAO.buscarId(c.getId()) == null){
            throw new NegocioException("El celular ni existe");
        }
        celularDAO.actualizar(c);
    }
    
    public void eliminar(int id) throws SQLException{
        if(celularDAO.buscarId(id) == null){
            throw new NegocioException("El celular no existe");
        }
        try{
            celularDAO.eliminar(id);
        }catch(RuntimeException e){
            throw new NegocioException("No se puede eliminar: el celular tine ventas registradas");
        }
    }
}
