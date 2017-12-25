package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.usermindarchive.h.friendschat.R;

/**
 * Created by HERO on 12/22/2017.
 */

public class UserModel {
    public UserModel() {
        super();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }







    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

   private String Username;

    public UserModel(String username, String status) {
        Username = username;
        this.status = status;
    }

    private   String status;








}
