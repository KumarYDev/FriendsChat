package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

/**
 * Created by HERO on 1/3/2018.
 */

public class GroupInfoModel {
    String groupid;
    String groupname;

    public GroupInfoModel(String groupid, String groupname) {
        this.groupid = groupid;
        this.groupname = groupname;
    }

    public String getGroupid() {

        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
