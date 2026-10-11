package Impuesto;

public class Iva implements CalculoImpuesto{
   private static final double TASA = 0.19;
    @Override
    public double calcular(double subtotal) {
        return subtotal * TASA;
    }
    
}
