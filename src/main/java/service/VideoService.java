package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import exceptions.UnauthorizedException;
import models.VideoModel;
import org.json.simple.JSONObject;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

/**
 * Created by Laufey on 01/11/2016.
 */
public interface VideoService {

    /**
     *
     * @param body
     * @param authorization
     * @return
     * @throws JsonProcessingException
     */
    public Response addVideo(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException ;

    /**
     *
     * @param authorization
     * @return
     * @throws JsonProcessingException
     */
    public Response ListOfVideos( @HeaderParam("authorization")String authorization) throws JsonProcessingException;

    /**
     *
     * @param body
     * @param authorization
     * @return
     * @throws JsonProcessingException
     */
    public Response addChannel(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException;

    /**
     *
     * @param body
     * @param authorization
     * @return
     * @throws JsonProcessingException
     */
    public Response addVideoToChannel(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException;

    /**
     *
     * @param id
     * @param authorization
     * @return
     * @throws JsonProcessingException
     */
    public Response listOfVideosInChannel(@PathParam("id") int id, @HeaderParam("authorization")  String authorization )throws JsonProcessingException;

    /**
     *
     * @param id
     * @param authorization
     * @return
     * @throws JsonProcessingException
     */
    public Response removeVideo(@PathParam("id") int id, @HeaderParam("authorization")  String authorization )throws JsonProcessingException;

    /**
     *
     * @param body
     * @param model
     * @return
     * @throws IOException
     */
    public Object mapper(String body, Class model) throws IOException;


    }
