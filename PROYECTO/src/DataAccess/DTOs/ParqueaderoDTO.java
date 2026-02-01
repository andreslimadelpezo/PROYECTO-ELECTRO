package DataAccess.DTOs;

public class ParqueaderoDTO {
    private  Integer IdParqueadero ;
    private  String Nombre        ;
    private  Integer CapacidadTotal ;
    private  String Estado        ;
    private  String FechaCreacion ;
    private  String FechaModifica ;
    public Integer Ocupados; 
    public Integer Libres;
    
    public Integer getOcupados() {
        return Ocupados;
    }
    public void setOcupados(Integer ocupados) {
        Ocupados = ocupados;
    }
    public Integer getLibres() {
        return Libres;
    }
    public void setLibres(Integer libres) {
        Libres = libres;
    }
    public ParqueaderoDTO() {
    }
    public ParqueaderoDTO(Integer idParqueadero, String nombre, Integer capacidadTotal) {
        IdParqueadero = idParqueadero;
        Nombre = nombre;
        CapacidadTotal = capacidadTotal;
    }
    public ParqueaderoDTO(Integer idParqueadero, String nombre, Integer capacidadTotal, String estado,
            String fechaCreacion, String fechaModifica) {
        IdParqueadero = idParqueadero;
        Nombre = nombre;
        CapacidadTotal = capacidadTotal;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }
    public Integer getIdParqueadero() {
        return IdParqueadero;
    }
    public void setIdParqueadero(Integer idParqueadero) {
        IdParqueadero = idParqueadero;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public Integer getCapacidadTotal() {
        return CapacidadTotal;
    }
    public void setCapacidadTotal(Integer capacidadTotal) {
        CapacidadTotal = capacidadTotal;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String estado) {
        Estado = estado;
    }
    public String getFechaCreacion() {
        return FechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }
    public String getFechaModifica() {
        return FechaModifica;
    }
    public void setFechaModifica(String fechaModifica) {
        FechaModifica = fechaModifica;
    }
}
