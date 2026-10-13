package util;

import Model.DetalleVenta;
import Model.Venta;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ArchivoUtils {
    
    public static final String ARCHIVO_REPORTE = "reporte_ventas.dat";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private ArchivoUtils(){
    }
    
    public static void generarReporte(List<Venta> ventas ) throws IOException{
        try(BufferedWriter w = Files.newBufferedWriter(Paths.get(ARCHIVO_REPORTE), StandardCharsets.UTF_8)){
            w.write("--- Reporte de Ventas ---");
            w.newLine();
            w.newLine();
            
            double totalGeneral = 0;
            for(Venta v : ventas){
                w.write("Venta # " + v.getId() + " | " + v.getFecha().format(FORMATO));
                w.newLine();
                w.write("Cliente: " + v.getCliente().getNombre() + " (" + v.getCliente().getIdentificacion() + ")");
                w.newLine();
                w.write("Vendedor: " + v.getVendedor().getNombre());
                w.newLine();
                for(DetalleVenta d : v.getDetalles()){
                    w.write(" - " + d.getCelular().getMarca() + " " + d.getCelular().getModelo() + " X" + d.getCantidad() + " | $" + String.format("%,.0f", d.getSubtotal()));
                    w.newLine();
                }
                w.write(" Subtotal: $" + String.format("%,.0f", v.getSubtotal()));
                w.newLine();
                w.write(" Iva: $" + String.format("%,.0f", v.getIva()));
                w.newLine();
                w.write(" Total: $" + String.format("%,.0f", v.getTotal()));
                w.newLine();
                w.write("---------------------------------------------------------------");
                w.newLine();
                totalGeneral += v.getTotal();
            }
            w.newLine();
            w.write("Ventas registradas: " + ventas.size());
            w.newLine();
            w.write("Total general: $" + String.format("%,.0f", totalGeneral));
            w.newLine();
        }
    }
}
