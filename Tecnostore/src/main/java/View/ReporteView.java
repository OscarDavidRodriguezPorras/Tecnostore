package View;

import Controller.ReporteController;
import Model.Celular;
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
                               0. Volver
                               """);
            op=v.validarEntero("Opcion: ");
            try{
                switch(op){
                    case 1 -> stockBajo();
                    case 2 -> top3();
                    case 3 -> ventasPorMes();
                    case 0 -> {}
                    default -> System.out.println("Opcion invalida");
                }
            }catch(RuntimeException e){
                System.out.println("Eror del sistema, intente mas tarde");
            }
        }while(op !=0);
    }
    
    private void stockBajo(){
        System.out.println("-- Stock Bajo --");
        var lista = controller.stockBajo();
        if(lista.isEmpty()){
            System.out.println("no hay celulares con stock bajo");
            return;
        }
        for(Celular c : lista){
            System.out.println(c.getMarca()+ " " + c.getModelo() + " -> " + c.getStock() + "unidades");
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
}
