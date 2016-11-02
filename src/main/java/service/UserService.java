package service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Laufey on 01/11/2016.
 */
public interface UserService {

    /**
     * Enables the user to delete himself
     * Full url is "127.0.0.1:8080/accounts/user
     *
     * @param authorization the token the user was given when he loged in/signed up
     * @return status code 200 if the user was sussesfully deleted
     * @throws JsonProcessingException
     */

    /**
     *  Full url is "127.0.0.1:8080/
     *
     * @param body
     * @param authorization the token the user was given when he loged in/signed up
     * @return
     * @throws JsonProcessingException
     */
    public Response addCloseFriend(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException;

    /**
     *
     * @param authorization the token the user was given when he loged in/signed up
     * @param friendId
     * @return
     * @throws JsonProcessingException
     */
    public Response deleteCloseFriend(@HeaderParam("authorization") String authorization,@PathParam("id") int friendId) throws JsonProcessingException;

    /**
     *
     * @param body
     * @param authorization the token the user was given when he loged in/signed up
     * @return
     * @throws JsonProcessingException
     */
    public Response addFavoriteVideo(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException;

    /**
     *
     * @param authorization the token the user was given when he loged in/signed up
     * @param videoId
     * @return
     * @throws JsonProcessingException
     */
    public Response deleteFavoritVideo(@HeaderParam("authorization") String authorization,@PathParam("id") int videoId) throws JsonProcessingException;

    /**
     *
     * @param authorization the token the user was given when he loged in/signed up
     * @param body
     * @return
     * @throws JsonProcessingException
     */
    public Response updateUserInfo(@HeaderParam("authorization") String authorization,String body) throws JsonProcessingException ;

    /**
     *
     * @param authorization the token the user was given when he loged in/signed up
     * @return
     * @throws JsonProcessingException
     */
    public Response profileInfo(@HeaderParam("authorization") String authorization) throws JsonProcessingException ;

    /**
     *  Maps the body parameters to the UserModel URL
     * @param body The body should include fullName, userName and password (REQUIRED) 
     * @param model the model we want to map to
     * @return Object with the User
     * @throws IOException when unable to map ot object
     */
    public Object mapper(String body, Class model) throws IOException;

}
