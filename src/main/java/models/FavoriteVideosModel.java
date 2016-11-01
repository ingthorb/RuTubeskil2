package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Laufey on 01/11/2016.
 */
public class FavoriteVideosModel {

    public final static  String mediaType = "application/json";

    @NotNull
    @JsonProperty("id")
    int id;

    @NotNull
    @JsonProperty("userID")
    int userID;

    @NotNull
    @JsonProperty("videoID")
    int videoID;

    public FavoriteVideosModel() {
    }

    public FavoriteVideosModel(int id, int userID, int videoID) {
        this.id = id;
        this.userID = userID;
        this.videoID = videoID;
    }

    public static String getMediaType() {
        return mediaType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }
}
