package service;

import exceptions.InvalidPasswordException;
import exceptions.UnauthorizedException;
import exceptions.UserAlreadyExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.AccountDataGateway;
import is.ruframework.data.RuDataAccessFactory;
import is.ruframework.domain.RuException;
import models.ChangePasswordModel;
import models.TokenModel;
import models.UserModel;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Created by Laufey on 31/10/2016.
 */
@Path("/accounts")
public class AccountServiceData implements AccountService{

    AccountDataGateway accountDataGateway;

    public AccountServiceData() {

        RuDataAccessFactory factory = null;
        try {
            factory = RuDataAccessFactory.getInstance("data.xml");
        } catch (RuException e) {
            e.printStackTrace();
        }

        accountDataGateway = (AccountDataGateway)factory.getDataAccess("AccountDataGateway");
    }

    @POST
    @Path("/user/")
    @Produces("application/json")
    public Response signup(String body) throws JsonProcessingException{

        int userId = 0;
        Object user = null;
        try {
            user = mapper(body, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        try {
            userId = accountDataGateway.addUser((UserModel)user);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            JSONObject userExists = new JSONObject();
            userExists.put("Explanation", "User with this username already exists");
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(userExists.toJSONString()).build();
        }


        String token = CreateAToken();
        TokenModel tokenData = new TokenModel(((UserModel) user).getUserName(),token);
        accountDataGateway.addToken(tokenData);

        JSONObject jsonUser = new JSONObject();
        jsonUser.put("id", userId);
        jsonUser.put("token", token);

        return Response.status(Response.Status.CREATED).entity(jsonUser.toJSONString()).build();
    }

    @POST
    @Path("/login/")
    @Produces(UserModel.mediaType)
    public Response login(String body) throws JsonProcessingException {

        JSONObject jsonToken = new JSONObject();
        Object user = null;
        try {
            user = mapper(body, UserModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        UserModel existingUser = accountDataGateway.checkIfUserExists((UserModel)user);

        if(existingUser == null) {
            JSONObject userDoesNotExist = new JSONObject();
            userDoesNotExist.put("Explanation", "User with this username does not exist");
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(userDoesNotExist.toJSONString()).build();
        }

        if(!accountDataGateway.checkIfPasswordMatches(((UserModel) user).getPassword(), existingUser.getPassword())){
            JSONObject wrongPassword = new JSONObject();
            wrongPassword.put("Explanation", "Incorrect password");
            return Response.status(Response.Status.PRECONDITION_FAILED).entity(wrongPassword.toJSONString()).build();
        }

        String token = CreateAToken();
        TokenModel tokenData = new TokenModel(((UserModel) user).getUserName(),token);
        accountDataGateway.addToken(tokenData);

        jsonToken.put("token", token);
        return Response.status(Response.Status.OK).entity(jsonToken.toJSONString()).build();
    }

    @POST
    @Path("/user/password/")
    @Produces(ChangePasswordModel.mediaType)
    public Response updateUserPw(String body,@HeaderParam("authorization") String authorization) throws JsonProcessingException {
        String username;

        Object password = null;
        try {
            password = mapper(body, ChangePasswordModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        try {
            username = accountDataGateway.getUserNameFromToken(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", "Wrong token - You need to be signed in to perform this action ");
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }

        try {
            accountDataGateway.ChangePassword((ChangePasswordModel) password, username);
        } catch (InvalidPasswordException e) {
            JSONObject wrongPassword = new JSONObject();
            wrongPassword.put("Explanation", "Incorrect password");
            return Response.status(Response.Status.UNAUTHORIZED).entity(wrongPassword.toJSONString()).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/user/{id}/")
    @Produces("application/json")
    public Response deleteUser(@PathParam("id") int id,@HeaderParam("authorization") String authorization) throws JsonProcessingException {

        //TODO test
        try {
            accountDataGateway.getUserNameFromToken(authorization);
        } catch (UnauthorizedException e) {
            JSONObject unauthorized = new JSONObject();
            unauthorized.put("Explanation", "Wrong Token");
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorized.toJSONString()).build();
        }
        accountDataGateway.DeleteUser(id);
        return Response.status(Response.Status.OK).build();
    }


    public Object mapper(String body, Class model) throws IOException{
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

    public String CreateAToken() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();
        return token;
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
