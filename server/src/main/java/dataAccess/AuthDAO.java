package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.HashSet;

public interface AuthDAO {

    String getAuth(String username);

    HashMap getAuthMap();

    void clear();

    AuthData createAuthData(String username);

    String getAuthToken();


}
