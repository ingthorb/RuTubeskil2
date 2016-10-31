package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Laufey on 31/10/2016.
 */
public class ChangePasswordModel {

    public final static  String mediaType = "application/json";

    @NotNull
    @JsonProperty("oldPassword")
    String oldPassword;

    @NotNull
    @JsonProperty("newPassword")
    String newPassword;

    public ChangePasswordModel() {
    }

    public ChangePasswordModel(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public static String getMediaType() {
        return mediaType;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
