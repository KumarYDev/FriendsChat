package com.usermindarchive.h.friendschat.Model.MainClass.Login;

import android.content.Context;

import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.ModelInterfaces;
import com.usermindarchive.h.friendschat.Presenter.MainClass.LoginPresenter;
import com.usermindarchive.h.friendschat.View.MainClass.Login;



public class LoginModel implements ModelInterfaces.Login {
    Context context;
    private String id,password;
    LoginAuth loginAuth;
     LoginPresenter loginPresenter;

    public LoginModel(Login login, Firebase firebase, LoginPresenter loginPresenter) {
//        this.context=login;
        loginAuth=new LoginAuth(firebase,this);
        this.loginPresenter=loginPresenter;
    }

    @Override
    public void loginDetailsIN(String id, String password) {
        this.id=id;
        this.password=password;
    }

    @Override
    public void loginDetailsAuth() {
         loginAuth.Auth(id,password);
    }

    @Override
    public void status(Boolean stat) {
        loginPresenter.status(stat);
    }
}
