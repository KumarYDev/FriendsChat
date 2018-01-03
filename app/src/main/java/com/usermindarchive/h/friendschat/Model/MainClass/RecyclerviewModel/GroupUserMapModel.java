package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

/**
 * Created by HERO on 1/1/2018.
 */

public class GroupUserMapModel {
    String UserID;

    public GroupUserMapModel() {
        super();
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {

        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    String UserName;

    public GroupUserMapModel(String userID, String userName) {
        UserID = userID;
        UserName = userName;
    }
}
