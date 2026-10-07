package Model;

public class Usuario {
    private int id;
    private String nombre;
    private String Usuario;
    private String password;
    private Rol rol;
    
    public Usuario(){
        
    }
    
    public Usuario(int id, String nombre, String Usuario, String password, Rol rol){
        this.id=id;
        this.nombre=nombre;
        this.Usuario=password;
        this.rol=rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    
}
