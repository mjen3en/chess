package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.HashSet;

public interface AuthDAO {

    String insertAuth(String username) throws DataAccessException;

    HashMap getAuthMap();

    void clear() throws DataAccessException;

    void deleteAuth(String authToken);

    AuthData getAuthData(String authToken) throws DataAccessException;

    boolean checkAuthToken(String authToken) throws DataAccessException;



}
