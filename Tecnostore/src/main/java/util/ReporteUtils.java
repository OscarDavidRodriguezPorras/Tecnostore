package util;

import Model.Celular;
import Model.DetalleVenta;
import Model.Venta;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ReporteUtils {
    private ReporteUtils(){
    }
    
    public static List<Celular> stockBajo(List<Celular> celulares){
        return celulares.stream()
                .filter(c->c.getStock() < 5)
                .collect(Collectors.toList());
    }
    
    public static List<Map.Entry<String, Integer>> top3MasVendidos(List<Venta> ventas){
        return ventas.stream()
                .flatMap(v->v.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        (DetalleVenta d)->d.getCelular().getMarca() + " " + d.getCelular().getModelo(),
                Collectors.summingInt(DetalleVenta::getCantidad)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
    
    public static Map<YearMonth, Double> ventasPorMes(List<Venta> ventas){
        return ventas.stream()
                .collect(Collectors.groupingBy(
                v->YearMonth.from(v.getFecha()),
                        TreeMap::new,
                        Collectors.summingDouble(Venta::getTotal)));
        
    }
}
