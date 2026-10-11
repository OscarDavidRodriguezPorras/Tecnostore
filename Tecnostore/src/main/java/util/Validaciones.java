package util;

import java.util.Scanner;

public class Validaciones {

    private final Scanner x = new Scanner(System.in);
    
    private static final String REGEX_CORREO = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    
    public static boolean CorreoValido(String c){
        return c != null && c.matches(REGEX_CORREO);
    }
    
    public static boolean Positivo(double n){
        return n > 0;
    }
    
    public static boolean PositivoInt(int n){
        return n>= 0;
    }
    
    public static boolean NoVacio(String t){
        return t != null && !t.trim().isEmpty();
    }
    
    public static boolean IdValida(String id){
        return id != null && id.matches("\\d{5,15}");
    }
    
    public static boolean TelefonoValido(String t){
        return t != null && t.matches("\\d{7,10}");
    }
    
    public String validarTexto(String mensaje) {
        boolean validacion;
        String texto = "";
        Scanner x = new Scanner(System.in);
        int contador = 0;
        do {
            validacion = true;
            System.out.println(mensaje);
            texto = x.nextLine();
            for (int i = 0; i < texto.length(); i++) {
                contador += texto.charAt(i) == ' ' ? 1 : 0;
                if (!Character.isLetter(texto.charAt(i)) || texto.charAt(i) != ' ') {
                    if (texto.charAt(i) == ' ' && i == 0) {
                        validacion = false;
                        break;
                    }
                }
            }
        } while (validacion == false);
        return texto;
    }

    public double validarDecimal(String mensaje) {
        System.out.println(mensaje);
        Scanner x = new Scanner(System.in);
        while (!x.hasNextDouble()) {
            System.out.println("Error, se espera un valor decimal");
        }
        return x.nextDouble();
    }

    public int validarEntero(String mensaje) {
        Scanner x = new Scanner(System.in);
        System.out.println(mensaje);
        while (!x.hasNextInt()) {
            System.out.println("Error, se espera un valor entero");
            x.next();
        }
        return x.nextInt();
    }

    public long validarEnteroGrande(String mensaje) {
        Scanner x = new Scanner(System.in);
        System.out.println(mensaje);
        while (!x.hasNextLong()) {
            System.out.println("Error, se espera un valor entero");
            x.next();
        }
        return x.nextLong();
    }
    
    public double validarPrecio(String mensaje){
        double precio;
        do{
            precio = validarDecimal(mensaje);
            if(!Positivo(precio)){
                System.out.println("Error, el precio debe ser mayor a 0");
            }
        }while(!Positivo(precio));
        return precio;
    }
    
    public int ValidarStock(String mensaje){
        int stock;
        do{
            stock = validarEntero(mensaje);
            if(!PositivoInt(stock)){
                System.out.println("Error, el stock no puede ser negativo");
            }
        }while(!PositivoInt(stock));
        return stock;
    }
    
    public String ValidarCorreo(String mensaje){
        String correo;
        do{
            System.out.println(mensaje);
            correo = x.nextLine().trim();
            if(!CorreoValido(correo)){
                System.out.println("Error, correo invalido");
            }
        }while(!CorreoValido(correo));
        return correo;
    }
    
    public String ValidarId(String mensaje){
        String id;
        do{
            System.out.println(mensaje);
            id = x.nextLine().trim();
            if(!IdValida(id)){
                System.out.println("Error, solo digitos (5 a 15)");
            }
        }while(!IdValida(id));
        return id;
    }
    
    public String ValidarTelefono(String mensaje){
        String tel;
        do{
            System.out.println(mensaje);
            tel = x.nextLine().trim();
            if(!TelefonoValido(tel)){
                System.out.println("Error, solo digitos (7 a 10)");
            }
        }while (!TelefonoValido(tel));
        return tel;
    }
}
