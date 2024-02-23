package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.HashSet;

public interface AuthDAO {

    void insertAuth(AuthData authData);

    HashMap getAuthMap();

    void clear();
}
