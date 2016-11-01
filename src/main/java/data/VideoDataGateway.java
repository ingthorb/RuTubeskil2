package data;

import exceptions.channelNotFoundException;
import exceptions.videoNotFoundException;
import models.ChannelModel;
import models.VideoModel;
import models.VideosInChannelModel;

import java.util.List;

/**
 * Created by Laufey on 01/11/2016.
 */
public interface VideoDataGateway {

    /**
     *
     * @param video
     * @return
     */
    public int addVideo(VideoModel video);


    /**
     *
     * @return
     */
    public List<VideoModel> getVideos();

    /**
     *
     * @param channel
     * @return
     */
    public int addChannel(ChannelModel channel);

    /**
     *
     * @param videosInChannel
     * @return
     */
    public void addVideoToChannel(VideosInChannelModel videosInChannel) throws videoNotFoundException , channelNotFoundException;

}
