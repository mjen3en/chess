package dataAccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{

    public static HashMap<String, AuthData> authMap = new HashMap<>();
    @Override
    public void insertAuth(AuthData authData) {
        authMap.put(authData.getAuthToken(), authData);
    }

    @Override
    public HashMap getAuthMap() {
        return authMap;
    }

    @Override
    public void clear() {
        authMap.clear();
    }
}
