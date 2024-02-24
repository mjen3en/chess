package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.HashSet;

public interface UserDAO {

    UserData getUser(String username);

    void insertUser(UserData userData);

    HashMap getUserMap();

    void clear();


}
