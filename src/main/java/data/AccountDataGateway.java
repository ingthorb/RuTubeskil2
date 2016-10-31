package data;

import Exceptions.InvalidPasswordException;
import Exceptions.UnauthorizedException;
import Exceptions.UserAlreadyExistsException;
import models.ChangePasswordModel;
import models.TokenModel;
import models.UserModel;

/**
 * Created by Laufey on 31/10/2016.
 */
public interface AccountDataGateway {

    public int addUser(UserModel user) throws UserAlreadyExistsException;

    public int addToken(TokenModel token);

    public UserModel checkIfUserExists(UserModel user);

    public boolean checkIfPasswordMatches(String token, String userExists);

    public String getUserNameFromToken(String token) throws UnauthorizedException;

    public void ChangePassword(ChangePasswordModel passwordModel, String username) throws InvalidPasswordException;
}
