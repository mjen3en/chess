package dataAccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{

    public static HashMap<String, AuthData> authSet = new HashMap<>();
    @Override
    public void insertAuth(AuthData authData) {
        authSet.put(authData.getAuthToken(), authData);
    }
}
