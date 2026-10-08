package dao;

public class CelularDAO {
    private static final String SELECT_BASE=
            "select c.id, c.modelo, c.precio, c.stock, "
            + "m.id as marca_id, m.nombre as nombre_marca, "
            + "so.id as so_id, so.nombre as nombre_so, "
            + "g.id as gama_id, g.nombre as nombre_gama, "
            + "from celulares c "
            + "join marcas m on c.marca_id = m.id "
            + "join sistemas_operativos so on c.sistema_operativo_id = so.id "
            + "join gamas g on c.gama_id = g.id ";
    
    private Celular mapear()
}
