package View;

import Controller.ClienteController;
import Controller.VentaController;
import Model.Cliente;
import Model.DetalleVenta;
import Model.Usuario;
import Model.Venta;
import exception.NegocioException;
import java.sql.SQLException;
import util.Validaciones;

public class MenuVendedorView {
    
    private final Validaciones v;
    private final VentaController ventaC;
    private final ClienteController clienteC;
    private final ClienteView clienteV;
    private final CelularView celularV;

    public MenuVendedorView(Validaciones v, VentaController ventaC, ClienteController clienteC, ClienteView clienteV, CelularView celularV) {
        this.v = v;
        this.ventaC = ventaC;
        this.clienteC = clienteC;
        this.clienteV = clienteV;
        this.celularV = celularV;
    }
    
    public void mostrar(Usuario vendedor) throws SQLException{
        int op;
        do{
            System.out.println("""
                               -- Menu Vendedor --
                               1. Registrar venta
                               2. Registrar cliente
                               3. Ver catalogo de celulares
                               0. Cerrar Sesion
                               """);
            op = v.validarEntero("Opcion: ");
            switch(op){
                case 1 -> registrarVenta(vendedor);
                case 2 -> clienteV.registrar();
                case 3 -> celularV.listar();
                case 0 -> System.out.println("Sesion cerrada");
                default -> System.out.println("Opcion invalida");
            }
        }while(op != 0);
    }
    
    private void registrarVenta(Usuario vendedor) throws SQLException{
        ventaC.cancelarVenta();
        System.out.println("\n -- Registrar venta --");
        String id = v.ValidarId("Id del cliente: ");
        Cliente cliente;
        try{
            cliente = clienteC.buscarIdentificacion(id);
        }catch(RuntimeException e){
            System.out.println("Erro del sistema, intente mas tarde");
            return;
        }
        if(cliente == null){
            System.out.println("El cliente no existe");
            String r = v.validarTexto("Desea registrarlo? (s/n)");
            if(!r.equalsIgnoreCase("s")){
                return;
            }
            cliente = clienteV.registrar();
            if(cliente == null){
                return;
            }
        }
        System.out.println("Cliente: " + cliente.getNombre());
        
        celularV.listar();
        while(true){
            int celularId = v.validarEntero("Id del celular (0 para terminar): ");
            if(celularId == 0){
                break;
            }
            int cantidad = v.validarEntero("Cantidad: ");
            try{
                DetalleVenta d = ventaC.agregarCarrito(celularId, cantidad);
                System.out.println("Agregado :)");
            }catch(NegocioException e){
                System.out.println("Error: " + e.getMessage());
            }catch(RuntimeException e){
                System.out.println("Eror del sistema, intente mas tarde");
            }
        }
        
        if(ventaC.getCarrito().isEmpty()){
            System.out.println("Venta cancelada: no agrego celulares");
            return;
        }
        
        System.out.println("\nSubtotal del carrito: $"+ String.format("%,.0f", ventaC.subtotalCarrito()));
        String c = v.validarTexto("Confirmar la venta? (s/n)");
        if(!c.equalsIgnoreCase("s")){
            ventaC.cancelarVenta();
            System.out.println("Venta cancelada");
            return;
        }
        
        try{
            Venta v = ventaC.confirmarVenta(cliente, vendedor);
            System.out.println("\n*** VENTA REGISTRADA (id " + v.getId() + ") ***");
            System.out.println("Subtotal: $" + String.format("%,.0f", v.getSubtotal()));
            System.out.println("IVA 19%:  $" + String.format("%,.0f", v.getIva()));
            System.out.println("TOTAL:    $" + String.format("%,.0f", v.getTotal()));
        }catch(NegocioException e){
            System.out.println("Errlr: " + e.getMessage());
            ventaC.cancelarVenta();
        }catch(RuntimeException e){
            System.out.println("Error en el sistema, la venta no se guardo");
            ventaC.cancelarVenta();
        }
    }
    
}
