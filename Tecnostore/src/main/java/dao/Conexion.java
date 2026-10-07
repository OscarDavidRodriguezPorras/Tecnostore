package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
        public Connection conexion(){
        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostoreoscar_db","root", "1101261349");
            System.out.println("Conexion exitosa");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return c;
    }
}