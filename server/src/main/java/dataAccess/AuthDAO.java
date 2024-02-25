package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.HashSet;

public interface AuthDAO {

    String insertAuth(String username);

    HashMap getAuthMap();

    void clear();

    AuthData createAuthData(String username);

    String createAuthToken();

    void deleteAuth(String authToken);

    AuthData getAuthData(String authToken);

    boolean checkAuthToken(String authToken);


}
