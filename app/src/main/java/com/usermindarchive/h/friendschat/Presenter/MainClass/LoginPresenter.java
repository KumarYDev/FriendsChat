package com.usermindarchive.h.friendschat.Presenter.MainClass;

import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.Login.LoginModel;
import com.usermindarchive.h.friendschat.Model.ModelInterfaces;
import com.usermindarchive.h.friendschat.Presenter.Interface.PresenterInterfaces;
import com.usermindarchive.h.friendschat.View.Interface.ViewInterfaces;
import com.usermindarchive.h.friendschat.View.MainClass.Login;



public class LoginPresenter implements PresenterInterfaces.Login {

    ViewInterfaces.LoginInterface loginView;
    ModelInterfaces.Login loginModel;



    public LoginPresenter(Login login, Firebase firebase) {
        loginView=login;
        loginModel=new LoginModel(login,firebase,this);

    }

    @Override
    public void authLoginId(String id, String password) {
        loginModel.loginDetailsIN(id,password);
        loginModel.loginDetailsAuth();



    }

    @Override
    public void status(Boolean stat) {
        if(stat){

            loginView.loginSuccess();
        }else
            loginView.loginFailure();
    }
}
