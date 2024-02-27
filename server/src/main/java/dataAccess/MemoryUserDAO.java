package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{



    public static HashMap<String, UserData> userMap = new HashMap<>();

    public MemoryUserDAO() {
        userMap.clear();
    }


    @Override
    public UserData getUser(String username) {
        return userMap.get(username);

    }

    @Override
    public void insertUser(UserData userData) {
        userMap.put(userData.username, userData);
    }

    @Override
    public HashMap<String, UserData> getUserMap() {
        return userMap;
    }

    @Override
    public void clear() {
        userMap.clear();
    }

    @Override
    public void deleteUser(String username) {
        userMap.remove(username);
    }
}
