package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

/**
 * Created by HERO on 12/23/2017.
 */

public class MessageModel {
    public MessageModel(String message, Long time, String type) {
        this.message = message;
        this.time = time;
        this.type=type;
    }

    public MessageModel() {
        super();
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    String message;
    Long time;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
