package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import data.AccountDataGateway;
import data.UserDataGateway;
import data.VideoDataGateway;
import exceptions.UnauthorizedException;
import is.ruframework.data.RuDataAccessFactory;
import is.ruframework.domain.RuException;
import models.CloseFriendsModel;
import models.VideoModel;
import org.json.simple.JSONObject;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Laufey on 01/11/2016.
 */
@Path("/userservice")
public class UserServiceData implements UserService{

    VideoDataGateway videoDataGateway;
    UserDataGateway userDataGateway;
    AccountDataGateway accountDataGateway;


    public UserServiceData() {

        RuDataAccessFactory factory = null;
        try {
            factory = RuDataAccessFactory.getInstance("data.xml");
        } catch (RuException e) {
            e.printStackTrace();
        }

        videoDataGateway = (VideoDataGateway)factory.getDataAccess("VideoDataGateway");
        userDataGateway = (UserDataGateway)factory.getDataAccess("UserDataGateway");
        accountDataGateway = (AccountDataGateway)factory.getDataAccess("accountDataGateway");

    }

    @POST
    @Path("/closefriends")
    @Produces(VideoModel.mediaType)
    public Response addCloseFriend(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException {

        Object closeFriend = null;

        int userId;

        try {
            userId = accountDataGateway.getUserIdFromToken(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", "Wrong token - You need to be signed in to perform this action ");
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        try {
            closeFriend = mapper(body, CloseFriendsModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        int closeFriendId = userDataGateway.addCloseFriend((CloseFriendsModel)closeFriend, userId);

        Gson gson = new Gson();
        String addedVideo = gson.toJson(closeFriendId);
        return Response.status(Response.Status.OK).entity(addedVideo).build();
    }


    public Object mapper(String body, Class model) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Object user = new Object();

        try {
            user =  mapper.readValue(body,model );
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return user;
    }

}
