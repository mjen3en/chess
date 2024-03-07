package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import request.RegisterRequest;
import result.RegisterResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        if (request.username() == null || request.password() == null || request.email() == null){
            throw new DataAccessException("Error: bad request");
        }

        //encrypt password
        String encryptedPassword = encodePassword(request.password());

        UserData userData = new UserData(request.username(), encryptedPassword, request.email());
        //get user
        if (userDAO.getUser(userData.username) != null){
            throw new DataAccessException("Error: already taken");
        }
        //create user
        userDAO.insertUser(userData);

        //create auth
        return new RegisterResult(request.username(), authDao.insertAuth(request.username()));
    }

    static String encodePassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }


}
