package com.usermindarchive.h.friendschat.View.Interface;



public interface ViewInterfaces {
    public interface LoginInterface {
        public void loginSuccess();
        public void loginFailure();

    }
    public interface CreateUserInterface{
        void SuccessUserCreated();
        void FailedUserCreated();
        void processWait();
        void processStop();
    }
}
