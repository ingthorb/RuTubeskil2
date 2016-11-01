package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.VideoDataGataway;
import is.ruframework.data.RuDataAccessFactory;
import is.ruframework.domain.RuException;
import models.HelloWorldModel;

import javax.ws.rs.*;

/**
 * Created by Laufey on 01/11/2016.
 */
@Path("/videos")
public class VideoServiceData implements VideoService {

    VideoDataGataway videoDataGataway;

    public VideoServiceData() {

        RuDataAccessFactory factory = null;
        try {
            factory = RuDataAccessFactory.getInstance("data.xml");
        } catch (RuException e) {
            e.printStackTrace();
        }

        videoDataGataway = (VideoDataGataway)factory.getDataAccess("VideoDataGataway");
    }

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
