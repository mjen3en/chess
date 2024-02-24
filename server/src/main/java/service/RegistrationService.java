package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import request.RegisterRequest;
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

    public RegisterResult register(RegisterRequest request) throws DataAccessException{
        UserData userData = new UserData(request.username(), request.password(), request.email());
        //get user
        if (userDAO.getUser(userData.username) != null){
            throw new DataAccessException("Already taken");
        }
        //create user
        userDAO.insertUser(userData);

        //create auth
        return new RegisterResult(request.username(), authDao.getAuth(request.username()));
    }


}
