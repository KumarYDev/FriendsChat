package com.usermindarchive.h.friendschat.Model.MainClass.CreateUser;

import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.ModelInterfaces;
import com.usermindarchive.h.friendschat.Presenter.MainClass.CreateUserpresenter;
import com.usermindarchive.h.friendschat.View.MainClass.CreateUser;

import javax.inject.Inject;



public class CreateUserModel implements ModelInterfaces.CreateUser {

    Firebase firebase;
    CreateUserpresenter createUserpresenter;


    public CreateUserModel(CreateUser createUser, Firebase firebase, CreateUserpresenter createUserpresenter) {
        this.firebase=firebase;
        this.createUserpresenter=createUserpresenter;

    }

    @Override
    public boolean CreateUser(String email, String password) {
        return firebase.createUser(email,password,this);
    }

    @Override
    public void status(Boolean stat) {
        createUserpresenter.status(stat);

    }

    @Override
    public void processWait() {
        createUserpresenter.processWait();
    }

    @Override
    public void processStop() {
        createUserpresenter.processStop();

    }
}
