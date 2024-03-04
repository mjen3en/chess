package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.HashSet;

public interface UserDAO {

    UserData getUser(String username);

    void insertUser(UserData userData) throws DataAccessException;

    HashMap getUserMap();

    void clear() throws DataAccessException;

    void deleteUser(String username);


}
