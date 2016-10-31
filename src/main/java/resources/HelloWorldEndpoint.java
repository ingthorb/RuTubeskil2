package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.HelloWorldModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.*;


/**
 *  Endpoint class, with root path as /hello
 *  All endpoints in this class will go under /hello,
 *  if not defined any more specific on the method, the method can be called using only /hello
 *  returns json string out.
 */
@Path("/hello")
public class HelloWorldEndpoint {


    /** Hellow world endpoint.
     * Full url is "127.0.0.1:8080/hello/world/{name}?lastname={last_name}
     *
     * Example: curl 127.0.0.1:8080/hello/world/Josh?lastName=Finnick
     * returns: Hello Josh Finnick
     *
     * @param name First name of a user. (REQUIRED)
     * @param lastName Lastname of a user (OPTIONAL)
     * @return Hello {name} {lastName}
     */
    @GET
    @Path("/world/{name}")
    @Produces(HelloWorldModel.mediaType)
    public String getHelloWorld(@PathParam("name") String name,
                                @QueryParam("lastName") String lastName) throws JsonProcessingException {
        System.out.println("In the hello world endpoint");

        ObjectMapper mapper = new ObjectMapper();
        String fullName = lastName != null ? name + " " + lastName  : name;

        HelloWorldModel m = new HelloWorldModel(fullName);
        return mapper.writeValueAsString(m);

    }


}
