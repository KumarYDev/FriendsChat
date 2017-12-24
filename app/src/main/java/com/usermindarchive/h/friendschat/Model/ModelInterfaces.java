package com.usermindarchive.h.friendschat.Model;



public interface ModelInterfaces {

    public interface Login{
        public void loginDetailsIN(String id,String password);
        public void loginDetailsAuth();
        public void status(Boolean stat);
    }

    public interface CreateUser{
        boolean CreateUser(String email,String password);
        public void status(Boolean stat);
        void processWait();
        void processStop();
    }

}
