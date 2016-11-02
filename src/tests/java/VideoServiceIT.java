
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import data.AccountDataGateway;
import is.ruframework.data.RuDataAccessFactory;
import is.ruframework.domain.RuException;
import models.ChannelModel;
import models.UserModel;
import models.VideoModel;
import models.VideosInChannelModel;
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

import static org.junit.Assert.assertEquals;
/**
 * Created by Ingthor on 1.11.2016.
 */
public class VideoServiceIT {
    App app;
    AccountDataGateway accountDataGateway;
    final String url = "http://127.0.0.1:8080/videos";
    final String channelVideoUrl = "http://127.0.0.1:8080/videos/channel/video";
    final String channelUrl = "http://127.0.0.1:8080/videos/channel";
    int channelnewID = 0;
    int videoID = 0;

    //User that exists in my database!
    String Token = "[B@71d3af07";
    UserModel userExisting = new UserModel("Mcfly","MartyJunior","marty@future.is","1234");
    VideoModel video = new VideoModel("Funny cat","Funny","Cat falls over","youtube.com",1);
    ChannelModel channel = new ChannelModel("TestChannel");
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
        //Authentication hardcoded, we can get a user and then get his token later
        RuDataAccessFactory factory = null;
        try {
            factory = RuDataAccessFactory.getInstance("data.xml");
        } catch (RuException e) {
            e.printStackTrace();
        }
        accountDataGateway = (AccountDataGateway)factory.getDataAccess("AccountDataGateway");

        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
       // headers.set("Authorization","[B@71d3af07");
        headers.set("Authorization",Token);
        String result = null;
        try{
            result = restTemplate.postForObject(channelUrl ,channel, String.class);
        }
        catch (HttpClientErrorException hp)
        {
            System.out.println(hp.getMessage());
        }
        channelnewID = Integer.parseInt(result);

        //Making sure that the database isn't empty so we can safely delete a video that exists
        String result2 = restTemplate.postForObject(url,video, String.class);
        //Need to save the id that result gives us so we can add to channel
        videoID = Integer.parseInt(result2);
    }

    /**
     * Test to see if adding a video to a fails without authorization
     * Should return
     */
    @Test
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
    }

    /**
     * Test to see if adding a video is a success and is listed in all videos
     * Should return
     */
    @Test
    public void addAVideo()
    {
        String result2 = restTemplate.postForObject(url,video, String.class);

        //Get out of database some token
        //Get the videos with a get request
        /*
        Get the user with the ID 1
         */
        headers.add("Authorization","[B@5db60c16");
        //Videos get all the videos in the database
        String videos = restTemplate.getForObject(url, String.class);
        JsonParser parser = new JsonParser();
        JsonArray o = parser.parse(videos).getAsJsonArray();
        boolean videoAdded = false;
        for (int i = 0; i < o.size(); ++i) {
            JsonElement obj =  o.get(i);
            String os = obj.toString();
            JSONObject sfs = parser(os);
            int listID = (int) (long) sfs.get("id");
            if(listID == videoID)
            {
                videoAdded = true;
            }
        }

        assertEquals(true,videoAdded);

    }
    @Test
   public void addAVideoInChannel()
    {
        //Hardcoded
        // headers.add("Authorization","[B@5db60c16");

        VideosInChannelModel videochannel = new VideosInChannelModel(videoID,channelnewID);
        //Post video into channel
        System.out.println("VideoID: " + videoID + "ChannelID: " + channelnewID);
        String result = null;
        try
        {
            result = restTemplate.postForObject(channelVideoUrl, videochannel, String.class);
        }
        catch(HttpClientErrorException hp)
        {
            System.out.println("SFDSf");
            hp.printStackTrace();
        }

        final String checkChannelURL = "http://127.0.0.1:8080/videos/channel/video/" + channelnewID;
        String result2 = null;
        try
        {
            result2 = restTemplate.getForObject(checkChannelURL, String.class);
        }
        catch (HttpClientErrorException hs)
        {
            hs.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonArray o = parser.parse(result2).getAsJsonArray();
        boolean videoAddedToChannel = false;
        for (int i = 0; i < o.size(); ++i) {
            JsonElement obj =  o.get(i);
            String os = obj.toString();
            JSONObject sfs = parser(os);
            int listID = (int) (long) sfs.get("id");
            if(listID == videoID)
            {
                videoAddedToChannel = true;
            }
            System.out.println(listID);
        }

        assertEquals(true,videoAddedToChannel);
    }

    /**
     * Removes a video from a channel and lists
     */
    @Test
    public void removeVideo()
    {
        //Hardcoded
        // headers.add("Authorization","[B@5db60c16");
        final String deleteVideoUrl = "http://127.0.0.1:8080/videos/" + videoID;
        System.out.println(deleteVideoUrl);


        //Delete the video
        restTemplate.delete(deleteVideoUrl,String.class);
        //Get all the videos in the channel

        //Returns a list of videos<VideoModel>
        String videos = null;
        try {
            videos = restTemplate.getForObject(url, String.class);
        }
        catch (HttpClientErrorException hs)
        {
            hs.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonArray o = parser.parse(videos).getAsJsonArray();
        boolean videoInList = false;
        for (int i = 0; i < o.size(); ++i) {
            JsonElement obj =  o.get(i);
            String os = obj.toString();
            JSONObject sfs = parser(os);
            int listID = (int) (long) sfs.get("id");
            if(listID == videoID)
            {
                videoInList = true;
            }
        }
        assertEquals(false,videoInList);
    }


    @After
    public void shutDownServer()
    {
        //Shutdown server
        app.close();
    }

    public JSONObject parser(String result)
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
