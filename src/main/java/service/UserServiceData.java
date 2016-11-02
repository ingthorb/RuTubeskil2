package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import data.AccountDataGateway;
import data.UserDataGateway;
import data.VideoDataGateway;
import exceptions.*;
import is.ruframework.data.RuDataAccessFactory;
import is.ruframework.domain.RuException;
import models.CloseFriendsModel;
import models.FavoriteVideosModel;
import models.UserModel;
import models.VideoModel;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
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
        accountDataGateway = (AccountDataGateway)factory.getDataAccess("AccountDataGateway");
    }

    @POST
    @Path("/closefriends")
    @Produces(VideoModel.mediaType)
    public Response addCloseFriend(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException {

        Object closeFriend = null;
        Gson gson = new Gson();

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

        int closeFriendId = 0;
        try {
            closeFriendId = userDataGateway.addCloseFriend((CloseFriendsModel)closeFriend, userId);
        } catch (UserNotFoundException e) {
            String exception = gson.toJson(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(exception).build();
        } catch (UserAlreadyACloseFriendException e) {
            String exception = gson.toJson(e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(exception).build();
        }
        String addedVideo = gson.toJson(closeFriendId);
        return Response.status(Response.Status.OK).entity(addedVideo).build();
    }

    @DELETE
    @Path("/closefriends/{id}")
    @Produces(CloseFriendsModel.mediaType)
    public Response deleteCloseFriend(@HeaderParam("authorization") String authorization,@PathParam("id") int friendId) throws JsonProcessingException {
        int userId;
        Object closeFriend = null;
        Gson gson = new Gson();

        try {
            userId = accountDataGateway.getUserIdFromToken(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", "Wrong token - You need to be signed in to perform this action ");
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        try {
            userDataGateway.DeleteCloseFriend(friendId,userId);
        } catch (UserNotFoundException e) {
            String exception = gson.toJson(e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(exception).build();        }
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/favoriteVideos")
    @Produces(VideoModel.mediaType)
    public Response addFavoriteVideo(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException {

        FavoriteVideosModel favoritVideo = null;
        Gson gson = new Gson();

        int userId;

        try {
            userId = accountDataGateway.getUserIdFromToken(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", "Wrong token - You need to be signed in to perform this action ");
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        try {
            favoritVideo = (FavoriteVideosModel) mapper(body, FavoriteVideosModel.class);
        } catch (IOException e) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        int favoritVideoId = 0;

        try {
            favoritVideoId = userDataGateway.addFavoriteVideo(favoritVideo, userId);
        } catch (videoNotFoundException e) {
            String exception = gson.toJson(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(exception).build();
        } catch (videoAlreadyAFavoritVideoxception e) {
            String exception = gson.toJson(e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(exception).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/favoriteVideos/{id}")
    @Produces(FavoriteVideosModel.mediaType)
    public Response deleteFavoritVideo(@HeaderParam("authorization") String authorization,@PathParam("id") int videoId) throws JsonProcessingException {
        int userId;
        Gson gson = new Gson();

        try {
            userId = accountDataGateway.getUserIdFromToken(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", "Wrong token - You need to be signed in to perform this action ");
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        try {
            userDataGateway.DeleteFavoritVideo(videoId,userId);
        } catch (videoNotFoundException e) {
            String exception = gson.toJson(e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(exception).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Produces(UserModel.mediaType)
    public Response updateUserInfo(@HeaderParam("authorization") String authorization,String body) throws JsonProcessingException {
        int userId;
        Gson gson = new Gson();

        UserModel user = null;

        try {
            userId = accountDataGateway.getUserIdFromToken(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", "Wrong token - You need to be signed in to perform this action ");
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        try {
            user = (UserModel) mapper(body, UserModel.class);
        } catch (IOException e) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        UserModel userChanged = null;
        try {
            userChanged = userDataGateway.updateUser(user,userId);
        } catch (UserAlreadyExistsException e) {
            String exception = gson.toJson(e.getMessage());
            return Response.status(Response.Status.CONFLICT).entity(exception).build();
        }

        String userChangedInfo = gson.toJson(userChanged);
        return Response.status(Response.Status.OK).entity(userChangedInfo).build();
    }

    @GET
    @Produces(UserModel.mediaType)
    public Response profileInfo(@HeaderParam("authorization") String authorization) throws JsonProcessingException {
        Gson gson = new Gson();
        int userId;

        try {
            userId = accountDataGateway.getUserIdFromToken(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", "Wrong token - You need to be signed in to perform this action ");
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        JSONObject userInfo = new JSONObject();
        userInfo.put("User information", userDataGateway.getUser(userId));
        userInfo.put("Favorite videos", userDataGateway.getFavoriteVideos(userId));
        userInfo.put("Close friends", userDataGateway.getCloseFriends(userId));
        String userInformation = gson.toJson(userInfo);
        return Response.status(Response.Status.OK).entity(userInformation).build();
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
