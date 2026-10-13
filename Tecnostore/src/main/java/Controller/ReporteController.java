package Controller;

import Impuesto.Iva;
import Model.Celular;
import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import service.CelularService;
import service.VentaService;
import util.ReporteUtils;

public class ReporteController {
    
    private final CelularService celularService = new CelularService();
    private final VentaService ventaService = new VentaService(new Iva());
    
    public List<Celular> stockBajo(){
        return ReporteUtils.stockBajo(celularService.listar());
    }
    
    public List<Map.Entry<String, Integer>> top3MasVendidos() throws SQLException{
        return ReporteUtils.top3MasVendidos(ventaService.listar());
    }
    
    public Map<YearMonth, Double> ventasPorMes() throws SQLException{
        return ReporteUtils.ventasPorMes(ventaService.listar());
    }
    
    public void generarArchivo() throws SQLException, IOException{
    util.ArchivoUtils.generarReporte(ventaService.listar());
}
}
