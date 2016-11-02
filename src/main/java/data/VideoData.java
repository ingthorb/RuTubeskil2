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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laufey on 01/11/2016.
 */
public class VideoData extends RuData implements VideoDataGateway {

    public int addVideo(VideoModel video, int userId) {
        SimpleJdbcInsert insertContent =
                new SimpleJdbcInsert(getDataSource())
                        .withTableName("videos")
                        .usingColumns("title","type","description","src","userId")
                        .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>(5);
        parameters.put("title", video.getTitle());
        parameters.put("type", video.getType());
        parameters.put("description", video.getDescription());
        parameters.put("src", video.getSrc());
        parameters.put("userId", userId);

        int returnKey = 0;

        returnKey = insertContent.executeAndReturnKey(parameters).intValue();

        return returnKey;
    }

    public List<VideoModel> getVideos()
    {
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());
        List<VideoModel> videos = queryContent.query("select * from videos", new VideoRowMapper());
        return videos;
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

    public List<VideoModel> getVideosInChannel(int channelId)
    {
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());
        List<VideosInChannelModel> VideosInChannel = queryContent.query("select * from VideosInChannel where channelID ='" + channelId + "'", new VideosInChannelRowMapper());

        List<VideoModel> videosInChannelWithInfo  =new ArrayList<VideoModel>();

        for(int i = 0; i< VideosInChannel.size(); i++){
            List<VideoModel> video = queryContent.query("select * from videos where id ='" + VideosInChannel.get(0).getVideoID() + "'", new VideoRowMapper());
            videosInChannelWithInfo.add(video.get(0));
        }
        return videosInChannelWithInfo;
    }

    public void RemoveVideo(int videoId) throws videoNotFoundException, channelNotFoundException{
        JdbcTemplate queryContent = new JdbcTemplate(getDataSource());

        queryContent.execute("DELETE FROM favoritVideos WHERE videoID='" + videoId + "'");

        List<VideosInChannelModel> VideosInChannel = queryContent.query("select * from VideosInChannel", new VideosInChannelRowMapper());

        for(int i = 0; i< VideosInChannel.size(); i++) {
            if(VideosInChannel.get(i).getVideoID() == videoId)
            {
                queryContent.execute("DELETE FROM VideosInChannel WHERE videoID='" + VideosInChannel.get(i).getVideoID() + "'");
            }
        }

        if(doesVideoExist(videoId)){
            queryContent.execute("DELETE FROM videos WHERE id='" + videoId + "'");
        }
        else{
            throw new videoNotFoundException("Video not found videos");
        }
    }

}
