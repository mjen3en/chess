package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.HashSet;

public interface UserDAO {

    UserData getUser(String username) throws DataAccessException;

    void insertUser(UserData userData) throws DataAccessException;

    HashMap getUserMap() throws DataAccessException;

    void clear() throws DataAccessException;

    void deleteUser(String username) throws DataAccessException;


}
