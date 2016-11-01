package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Laufey on 01/11/2016.
 */
public class VideosInChannelModel {

    public final static  String mediaType = "application/json";

    @NotNull
    @JsonProperty("id")
    int id;

    @NotNull
    @JsonProperty("videoID")
    int videoID;

    @NotNull
    @JsonProperty("channelID")
    int channelID;

    public VideosInChannelModel() {
    }


    public VideosInChannelModel(int id, int videoID, int channelID) {
        this.id = id;
        this.videoID = videoID;
        this.channelID = channelID;
    }

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
