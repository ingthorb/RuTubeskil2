import TestModels.Login;
import data.AccountDataGateway;
import exceptions.UnauthorizedException;
import is.ruframework.data.RuData;
import is.ruframework.data.RuDataAccessFactory;
import is.ruframework.domain.RuException;
import models.UserModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import server.App;


/**
 * Created by Ingthor on 1.11.2016.
 */
public class AccountServiceIT extends RuData{

    App app;
    AccountDataGateway accountDataGateway;
    final String url = "http://127.0.0.1:8080/accounts/user/";
    final String loginFailed = "http://127.0.0.1:8080/accounts/login/";

    //User that exists in my database!
    UserModel userExisting = new UserModel("Mcfly","MartyJunior","marty@future.is","1234");

    //User with wrong password
    Login wrongPassword = new Login("MartyJunior","1233333");

    //Need to change before test
    UserModel newUser = new UserModel("Marty Mcfly","Bacsssssssk","marty@future.is","1234");

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Before
    public void setup() throws Exception
    {
        //Initialize the server.
        app = new App();
        System.out.println("App starting!");

        //Start the server.
        app.start();
        //Initialize accountDataGateway
        RuDataAccessFactory factory = null;
        try {
            factory = RuDataAccessFactory.getInstance("data.xml");
        } catch (RuException e) {
            e.printStackTrace();
        }
        accountDataGateway = (AccountDataGateway)factory.getDataAccess("AccountDataGateway");
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    /**
     * Test to see if the user is added successfully to the database
     * Should return the Id and token
     */
    @Test
    public void testAddUser()
    {
        String result = restTemplate.postForObject(url,newUser,String.class);
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = new JSONObject();
        try{
            jsonObj = (JSONObject) parser.parse(result);
        }
        catch(ParseException pe)
        {
            pe.printStackTrace();
        }
        int passwordReturned = (int) (long) jsonObj.get("id");
        String Token = jsonObj.get("token").toString();
        //Get the Id from the User
        int idFromDb = 0;
       try {
           idFromDb = accountDataGateway.getUserIdFromToken(Token);
       }
       catch(UnauthorizedException une)
       {
           une.printStackTrace();
       }
        assertEquals(passwordReturned ,idFromDb);
    }

    /**
     * Test to see what happens when trying to add a user that exists
     * Should return 409 Conflict
     */
    @Test
    public void testAddUserExists()
    {
        //Should be Conflict
        String result = "409 Conflict";
        try {
            result = restTemplate.postForObject(url, userExisting, String.class);
        }
        catch (HttpClientErrorException ex)
        {
            assertEquals(result,ex.getMessage());
        }
    }
    /**
     * Test to see what happens when user tries to log in with the wrong password
     * Should return 401 Unauthorized failed with the Explanation: "Incorrect password"
     */
    @Test
    public void testLoginFailed()
    {
        //Should be Unauthorized 401
        String explanation = "401 Unauthorized";
        String result = null;
        try {
            result = restTemplate.postForObject(loginFailed, wrongPassword, String.class);
        }
        catch (HttpClientErrorException ex)
        {
            assertEquals(explanation,ex.getMessage());
        }
    }

    @After
    public void shutDownServer()
    {
        //Shutdown server
        app.close();
    }
}
