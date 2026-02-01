package BusinessLogic;

import java.util.List;
import DataAccess.DAOs.ParqueaderoDAO;
import DataAccess.DTOs.ParqueaderoDTO;
import Infrastructure.AppException;

public class ParqueaderoBL {
    private static final int DISTANCIA_MAX = 5; 
    
    ParqueaderoDAO ParDAO;
    ParqueaderoDTO ParDTO;

    public ParqueaderoBL() throws AppException {
        ParDAO = new ParqueaderoDAO();
        ParDTO = new ParqueaderoDTO();    
    }

    public boolean tieneEspacio() throws AppException {
        ParqueaderoDTO resumen = getResumenEstado();
        return resumen != null && resumen.getLibres() > 0;
    }

    public boolean esVehiculoValido(int distancia) {
        return distancia > 0 && distancia <= DISTANCIA_MAX;
    }

   
    public ParqueaderoDTO getResumenEstado() throws AppException {
    ParqueaderoDTO parqueadero = ParDAO.readBy(1); 
    
    if (parqueadero != null) {
        int ocupados = ParDAO.getOcupados(); 
        int libres = parqueadero.getCapacidadTotal() - ocupados;

        parqueadero.setLibres(libres);
        parqueadero.setOcupados(ocupados); 

    }
    
    return parqueadero;
}

    public List<ParqueaderoDTO> leerTodos() throws AppException {
        return ParDAO.readAll();
    }
}