package service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Laufey on 31/10/2016.
 */
public interface AccountService {

    /** Sign up user. 
     * Full url is "127.0.0.1:8080/accounts/user/" 
     *
     * @param body The body should include fullName, userName and password (REQUIRED) 
     * @return The id, fullName, userName and password for the created user 
     * @throws JsonProcessingException  */
    public Response signup(String body) throws JsonProcessingException;


    /** Login user. 
     * Full url is "127.0.0.1:8080/accounts/login/" 
     *
     * @param body The body should include userName and password (REQUIRED) 
     * @return  The userName and token
     * @throws JsonProcessingException  */
    public Response login(String body) throws JsonProcessingException ;

    /**
     * Update User password
     * Full url is "127.0.0.1:8080/accounts/user/password/" 
     *
     * @param body The body should include old password and new password (REQUIRED) 
     * @param authorization the token the user was given when he loged in/signed up
     * @return Status code 200 if password was changed
     * @throws JsonProcessingException
     */
    public Response updateUserPw(String body,@HeaderParam("authorization") String authorization) throws JsonProcessingException;


    /**
     * Enables the user to delete himself
     * Full url is "127.0.0.1:8080/accounts/user
     *
     * @param authorization the token the user was given when he loged in/signed up
     * @return status code 200 if the user was sussesfully deleted
     * @throws JsonProcessingException
     */
    public Response deleteUser( @HeaderParam("authorization") String authorization) throws JsonProcessingException;

    /**
     *  Maps the body parameters to the UserModel URL
     * @param body The body should include fullName, userName and password (REQUIRED) 
     * @param model the model we want to map to
     * @return Object with the User
     * @throws IOException when unable to map ot object
     */
    public Object mapper(String body, Class model) throws IOException;


    /**
     * Creates a new token for a user
     * @return the created token
     */
    public String CreateAToken();

    }
