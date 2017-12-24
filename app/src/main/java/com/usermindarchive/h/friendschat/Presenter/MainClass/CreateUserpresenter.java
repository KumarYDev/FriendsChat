package com.usermindarchive.h.friendschat.Presenter.MainClass;

import com.usermindarchive.h.friendschat.Model.MainClass.CreateUser.CreateUserModel;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.ModelInterfaces;
import com.usermindarchive.h.friendschat.Presenter.Interface.PresenterInterfaces;
import com.usermindarchive.h.friendschat.View.Interface.ViewInterfaces;
import com.usermindarchive.h.friendschat.View.MainClass.CreateUser;



public class CreateUserpresenter implements PresenterInterfaces.CreateUser {

    ModelInterfaces.CreateUser createUserModel;
    ViewInterfaces.CreateUserInterface createUserView;


    public CreateUserpresenter(CreateUser createUser, Firebase firebase) {
        this.createUserModel=new CreateUserModel(createUser,firebase,this);
        createUserView=createUser;
    }

    @Override
    public void creatUser(String id, String password) {
        createUserModel.CreateUser(id,password);

        }

    @Override
    public void status(Boolean stat) {
        if(stat){
            createUserView.SuccessUserCreated();
        }else{
            createUserView.FailedUserCreated();
        }
    }

    @Override
    public void processWait() {
        createUserView.processWait();
    }

    @Override
    public void processStop() {
        createUserView.processStop();

    }


}
