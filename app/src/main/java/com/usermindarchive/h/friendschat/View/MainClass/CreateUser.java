package com.usermindarchive.h.friendschat.View.MainClass;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Presenter.Interface.PresenterInterfaces;
import com.usermindarchive.h.friendschat.Presenter.MainClass.CreateUserpresenter;
import com.usermindarchive.h.friendschat.R;
import com.usermindarchive.h.friendschat.View.Interface.ViewInterfaces;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class CreateUser extends Fragment implements ViewInterfaces.CreateUserInterface {
    View v;
    @BindView(R.id.crtemail)
    EditText email;
    @BindView(R.id.crtpassword)
    EditText password;
    @BindView(R.id.crtrepassword)
    EditText repassword;
    @BindView(R.id.crtuser)
    Button crtuser;
    Context context;

//    @BindView(R.id.progressBar)
    ProgressBar status;

    PresenterInterfaces.CreateUser createUser;

    Firebase firebase;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        firebase=new Firebase(context);
        this.context=context;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.createuser, container, false);
        status=getActivity().findViewById(R.id.progressBar);
        ButterKnife.bind(this,v);
        createUser=new CreateUserpresenter(this,firebase);
//        firebase=new Bundle().getParcelable("fire");
        emailError();

        return v;
    }

    public void emailError(){
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                sanckbar(intent.getStringExtra("email"));


            }
        };

        IntentFilter action=new IntentFilter("action.Email");

        context.registerReceiver(broadcastReceiver,action);
    }

    @OnClick(R.id.crtuser)
    public void createuser(){
        if(fill()){
//            Functions if everything is entered correctly
            createUser.creatUser(email.getText().toString(),password.getText().toString());
        }
    }

    //    Checking the Edittext is filled or empty
    public boolean check(EditText view){
        String viewdata = view.getText().toString();

        if(view.getId()==password.getId()) {
            if(view.getText().length()<6)

            view.setError("Password  should be greater than 6 Characters");
        }



        if(TextUtils.isEmpty(viewdata)) {

            if(view.getId()==email.getId())
                view.setError("Enter the Email");
            if(view.getId()==password.getId())
                view.setError("Type the Password");
            if(view.getId()==repassword.getId())
                view.setError("Re-type the Password");
            return false;
        }
        return true;
    }

    public boolean checkpassword(){

        if(password.getText().toString().equals(repassword.getText().toString())&&password.getText().length()<=6) {

            return true;
        }
        else{
            password.setError("Re-type the Password");
            repassword.setError("Re-type the Password");

//            Toast.makeText(getActivity(),"Password is not matching",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    //    Checking individual Editiexts
    public boolean fill(){
        check(email);
        check(password);
        check(repassword);


        if(check(email)
                &&check(password)
                &&check(repassword)
            &&
        checkpassword())
            return true;
        else
            return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,v).unbind();
        context.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void SuccessUserCreated() {
        alter();


        Log.e(getActivity().toString(),"User Creation Success"+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

    public void sanckbar(String s){
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }
        builder.setTitle("Error")
                .setMessage("Error with Email \n "+s);




        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        })
                .setIcon(android.R.drawable.stat_notify_error)
                .show();

    }

    @Override
    public void FailedUserCreated() {

//        Toast.makeText(getActivity(),"Error\nUser Creation Failed",Toast.LENGTH_LONG).show();
        Log.e(getActivity().toString(),"User Creation Failed"+TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
    }

    @Override
    public void processWait() {
//        status.setVisibility(View.VISIBLE);

    }

    @Override
    public void processStop() {
//        status.setVisibility(View.GONE);
    }

    public void userpage(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main, new UserWelcome());
//        transaction.addToBackStack("tag");
        transaction.commit();
    }

    public void alter(){
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }
        builder.setTitle("Username")
                .setMessage("Select a Username \n(OR)\n App will Assign a Username");
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);



               builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        firebase.UserName(input.getText().toString());
                        userpage();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        String s="";
                        firebase.UserName(s);
                        userpage();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}
