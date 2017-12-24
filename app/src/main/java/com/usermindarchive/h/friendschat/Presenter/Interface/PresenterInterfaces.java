package com.usermindarchive.h.friendschat.Presenter.Interface;



public interface PresenterInterfaces {

//    Interface for Login and presenter

    public interface Login{
        public void authLoginId(String id, String password);
        public void status(Boolean stat);
    }
    public interface CreateUser{
        public void creatUser(String id, String password);
        public void status(Boolean stat);

//        did not used
        void processWait();
        void processStop();

    }
}
