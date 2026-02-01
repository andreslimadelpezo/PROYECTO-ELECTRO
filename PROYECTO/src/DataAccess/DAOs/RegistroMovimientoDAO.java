package DataAccess.DAOs;

import DataAccess.DTOs.RegistroMovimientoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class RegistroMovimientoDAO extends DataHelperSQLiteDAO<RegistroMovimientoDTO> {
    
    public RegistroMovimientoDAO() throws AppException {
        super(RegistroMovimientoDTO.class, "RegistroMovimiento", "IdRegistro");
    }

}