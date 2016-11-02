
import data.AccountDataGateway;
import data.RowMappers.FavoritVideosRowMapper;
import data.UserDataGateway;
import data.VideoDataGateway;
import exceptions.UnauthorizedException;
import is.ruframework.data.RuDataAccessFactory;
import is.ruframework.domain.RuException;
import models.FavoriteVideosModel;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import server.App;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ingthor on 2.11.2016.
 */
public class UserServiceIT {
    App app;
    VideoDataGateway videoDataGateway;
    UserDataGateway userDataGateway;
    AccountDataGateway accountDataGateway;
    String Token = "[B@127fa917";
    int userId;
    final String url = "http://127.0.0.1:8080/userservice";
    final String favURL = "http://127.0.0.1:8080/userservice/favoriteVideos";

    HttpHeaders headers = new HttpHeaders();
    RestTemplate restTemplate = new RestTemplate();
    @Before
    public void setup() throws Exception
    {
        //Initialize the server.
        app = new App();
        System.out.println("App starting!");

        //Start the server.
        app.start();
        //Authentication hardcoded, we can get a user and then get his token later
        RuDataAccessFactory factory = null;
        try {
            factory = RuDataAccessFactory.getInstance("data.xml");
        } catch (RuException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void checkProfile()
    {
        headers.set("Authorization",Token);
        HttpEntity<String> ent2 = new HttpEntity<String>(headers);

        HttpEntity<String> result2 = restTemplate.exchange(url, HttpMethod.GET, ent2 ,String.class, "");
        System.out.println(result2.getBody());
        String profile = "{\"Favorite videos\":[{\"id\":8,\"title\":\"MartyJunior\",\"type\":\"funny\",\"description\":\"Sfs\",\"src\":\"youtube.com\",\"userId\":91}],\"User information\":{\"id\":101,\"fullName\":\"Marty Mcfly\",\"userName\":\"b2221l2abw2\",\"email\":\"marty@future.is\",\"password\":\"*****\"},\"Close friends\":[]}";

        boolean boolprofile = result2.getBody().equals(profile);
        assertEquals(profile,result2.getBody());
        System.out.println(boolprofile);


    }

    @Test
    public void favvideos()
    {
        int userID = 9;
        int videoID = 8;
        String Token2 = "[B@5db60c16";
        headers.set("Authorization",Token2);
        String profile = "{\"Favorite videos\":[{\"id\":8,\"title\":\"MartyJunior\",\"type\":\"funny\",\"description\":\"Sfs\",\"src\":\"youtube.com\",\"userId\":91}],\"User information\":{\"id\":101,\"fullName\":\"Marty Mcfly\",\"userName\":\"b2221l2abw2\",\"email\":\"marty@future.is\",\"password\":\"*****\"},\"Close friends\":[]}";
       FavoriteVideosModel favorite = new FavoriteVideosModel(userID,videoID);
        HttpEntity ent1 = new HttpEntity(favorite,headers);
        String result = restTemplate.postForObject(favURL, ent1, String.class);

    }
    @After
    public void shutDownServer()
    {
        //Shutdown server
        app.close();
    }

}
