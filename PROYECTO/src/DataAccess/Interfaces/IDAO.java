//  © 2K26 ❱──💀──❰ pat_mic ? code is life : life is code
package DataAccess.Interfaces;

import java.util.List;

import Infrastructure.AppException;

public interface IDAO<T> {
    List<T> readAll()            throws AppException;
    T       readBy (Integer id)  throws AppException;
    boolean create (T entity)    throws AppException;
    boolean update (T entity)    throws AppException;
    boolean delete (Integer id)  throws AppException;
    Integer getMaxReg()          throws AppException;
}
