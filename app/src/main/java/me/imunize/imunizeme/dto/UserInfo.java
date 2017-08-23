package me.imunize.imunizeme.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sr. Décio Montanhani on 20/08/2017.
 */

public class UserInfo {
    private int id;
    private String login;
    @SerializedName("profile_id")
    private int profileId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
