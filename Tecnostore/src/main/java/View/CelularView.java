
package View;

import Controller.CelularController;
import Model.Celular;
import exception.NegocioException;
import java.sql.SQLException;
import util.Validaciones;

public class CelularView {
    private final CelularController controller;
    private final Validaciones v;
    
    public CelularView(CelularController controller, Validaciones v){
        this.controller = controller;
        this.v = v;
    }
    
    public void mostrarMenu() throws SQLException{
        int op;
        do{
            System.out.println("""
                               --Gestion de celulares--
                               1. Listar Celulares
                               2. Agregar Celular
                               3. Actualizar celular
                               4. Eliminar Celular
                               0. Volver
                               """);
            op=v.validarEntero("Opcion: ");
            switch(op){
                case 1 -> listar();
                case 2 -> agregar();
                case 3 -> actualizar();
                case 4 -> eliminar();
                case 5 -> {}
                default -> System.out.println("Opcion invalida");
            }
        }while(op != 0);
    }
    
    public void listar(){
        System.out.println("\n -- Catalogo --");
        try{
            for (Celular c : controller.listar()) {
                System.out.println(c.getId() + ". " + c.getMarca() + " " + c.getModelo()
                        + " | " + c.getSistemaoperativo()+ " | " + c.getGama()
                        + " | $" + String.format("%,.0f",c.getPrecio())
                        + " | stock: " + c.getStock());
            }
        }catch (RuntimeException e){
            System.out.println("Error del sistema, intente mas tarde.");
        }
    }
    
    private int elegirMarca(){
        System.out.println("Marcas: ");
        controller.listarMarcas().forEach(m->System.out.println(" " + m.getId() + ". " + m));
        return v.validarEntero("Id de la marca: ");
    }
    
    private int elegirSistema(){
        System.out.println("Sistemas operativos: ");
        controller.listarSistemas().forEach(s->System.out.println(" " + s.getId() + ". " + s));
        return v.validarEntero("Id del sistema operativo");
    }
    
    private int elegirGama(){
        System.out.println("gamas: ");
        controller.listarGamas().forEach(g->System.out.println(" " + g.getId() + ". " + g));
        return v.validarEntero("Id de la gama: ");
    }
    
    private void agregar() throws SQLException{
        System.out.println("\n -- Agregar celular --");
        try{
            int marcaId = elegirMarca();
            String modelo = v.validarTexto("Modelo: ");
            int soId = elegirSistema();
            int gamaId = elegirGama();
            double precio = v.validarPrecio("Precio: ");
            int stock = v.ValidarStock("Stock: ");
            
            Celular c = controller.registrar(marcaId, modelo, soId, gamaId, precio, stock);
            System.out.println("Celular registrado con id " + c.getId());
        }catch(NegocioException e){
            System.out.println("Error: " +e.getMessage());
        }catch(RuntimeException e){
            System.out.println("Error del sistema, intentelo mas tarde");
        }
    }
    
    private void actualizar() throws SQLException{
        listar();
        int id = v.validarEntero("Id del celular a actualizar: ");
        try{
            if(controller.buscarId(id)==null){
                System.out.println("El celular no existe");
                return;
            }
            System.out.println("Escriba los nuevos datos: ");
            int marcaId = elegirMarca();
            String modelo = v.validarTexto("Modelo: ");
            int soId = elegirSistema();
            int gamaId = elegirGama();
            double precio = v.validarPrecio("Precio: ");
            int stock = v.ValidarStock("Stock: ");
            
            controller.actualizar(id, marcaId, modelo, soId, gamaId, precio, stock);
            System.out.println("Celular actualizado");
        }catch(NegocioException e){
            System.out.println("Error: " + e.getMessage());
        }catch(RuntimeException e){
            System.out.println("Error del sistema, intente mas tarde");
        }
    }
    
    private void eliminar() throws SQLException{
        listar();
        int id = v.validarEntero("Id del celular a eliminar: ");
        String c = v.validarTexto("Seguro que desea eliminarlo? (s/n): ");
        if(!c.equalsIgnoreCase("s")){
            System.out.println("Eliminacion cancelada");
            return;
        }
        try{
            controller.eliminar(id);
            System.out.println("Celular eliminado");
        }catch(NegocioException e){
            System.out.println("Error: " + e.getMessage());
        }catch(RuntimeException e){
            System.out.println("Error del sistema, intente mas tarde");
        }
    }
}
