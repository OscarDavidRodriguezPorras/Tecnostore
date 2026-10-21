package View;

import Controller.ReporteController;
import Model.Celular;
import exception.NegocioException;
import java.sql.SQLException;
import util.Validaciones;

public class ReporteView {
    
    private final ReporteController controller;
    private final Validaciones v;
    
    public ReporteView(ReporteController controller, Validaciones v){
        this.controller = controller;
        this.v = v;
    }
    
    public void Menu() throws SQLException{
        int op;
        do{
            System.out.println("""
                               -- Reportes -- 
                               1. Celulares con bajo stock (<5)
                               2. Top 3 celulares mas vendidos
                               3. Ventas totales por mes
                               4. Generar Reporte
                               0. Volver
                               """);
            op=v.validarEntero("Opcion: ");
            try{
                switch(op){
                    case 1 -> mostrarAlertaStock();
                    case 2 -> top3();
                    case 3 -> ventasPorMes();
                    case 4 -> generarArchivo();
                    case 0 -> {}
                    default -> System.out.println("Opcion invalida");
                }
            }catch(RuntimeException e){
                System.out.println("Eror del sistema, intente mas tarde");
            }
        }while(op !=0);
    }
    
        public void mostrarAlertaStock(){
        try{
            var lista = controller.stockBajo();
            System.out.println("\n--- ALERTA DE STOCK BAJO ---");
            controller.formatearStockBajo(lista).forEach(System.out::println);

            String r = v.validarTexto("¿Generar archivo stock_bajo.txt? (s/n): ");
            if(r.equalsIgnoreCase("s")){
                System.out.println("Archivo generado: " + controller.generarArchivoStockBajo(lista));
            }
        }catch(NegocioException e){
            System.out.println(e.getMessage());
        }catch(java.io.IOException e){
            System.out.println("Error al escribir el archivo: " + e.getMessage());
        }catch(RuntimeException e){
            System.out.println("Error del sistema (revise la conexion): " + e.getMessage());
        }
    }
    
    private void top3() throws SQLException{
        System.out.println("\n -- Top 3 mas vendidos -- ");
        var top = controller.top3MasVendidos();
        if(top.isEmpty()){
            System.out.println("Aun no hay ventas");
            return;
        }
        int puesto = 1;
        for(var e : top){
            System.out.println(puesto++ + ". "+ e.getKey()+ " -> " + e.getValue() + "unidades");
        }
    }
    
    private void ventasPorMes() throws SQLException{
        System.out.println("\n -- Ventas por mes -- ");
        var mapa = controller.ventasPorMes();
        if(mapa.isEmpty()){
            System.out.println("Aun no hay ventas");
            return;
        }
        mapa.forEach((mes, total)->
            System.out.println(mes + " -> $ " + String.format("%,.0f", total)));
    }    
    
    private void generarArchivo() throws SQLException{
        try{
            controller.generarArchivo();
            System.out.println("Archivo generado");
        }catch(java.io.IOException e){
            System.out.println("Error al generar el reporte" + e.getMessage());
        }
    }
}
