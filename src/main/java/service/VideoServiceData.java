package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import data.AccountDataGateway;
import data.VideoDataGateway;
import exceptions.UnauthorizedException;
import exceptions.channelNotFoundException;
import exceptions.videoNotFoundException;
import is.ruframework.data.RuDataAccessFactory;
import is.ruframework.domain.RuException;
import models.ChannelModel;
import models.VideoModel;
import models.VideosInChannelModel;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Created by Laufey on 01/11/2016.
 */
@Path("/videos")
public class VideoServiceData implements VideoService {

    VideoDataGateway videoDataGateway;
    AccountDataGateway accountDataGateway;

    public VideoServiceData() {

        RuDataAccessFactory factory = null;
        try {
            factory = RuDataAccessFactory.getInstance("data.xml");
        } catch (RuException e) {
            e.printStackTrace();
        }

        videoDataGateway = (VideoDataGateway)factory.getDataAccess("VideoDataGateway");
        accountDataGateway = (AccountDataGateway)factory.getDataAccess("AccountDataGateway");
    }

    @POST
    @Produces(VideoModel.mediaType)
    public Response addVideo(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException {

        Object video = null;

        try {
            String username = checkAuthorization(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", e);
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        try {
            video = mapper(body, VideoModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        int videoId = videoDataGateway.addVideo((VideoModel)video);

        Gson gson = new Gson();
        String addedVideo = gson.toJson(videoId);
        return Response.status(Response.Status.OK).entity(addedVideo).build();
    }

    @GET
    @Produces(VideoModel.mediaType)
    public Response ListOfVideos( @HeaderParam("authorization") String authorization ) throws JsonProcessingException {

        try {
            String username = checkAuthorization(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", e);
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        List<VideoModel> videoList = videoDataGateway.getVideos();

        Gson gson = new Gson();
        String videos = gson.toJson(videoList);
        return Response.status(Response.Status.OK).entity(videos).build();
    }

    @POST
    @Path("/channel")
    @Produces(ChannelModel.mediaType)
    public Response addChannel(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException {

        Object channel = null;

        try {
            String username = checkAuthorization(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", e);
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        try {
            channel = mapper(body, ChannelModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        int channelId = videoDataGateway.addChannel((ChannelModel)channel);

        Gson gson = new Gson();
        String addedChannel = gson.toJson(channelId);
        return Response.status(Response.Status.OK).entity(addedChannel).build();
    }

    @POST
    @Path("/channel/video")
    @Produces(ChannelModel.mediaType)
    public Response addVideoToChannel(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException {

        Object videoToChannel = null;

        try {
            String username = checkAuthorization(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", e);
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        try {
            videoToChannel = mapper(body, VideosInChannelModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        JSONObject videoNotFound = new JSONObject();
        try {
            videoDataGateway.addVideoToChannel((VideosInChannelModel)videoToChannel);
        } catch (videoNotFoundException ex) {

            Gson gson = new Gson();
            String exception = gson.toJson(ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(exception).build();
        }catch(channelNotFoundException ex){
            Gson gson = new Gson();
            String exception = gson.toJson(ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(exception).build();
        }
        return Response.status(Response.Status.OK).build();
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

    public String checkAuthorization(String authorization) throws UnauthorizedException{
        String username = "";
        try {
            username = accountDataGateway.getUserNameFromToken(authorization);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException( "Wrong token - You need to be signed in to perform this action ");
        }
        return username;
    }
}