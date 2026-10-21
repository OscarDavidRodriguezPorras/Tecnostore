package service;

import Model.Celular;
import dao.CelularDAO;
import exception.NegocioException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class AlertaStockService {

    public static final int UMBRAL = 5;
    public static final String ARCHIVO = "stock_bajo.txt";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final CelularDAO celularDAO = new CelularDAO();
    
    public List<Celular> obtenerStockBajo() {
        List<Celular> lista = celularDAO.listarStockBajo();
        if (lista.isEmpty()) {
            throw new NegocioException("No hay celulares con stock bajo (<= " + UMBRAL + ")");
        }
        return lista;
    }

    public String formatearLinea(Celular c) {
        return String.format("ID: %d | Modelo: %s | Marca: %s | Stock: %d | Precio: %.0f",
                c.getId(), c.getModelo(), c.getMarca(), c.getStock(), c.getPrecio());
    }

    public List<String> formatearLineas(List<Celular> celulares) {
        return celulares.stream()
                .map(this::formatearLinea)
                .collect(Collectors.toList());
    }

    public void generarArchivo(List<Celular> celulares) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(Paths.get(ARCHIVO), StandardCharsets.UTF_8)) {
            w.write("--- ALERTA DE STOCK BAJO ---");
            w.newLine();
            w.write("Generado: " + LocalDateTime.now().format(FORMATO_FECHA));
            w.newLine();
            w.newLine();
            for (String linea : formatearLineas(celulares)) {
                w.write(linea);
                w.newLine();
            }
            w.newLine();
            w.write("Total de modelos en alerta: " + celulares.size());
            w.newLine();
        }
    }
}