import com.google.gson.Gson;
import models.ChannelModel;
import models.VideoModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import server.App;
import service.VideoService;

import java.nio.channels.Channel;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ingthor on 1.11.2016.
 */
public class VideoServiceIT {
    App app;
    final String url = "http://127.0.0.1:8080/videos";
    final String channelVideoUrl = "http://127.0.0.1:8080/videos/channel/video";
    final String channelUrl = "http://127.0.0.1:8080/videos/channel";
    VideoModel video = new VideoModel("Funny cat","Funny","Cat falls over","youtube.com",1);
    ChannelModel channel = new ChannelModel("TestChannel");
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    String Auth = "[B@6e3be21f";

    @Before
    public void setup() throws Exception
    {
        //Initialize the server.
        app = new App();
        System.out.println("App starting!");

        //Start the server.
        app.start();
        //Authentication hardcoded, we can get a user and then get his token later
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","[B@6e3be21f");

        System.out.println("Channel url: " + channelUrl);
        System.out.println("Channel : " + channel);
        //Create channel and post
        String result = null;
        try{
             result = restTemplate.postForObject(channelUrl ,channel, String.class);
        }
        catch (HttpClientErrorException hp)
        {
            System.out.println("THIS IS A ERROR MESS");
            System.out.println(hp.getMessage());
        }
        //String result = restTemplate.postForObject(channelUrl,channel, String.class);
        //JSONObject results = (JSONObject) parser(result);
        System.out.println("****************TESTING***********");
        System.out.println(headers);
    }

    /**
     * Test to see if adding a video to a fails without authorization
     * Should return
     */
    /*@Test
    public void addAVideoFailed()
    {
        //Should be Unauthorized 401
        String explanation = "401 Unauthorized";
        String result = null;
        try{
            result = restTemplate.postForObject(url,video, String.class);
        }
        catch (HttpClientErrorException htp)
        {
            assertEquals(explanation,htp.getMessage());
        }
    }*/

    /**
     * Test to see if adding a video is a success and is listed in all videos
     * Should return
     */
    @Test
    public void addAVideo()
    {
        //Get out of database some token
        //Get the videos with a get request
        /*
        Get the user with the ID 1
         */
       // headers.add("Authorization","USERID1");

        System.out.println(headers.get("Content-Type"));
        //result posts video into the database
        String result = restTemplate.postForObject(channelUrl,video, String.class);
        //Videos get all the videos in the database
        String videos = restTemplate.getForObject(url, String.class);


        JSONObject videoJSON = (JSONObject) parser(videos);
        JSONObject results = (JSONObject) parser(result);

         boolean videoAdded = videoJSON.get("id").equals(results.get("id"));
        assertEquals(true,videoAdded);

    }
    @Test
    public void addAVideoInChannel()
    {
        //Get out of database some token
        //Get the videos with a get request
        /*
        Get the user with the ID 1
         */
        // headers.add("Authorization","USERID1");
      /*  System.out.println("");
        //result posts video into the database
        String result = restTemplate.postForObject(channelUrl,video, String.class);
        //Get all the videos in the channel
        //Video id og channelID til að posta í channel
        //Bara hægt að pósta í video


        JSONObject results = (JSONObject) parser(result);
        //boolean videoAdded = videoJSON.get("id").equals(results.get("id"));
        //assertEquals(true,videoAdded);
*/
    }
    @After
    public void shutDownServer()
    {
        //Shutdown server
        app.close();
    }

    public Object parser(String result)
    {
        JSONParser parser = new JSONParser();
        JSONObject results = new JSONObject();

        try{
            results = (JSONObject) parser.parse(result);
        }
        catch(ParseException pe)
        {
            pe.printStackTrace();
        }
        return results;
    }
}
