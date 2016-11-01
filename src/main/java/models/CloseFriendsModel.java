package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Laufey on 01/11/2016.
 */
public class CloseFriendsModel {

    public final static  String mediaType = "application/json";

    @NotNull
    @JsonProperty("id")
    int id;

    @NotNull
    @JsonProperty("userID")
    int userID;

    @NotNull
    @JsonProperty("friendID")
    int friendID;

    public CloseFriendsModel() {
    }

    public CloseFriendsModel(int id, int userID, int friendID) {
        this.id = id;
        this.userID = userID;
        this.friendID = friendID;
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

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }
}
