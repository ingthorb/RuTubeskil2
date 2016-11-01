package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Laufey on 01/11/2016.
 */
public class VideoModel {

    public final static  String mediaType = "application/json";

    @NotNull
    @JsonProperty("id")
    int id;

    @NotNull
    @JsonProperty("title")
    String title;

    @NotNull
    @JsonProperty("type")
    String type;

    @NotNull
    @JsonProperty("description")
    String description;

    @NotNull
    @JsonProperty("src")
    String src;

    @NotNull
    @JsonProperty("userName")
    String userName;

    public VideoModel() {
    }

    public VideoModel(int id, String title, String type, String description, String src, String userName) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.src = src;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
