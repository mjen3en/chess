package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    public static HashMap<String, AuthData> authMap = new HashMap<>();
    @Override
    public String getAuth(String username) {
        AuthData authData = createAuthData(username);
        authMap.put(authData.getAuthToken(), authData);
        return authData.getAuthToken();
    }

    @Override
    public HashMap getAuthMap() {
        return authMap;
    }

    @Override
    public void clear() {
        authMap.clear();
    }

    @Override
    public AuthData createAuthData(String username) {
        AuthData aData = new AuthData();
        aData.setUsername(username);
        aData.setAuthToken(getAuthToken());
        return aData;
    }

    @Override
    public String getAuthToken(){
        return UUID.randomUUID().toString();
    }
}


