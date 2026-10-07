package Main;

import dao.Conexion;
import dao.MarcaDAO;

public class Tecnostore {

    public static void main(String[] args) {
        MarcaDAO dao = new MarcaDAO();
    dao.listar().forEach(System.out::println);
    System.out.println(dao.buscarId(2));   // debería imprimir Apple
    System.out.println(dao.buscarId(99));
    }
}
