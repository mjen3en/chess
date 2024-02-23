package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{



    public static HashMap<String, UserData> userMap = new HashMap<>();


    @Override
    public UserData getUser(UserData userData) {
        return userMap.get(userData.username);

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
}
