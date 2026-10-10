package Main;

import dao.CelularDAO;
import java.sql.SQLException;

public class Tecnostore {

    public static void main(String[] args) throws SQLException {
        CelularDAO dao = new CelularDAO();
        dao.listar().forEach(System.out::println);
        System.out.println(dao.buscarId(1));
        System.out.println(dao.buscarId(99));
        dao.actualizarStock(1, 9);
        System.out.println(dao.buscarId(1));
        dao.actualizarStock(1, 10);
    }
}
