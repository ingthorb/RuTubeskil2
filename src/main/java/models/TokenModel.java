package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Laufey on 31/10/2016.
 */
public class TokenModel {

    public final static  String mediaType = "application/json";

    @NotNull
    @JsonProperty("userName")
    int userID;

    @NotNull
    @JsonProperty("token")
    String token;

    public TokenModel() {
    }

    public TokenModel(int userID, String token) {
        this.userID = userID;
        this.token = token;
    }

    public static String getMediaType() {
        return mediaType;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
