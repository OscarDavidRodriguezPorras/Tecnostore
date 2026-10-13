package View;

import Controller.ClienteController;
import Model.Cliente;
import exception.NegocioException;
import java.sql.SQLException;
import util.Validaciones;

public class ClienteView {
    private final ClienteController controller;
    private final Validaciones v;
    
    public ClienteView(ClienteController controller, Validaciones v){
        this.controller = controller;
        this.v = v;
    }
    
    public void mostrarMenu() throws SQLException{
        int op;
        do{
            System.out.println("""
                               ---Gestion de clietnes---
                               1. Registrar cliente
                               2. Listar clientes
                               3. Actualizar cliente
                               0. Volver
                               """);
            op = v.validarEntero("Opcion: ");
            switch(op){
                case 1 -> registrar();
                case 2 -> listar();
                case 3 -> actualizar();
                case 0 -> { }
                default -> System.out.println("Opcion invalida");
            }
        }while(op != 0);
    }
    
    public Cliente registrar() throws SQLException{
        System.out.println("-- Registrar cliente --");
        String nombre = v.validarTexto("Nombre: ");
        String identificacion = v.ValidarId("Identificacion: ");
        String correo = v.ValidarCorreo("Correo: ");
        String telefono = v.ValidarTelefono("Telefono: ");
        try{
            Cliente c = controller.registrar(nombre, identificacion, correo, telefono);
            System.out.println("Cliente registrado con id " + c.getId());
            return c;
        }catch (NegocioException e){
            System.out.println("error: " + e.getMessage());
        }catch (RuntimeException e){
            System.out.println("Error del sistema, intente mas tarde");
        }
        return null;
    }
    
    private void listar(){
        System.out.println("\n -- Clientes --");
        try{
            controller.listar().forEach(c ->
                    System.out.println(c.getId() + ". " + c.getNombre() + " | " + c.getIdentificacion()
                    + " | " + c.getCorreo() + " | " + c.getTelefono()));
        }catch(RuntimeException e){
            System.out.println("Error del sistema" + e.getMessage());
        }
    }
    
    private void actualizar() throws SQLException{
        String actual = v.ValidarId("identificaion del cliente a actualizar: ");
        try{
            Cliente c = controller.buscarIdentificacion(actual);
            if(c==null){
                System.out.println("No existe un cliente con esta id");
                return;
            }
            System.out.println("Editando a " + c.getNombre());
            String nombre = v.validarTexto("Nombre: ");
            String identificacion = v.ValidarId("Identificacion: ");
            String correo = v.ValidarCorreo("Correo: ");
            String telefono = v.ValidarTelefono("Telefono: ");
            controller.actualizar(c.getId(), nombre, identificacion, correo, telefono);
            System.out.println("Cliente actualizado");       
        }catch(NegocioException e){
            System.out.println("Error: " + e.getMessage());
        }catch(RuntimeException e){
            System.out.println("error del sistema, intente mas tarde" + e.getMessage());
        }
    }
}
