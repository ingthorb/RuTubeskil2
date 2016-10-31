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

    /**
     *
     * @param user
     * @return
     * @throws UserAlreadyExistsException
     */
    public int addUser(UserModel user) throws UserAlreadyExistsException;

    /**
     *
     * @param token
     * @return
     */
    public int addToken(TokenModel token);

    /**
     *
     * @param user
     * @return
     */
    public UserModel checkIfUserExists(UserModel user);

    /**
     *
     * @param token
     * @param userExists
     * @return
     */
    public boolean checkIfPasswordMatches(String token, String userExists);

    /**
     *
     * @param token
     * @return
     * @throws UnauthorizedException
     */
    public String getUserNameFromToken(String token) throws UnauthorizedException;

    /**
     *
     * @param passwordModel
     * @param username
     * @throws InvalidPasswordException
     */
    public void ChangePassword(ChangePasswordModel passwordModel, String username) throws InvalidPasswordException;

    /**
     *
     * @param id
     */
    public void DeleteUser(int id);
}
