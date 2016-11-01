package data;

import exceptions.InvalidPasswordException;
import exceptions.UnauthorizedException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserNotFoundException;
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
     * @throws UserNotFoundException
     */
    public int getUserId(UserModel user) throws UserNotFoundException;

    /**
     *
     * @param token
     * @param userId
     * @return
     */
    public boolean checkIfPasswordMatches(String token, int userId);


    /**
     *
     * @param token
     * @return
     * @throws UnauthorizedException
     */
    public int getUserIdFromToken(String token) throws UnauthorizedException;


        /**
         *
         * @param passwordModel
         * @param userId
         * @throws InvalidPasswordException
         */
    public void ChangePassword(ChangePasswordModel passwordModel, int userId) throws InvalidPasswordException;

    /**
     *
     * @param id
     */
    public void DeleteUser(int id);
}
