package data;

import data.RowMappers.ChannelRowMapper;
import data.RowMappers.VideoRowMapper;
import data.RowMappers.VideosInChannelRowMapper;
import exceptions.channelNotFoundException;
import exceptions.videoNotFoundException;
import is.ruframework.data.RuData;
import models.ChannelModel;
import models.VideoModel;
import models.VideosInChannelModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laufey on 01/11/2016.
 */
public class VideoData extends RuData implements VideoDataGateway {

    public int addVideo(VideoModel video) {
        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("videos")
                        .usingColumns("title","type","description","src","userName")
                        .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>(5);
        parameters.put("title", video.getTitle());
        parameters.put("type", video.getType());
        parameters.put("description", video.getDescription());
        parameters.put("src", video.getSrc());
        parameters.put("userName", video.getUserName());

        int returnKey = 0;

        returnKey = insertContent.executeAndReturnKey(parameters).intValue();

        return returnKey;
    }

    public List<VideoModel> getVideos()
    {
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());
        List<VideoModel> users = queryContent.query("select * from users", new VideoRowMapper());
        return users;
    }

    public int addChannel(ChannelModel channel) {
        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("channels")
                        .usingColumns("channelName")
                        .usingGeneratedKeyColumns("channelID");

        Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put("channelName", channel.getChannelName());

        int returnKey = 0;

        returnKey = insertContent.executeAndReturnKey(parameters).intValue();

        return returnKey;
    }

    public void addVideoToChannel(VideosInChannelModel videosInChannel) throws videoNotFoundException ,channelNotFoundException{
        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("VideosInChannel")
                        .usingColumns("videoID", "channelID")
                        .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("videoID", videosInChannel.getVideoID());
        parameters.put("channelID", videosInChannel.getChannelID());

        int returnKey = 0;

        if(doesVideoExist(videosInChannel.getVideoID())){
            if(doesChannelExist(videosInChannel.getChannelID())){
                if(!isVideoInChannel(videosInChannel.getVideoID(), videosInChannel.getChannelID()))
                {
                    insertContent.executeAndReturnKey(parameters).intValue();
                }
                else{
                    throw new videoNotFoundException("Video already in channel");
                }
            }
            else{
                throw new channelNotFoundException("Channel not found");
            }
        }
        else{
            throw new videoNotFoundException("Video not found in all videos");
        }
    }

    public boolean isVideoInChannel(int videoId,int channelId){

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<VideosInChannelModel> videoExistsInChannel = queryContent.query("select * from VideosInChannel where videoID ='" + videoId + "' AND channelID ='" + channelId + "'", new VideosInChannelRowMapper());

        if(videoExistsInChannel.size() == 0){
            return false;
        }
        return true;
    }


    public boolean doesChannelExist(int channelId){

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<ChannelModel> videoExistsInChannel = queryContent.query("select * from channels where channelID ='" + channelId + "'", new ChannelRowMapper());

        if(videoExistsInChannel.size() == 0){
            return false;
        }
        return true;
    }

    public boolean doesVideoExist(int videoId){

        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        List<VideoModel> videoExists = queryContent.query("select * from videos where id ='" + videoId + "'", new VideoRowMapper());

        if(videoExists.size() == 0){
            return false;
        }
        return true;
    }

}
