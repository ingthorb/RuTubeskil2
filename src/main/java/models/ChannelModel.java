package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Laufey on 01/11/2016.
 */
public class ChannelModel {

    public final static  String mediaType = "application/json";

    @NotNull
    @JsonProperty("channelID")
    int channelID;

    @NotNull
    @JsonProperty("channelName")
    String channelName;

    public ChannelModel() {
    }

    public ChannelModel(int channelID, String channelName) {
        this.channelID = channelID;
        this.channelName = channelName;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
