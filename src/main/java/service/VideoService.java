package service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;

/**
 * Created by Laufey on 01/11/2016.
 */
public interface VideoService {

    public Response addVideo(String body, @HeaderParam("authorization")  String authorization )throws JsonProcessingException ;

    public Response ListOfVideos( @HeaderParam("authorization")String authorization) throws JsonProcessingException;


    }
