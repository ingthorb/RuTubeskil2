import TestModels.Login;
import data.AccountData;
import is.ruframework.data.RuData;
import models.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import server.App;
/**
 * Created by Ingthor on 1.11.2016.
 */
public class AccountServiceIT extends RuData{
    App app;
    AccountData acc = new AccountData();
    final String url = "http://127.0.0.1:8080/accounts/user/";
    final String loginFailed = "http://127.0.0.1:8080/accounts/login/";
    //User that exists in my database!
    UserModel userExisting = new UserModel("Mcfly","MartyJunior","marty@future.is","1234");
    //User with wrong password
    //Created a Special Model to login
    Login wrongPassword = new Login("MartyJunior","1233333");
    UserModel newUser = new UserModel("Marty Mcfly","111423111611211","marty@future.is","1234");
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

        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    //Send in new user
    /**
     * Test to see if the user is added successfully to the database
     * Should return the Id and token
     */
   /* @Test
    public void testAddUser()
    {
        //Works sending and receiving a user
        //Request er body,seinasta er response
        String result = restTemplate.postForObject(url,newUser,String.class);
        System.out.println(result);
        JSONParser parser = new JSONParser();

        JSONObject jsonObj = new JSONObject();
        try{
            jsonObj = (JSONObject) parser.parse(result);
        }
        catch(ParseException pe)
        {
            pe.printStackTrace();
        }
        Integer output =(int) (long) jsonObj.get("id");
        //Get the Id from the User
        System.out.println("BEFORE query");
       String query = "SELECT id FROM ru.users WHERE userName = " + newUser.getUserName();
        ResultSet rs =
       // UserModel newestUser = acc.checkIfUserExists(newUser);
        System.out.println(userExists.size());
       // int ID = newestUser.getId();
        //System.out.print(ID);
        //assertEquals(ID ,output);
    }
*/
    /**
     * Test to see what happens when trying to add a user that exists
     * Should return 412 Precondition failed
     */
    @Test
    public void testAddUserExists()
    {
        //Should be Not modified
        String result = "412 Precondition Failed";
        try {
            result = restTemplate.postForObject(url, userExisting, String.class);
        }
        catch (HttpClientErrorException ex)
        {
            System.out.println(ex.getMessage());
            assertEquals(result,ex.getMessage());
        }
    }
    /**
     * Test to see what happens when user tries to log in with the wrong password
     * Should return 412 Precondition failed with the Explanation: "Incorrect password"
     */
    @Test
    public void testLoginFailed()
    {
        //Should be Unauthorized 401
        String explanation = "412 Precondition Failed";
        String result = null;
        try {
            result = restTemplate.postForObject(loginFailed, wrongPassword, String.class);
        }
        catch (HttpClientErrorException ex)
        {
            System.out.println(ex.getMessage());
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
