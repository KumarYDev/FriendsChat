package com.usermindarchive.h.friendschat.Model.MainClass.Login;

import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;

import javax.inject.Inject;



public class LoginAuth {

    Firebase firebase;
    LoginModel loginModel;

    public LoginAuth(Firebase firebase, LoginModel loginModel) {

        this.loginModel=loginModel;
        this.firebase=firebase;
    }

    public void Auth(String id, String password){

         firebase.signUser(id,password,this);

    }

    public void Status(boolean stat){
        loginModel.status(stat);
    }
}
