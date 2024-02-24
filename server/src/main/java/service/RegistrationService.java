package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
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

    public RegisterResult register(String username, String password, String email) throws DataAccessException{
        UserData userData = new UserData(username, password, email);
        //get user
        if (userDAO.getUser(userData).username != null){
            throw new DataAccessException("Already taken");
        }
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
