package resources;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Laufey on 31/10/2016.
 */
public interface AccountService {

    /** Signup user endpoint. 
     * Full url is "127.0.0.1:8080/rutube/user/" 
     * Example: curl 127.0.0.1:8080/rutube/user/ 
     *
     * returns: The id, fullName, userName and password for the created user 
     * @param body The body should include fullName, userName and password (REQUIRED) 
     * @return The id, fullName, userName and password for the created user 
     * @throws JsonProcessingException  */
    public Response signup(String body) throws JsonProcessingException;


    /** Login user endpoint. 
     * Full url is "127.0.0.1:8080/rutube/login/" 
     * Example: curl 127.0.0.1:8080/rutube/login/ 
     *
     * @param body The body should include userName and password (REQUIRED) 
     * @return  The userName and token
     * @throws JsonProcessingException  */
    public Response login(String body) throws JsonProcessingException ;

    /** Change user password endpoint. 
     * Full url is "127.0.0.1:8080/rutube/user/password/" 
     * Example: curl 127.0.0.1:8080/rutube/user/password/
     *
     * Header Authorization is required
     *
     * returns: The id, fullName, userName and password for the created user 
     * @param body The body should include old password and new password (REQUIRED) 
     * @return The user id
     * @throws JsonProcessingException  */
    public Response updateUserPw(String body,@HeaderParam("authorization") String authorization) throws JsonProcessingException;

    /**
     *
     * @param id
     */
    public Response deleteUser(String body, @PathParam("id") int id, @HeaderParam("authorization") String authorization) throws JsonProcessingException;



    public Object mapper(String body, Class model);

    public String CreateAToken();

}
