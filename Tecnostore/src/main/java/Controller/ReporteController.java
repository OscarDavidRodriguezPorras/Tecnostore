package Controller;

import Impuesto.Iva;
import Model.Celular;
import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import service.AlertaStockService;
import static service.AlertaStockService.UMBRAL;
import service.VentaService;
import util.ReporteUtils;
import service.AlertaStockService;

public class ReporteController {
    
    private final AlertaStockService alertaService = new AlertaStockService();
    private final VentaService ventaService = new VentaService(new Iva());
    
    public List<Celular> stockBajo(){
        return ReporteUtils.stockBajo(alertaService.obtenerStockBajo());
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
