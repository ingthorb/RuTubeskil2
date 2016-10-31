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
    String userName;

    @NotNull
    @JsonProperty("token")
    String token;

    public TokenModel() {
    }

    public TokenModel(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public static String getMediaType() {
        return mediaType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
