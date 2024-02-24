package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;
import request.LoginRequest;
import result.LoginResult;

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

        //check password
        if (userData == null) {
            throw new DataAccessException("unauthorized");
        } else if (!(checkPassword(request, userData))) {
            throw new DataAccessException("unauthorized");
        }
        //create authToken
        // create and return Login Result
        return new LoginResult(request.username(), authDao.getAuth(request.username()));
    }

    private boolean checkPassword(LoginRequest request, UserData userData){
        return request.password().equals(userData.getPassword());
    }
}
