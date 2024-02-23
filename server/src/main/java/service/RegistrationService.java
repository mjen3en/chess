package service;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import result.RegisterResult;

import java.util.HashSet;
import java.util.UUID;

public class RegistrationService {

    private final AuthDAO authDao;
    private final UserDAO userDAO;

    public RegistrationService(AuthDAO aDao, UserDAO uDao){
        authDao = aDao;
        userDAO = uDao;
    }

    public RegisterResult register(String username, String password, String email){
        UserData userData = new UserData();
        userData.setUsername(username);
        userData.setPassword(password);
        userData.setEmail(email);
        //get user
        userDAO.getUser(userData);

        //create user
        userDAO.insertUser(userData);

        //create auth
        RegisterResult result = new RegisterResult(username, insertAuth(createAuth(username)));
        return result;
    }


    private AuthData createAuth(String username){
        AuthData aData = new AuthData();
        aData.setUsername(username);
        aData.setAuthToken(getAuthToken());
        return aData;

    }

    private String getAuthToken(){
        return UUID.randomUUID().toString();
    }

    private String insertAuth(AuthData authData){
        //inserts Auth into AuthDAO
        authDao.insertAuth(authData);
        return authData.getAuthToken();
    }


}
