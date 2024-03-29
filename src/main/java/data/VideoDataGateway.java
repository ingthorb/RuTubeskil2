package data;

import exceptions.channelNotFoundException;
import exceptions.videoNotFoundException;
import models.ChannelModel;
import models.VideoModel;
import models.VideosInChannelModel;

import java.util.List;

public interface VideoDataGateway {

    /**
     *
     * @param video
     * @return
     */
    public int addVideo(VideoModel video, int userId);

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

    /**
     *
     * @param chennelId
     * @return
     */
    public List<VideoModel> getVideosInChannel(int chennelId);

    /**
     *
     * @param videoId
     * @throws videoNotFoundException
     * @throws channelNotFoundException
     */
    public void RemoveVideo(int videoId) throws videoNotFoundException, channelNotFoundException;
}
