package com.usermindarchive.h.friendschat.Model.Dagger;

import com.usermindarchive.h.friendschat.MainActivity;
import com.usermindarchive.h.friendschat.Model.MainClass.CreateUser.CreateUserModel;
import com.usermindarchive.h.friendschat.Model.MainClass.Login.LoginAuth;
import com.usermindarchive.h.friendschat.Model.ModelInterfaces;

import javax.inject.Singleton;



@Singleton
@dagger.Component(modules = {FirebaseDependency.class})
public interface Component {
    void inject(MainActivity mainActivity);
//    void inject(LoginAuth loginAuth);
//    void inject(CreateUserModel createUserModel);
}
