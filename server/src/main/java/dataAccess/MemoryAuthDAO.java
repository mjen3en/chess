package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{

    public static HashMap<String, AuthData> authMap = new HashMap<>();

    public MemoryAuthDAO() {
        authMap.clear();
    }
    @Override
    public String insertAuth(String username) {
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
        aData.setAuthToken(createAuthToken());
        return aData;
    }

    @Override
    public String createAuthToken(){
        return UUID.randomUUID().toString();
    }

    @Override
    public AuthData getAuthData(String authToken) {
        return authMap.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) {
        authMap.remove(authToken);
    }

    @Override
    public boolean checkAuthToken(String authToken) {
        if (authMap.get(authToken) != null){
            return true;
        } else{
            return false;
        }
    }

}


