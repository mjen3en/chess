package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashSet;

public interface UserDAO {

    UserData getUser(UserData userData);

    void insertUser(UserData userData);


}
