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

    public Response addCloseFriend(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException;

    public Response deleteCloseFriend(@HeaderParam("authorization") String authorization,@PathParam("id") int friendId) throws JsonProcessingException;

    public Object mapper(String body, Class model) throws IOException;

}
