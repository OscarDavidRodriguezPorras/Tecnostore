package Controller;

import Model.Celular;
import Model.Marca;
import Model.SistemaOperativo;
import dao.GamaDAO;
import dao.MarcaDAO;
import dao.SistemaOperativoDAO;
import java.sql.SQLException;
import java.util.List;
import service.CelularService;

public class CelularController {
    private final CelularService service = new CelularService();
    private final MarcaDAO marcaDAO = new MarcaDAO();
    private final SistemaOperativoDAO soDAO = new SistemaOperativoDAO();
    private final GamaDAO gamaDAO = new GamaDAO();
    
    private Celular armar(int marcaId, int soId, String modelo, int gamaId, int stock, double precio, int stock1){
        Celular c = new Celular();
        c.setMarca(marcaDAO.buscarId(marcaId));
        c.setModelo(modelo);
        c.setSistemaoperativo(soDAO.buscarId(soId));
        c.setGama(gamaDAO.buscarId(gamaId));
        c.setPrecio(precio);
        c.setStock(stock);
        return c;
    }
    
    public Celular registrar(int marcaId, String modelo, int soId, int gamaId, double precio, int stock) throws SQLException{
        Celular c = armar(marcaId, soId, modelo, gamaId, stock, precio, stock);
        service.registrar(c);
        return c;
    }
    
    public void actualizar(int id, int marcaId, String modelo, int soId, int gamaId, double precio, int stock) throws SQLException{
        Celular c = armar(id, marcaId, modelo, soId, gamaId, precio, stock);
        c.setId(id);
        service.actualizar(c);
    }
    
    public void eliminar(int id) throws SQLException{
        service.buscarId(id);
    }
    
    public List<Celular> listar(){
        return service.listar();
    }
    
    public Celular buscarId(int id) throws SQLException{
        return service.buscarId(id);
    }
    
    public List<Marca> listarMarcas(){
        return marcaDAO.listar();
    }
    
    public List<SistemaOperativo> listarSistemas(){
        return soDAO.listar();
    }
    
    
}
    
