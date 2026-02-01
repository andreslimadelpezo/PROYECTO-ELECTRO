package DataAccess.DTOs;

public class RegistroMovimientoDTO {
    private Integer  IdRegistro       ; 
    private Integer  IdParqueadero    ; 
    private String  Placa            ; 
    private String  NombreResponsable; 
    private String  CedulaResponsable; 
    private String  FechaIngreso     ; 
    private String  FechaSalida      ; 
    private String  Estado           ; 
    private String  FechaCreacion    ; 
    private String  FechaModifica    ;
    
    public RegistroMovimientoDTO(String placa, String nombre, String cedula) {
        this.Placa = placa;
        this.NombreResponsable = nombre;
        this.CedulaResponsable = cedula;
        this.IdParqueadero = 1; 
    }

    public RegistroMovimientoDTO() {
    }
    
    public RegistroMovimientoDTO(Integer idRegistro, Integer idParqueadero, String placa, String nombreResponsable,
            String cedulaResponsable, String fechaIngreso, String fechaSalida, String estado, String fechaCreacion,
            String fechaModifica) {
        IdRegistro = idRegistro;
        IdParqueadero = idParqueadero;
        Placa = placa;
        NombreResponsable = nombreResponsable;
        CedulaResponsable = cedulaResponsable;
        FechaIngreso = fechaIngreso;
        FechaSalida = fechaSalida;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }
    public Integer getIdRegistro() {
        return IdRegistro;
    }
    public void setIdRegistro(Integer idRegistro) {
        IdRegistro = idRegistro;
    }
    public Integer getIdParqueadero() {
        return IdParqueadero;
    }
    public void setIdParqueadero(Integer idParqueadero) {
        IdParqueadero = idParqueadero;
    }
    public String getPlaca() {
        return Placa;
    }
    public void setPlaca(String placa) {
        Placa = placa;
    }
    public String getNombreResponsable() {
        return NombreResponsable;
    }
    public void setNombreResponsable(String nombreResponsable) {
        NombreResponsable = nombreResponsable;
    }
    public String getCedulaResponsable() {
        return CedulaResponsable;
    }
    public void setCedulaResponsable(String cedulaResponsable) {
        CedulaResponsable = cedulaResponsable;
    }
    public String getFechaIngreso() {
        return FechaIngreso;
    }
    public void setFechaIngreso(String fechaIngreso) {
        FechaIngreso = fechaIngreso;
    }
    public String getFechaSalida() {
        return FechaSalida;
    }
    public void setFechaSalida(String fechaSalida) {
        FechaSalida = fechaSalida;
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
