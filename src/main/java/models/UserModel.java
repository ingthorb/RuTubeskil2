package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Laufey on 31/10/2016.
 */
public class UserModel {

    public final static  String mediaType = "application/json";
    @NotNull
    @JsonProperty("id")
    public int id;

    @NotNull
    @JsonProperty("fullName")
    public String fullName;

    @NotNull
    @JsonProperty("userName")
    public String userName;

    @NotNull
    @JsonProperty("email")
    public String email;

    @NotNull
    @JsonProperty("password")
    public String password;

    public UserModel() {
    }

    public UserModel(int id, String fullName, String userName, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public UserModel(String fullName, String userName, String email, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
