package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

/**
 * Created by HERO on 1/2/2018.
 */

public class GroupuCommMessageModel {
    String message,username,uid;

    public GroupuCommMessageModel() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public GroupuCommMessageModel(String message, String username, String uid) {
        this.message = message;
        this.username = username;
        this.uid = uid;
    }
}
