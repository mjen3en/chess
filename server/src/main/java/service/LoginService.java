package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import request.LoginRequest;
import request.LogoutRequest;
import result.LoginResult;
import result.LogoutResult;

public class LoginService {

    private final AuthDAO authDao;
    private final UserDAO userDAO;

    public LoginService(AuthDAO aDao, UserDAO uDao){
        authDao = aDao;
        userDAO = uDao;
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        // get user from DAO
        UserData userData = userDAO.getUser(request.username());

        String encodedPassword = encodePassword(request.password());

        //check password
        if (userData == null) {
            throw new DataAccessException("Error: unauthorized");
        } else if (!(checkPassword(request, userData))) {
            throw new DataAccessException("Error: unauthorized");
        }
        //create authToken
        // create and return Login Result
        return new LoginResult(request.username(), authDao.insertAuth(request.username()));
    }

    public LogoutResult logout(LogoutRequest request, String authToken) throws DataAccessException{
        //gets username from authMap
        AuthData authData = authDao.getAuthData(authToken);

        if (authData == null){
            throw new DataAccessException("Error: unauthorized");
        }

        //removes auth from authMap
        authDao.deleteAuth(authToken);

        //removes username and Authdata from userMap
        userDAO.deleteUser(authData.getUsername());

        return new LogoutResult("{}");
    }

    private boolean checkPassword(LoginRequest request, UserData userData){
        String encryptedPassword = encodePassword(request.password());
        return encryptedPassword.equals(userData.getPassword());
    }

    private String encodePassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
