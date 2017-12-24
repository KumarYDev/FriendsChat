package com.usermindarchive.h.friendschat.View.MainClass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Presenter.Interface.PresenterInterfaces;
import com.usermindarchive.h.friendschat.Presenter.MainClass.LoginPresenter;
import com.usermindarchive.h.friendschat.R;
import com.usermindarchive.h.friendschat.View.Interface.ViewInterfaces;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class Login extends Fragment implements ViewInterfaces.LoginInterface {
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.login) Button login;
    @BindView(R.id.create) Button createuser;

    View v;
    PresenterInterfaces.Login loginPresenter;
    CreateUser createUser;
    Firebase firebase;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        firebase=new Firebase(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v =inflater.inflate(R.layout.login, container, false);
        ButterKnife.bind(this,v);
//        firebase=new Firebase(getContext());
//        firebase=new Bundle().getParcelable("fire");
        loginPresenter=new LoginPresenter(this,firebase);


        return v;
    }

    @OnClick(R.id.login)
    public void login(){
        if(fill()){
//            requeried operations if email and password are filled
            loginPresenter.authLoginId(email.getText().toString(),password.getText().toString());
        }

    }

    @OnClick(R.id.create)
    public void Createuser(){

//        Loading the UI with CreateUser Page
        createUser=new CreateUser();

//        Bundle fire=new Bundle();
//        fire.putParcelable("fire", (Parcelable) firebase);
//        createUser.setArguments(fire);
//
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main, createUser);
        transaction.addToBackStack("tag");
        transaction.commit();

    }


    //    Checking the Edittext is filled or empty
    public boolean check(EditText view){
        String viewdata = view.getText().toString();
        if(TextUtils.isEmpty(viewdata)) {

            if(view.getId()==email.getId())
                view.setError("Enter the Email");
            if(view.getId()==password.getId())
                view.setError("Enter the Password");
            return false;
        }
        return true;
    }
//    Checking individual Editiexts
    public boolean fill(){
        check(email);
        check(password);

        if(check(email)&&
        check(password)
        )
        return true;
        else
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,v).unbind();
    }

    @Override
    public void loginSuccess() {

        userpage();

        Log.e(getActivity().toString(),"Login Success");
    }

    @Override
    public void loginFailure() {
        Log.e(getActivity().toString(),"Login Failure");

    }

    public void userpage(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main, new UserWelcome());
//        transaction.addToBackStack("tag");
        transaction.commit();
    }
}
