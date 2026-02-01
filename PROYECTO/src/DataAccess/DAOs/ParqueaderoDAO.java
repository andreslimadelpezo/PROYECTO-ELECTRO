package DataAccess.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DataAccess.DTOs.ParqueaderoDTO;
import DataAccess.Helpers.DataHelperSQLiteDAO;
import Infrastructure.AppException;

public class ParqueaderoDAO extends DataHelperSQLiteDAO<ParqueaderoDTO> {

    public ParqueaderoDAO() throws AppException {
        super(ParqueaderoDTO.class, "Parqueadero", "IdParqueadero");
    }


    public int getOcupados() throws AppException {
    String sql = "SELECT COUNT(*) AS Ocupados FROM RegistroMovimiento WHERE Estado = 'A'";
    try (Connection conn = openConnection(); 
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        return rs.next() ? rs.getInt("Ocupados") : 0;
    } catch (SQLException e) {
        throw new AppException("Error al contar ocupados", e, getClass(), "getCantidadOcupados");
    }
    }

  
    public ParqueaderoDTO obtenerResumen() throws AppException {
        String sql = "SELECT P.Nombre, P.CapacidadTotal, " +
                     "COUNT(I.IdIngreso) AS Ocupados, " +
                     "(P.CapacidadTotal - COUNT(I.IdIngreso)) AS Libres " +
                     "FROM Parqueadero P " +
                     "LEFT JOIN IngresoVehiculo I ON P.IdParqueadero = I.IdParqueadero AND I.Estado = 'D' " +
                     "WHERE P.IdParqueadero = 1 " +
                     "GROUP BY P.IdParqueadero";

        try (Connection conn = openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
     
                return mapResultSetToEntity(rs);
            }
        } catch (SQLException e) {
            throw new AppException("Error al obtener resumen de parqueadero", e, getClass(), "obtenerResumen");
        }
        return null;
    }
}