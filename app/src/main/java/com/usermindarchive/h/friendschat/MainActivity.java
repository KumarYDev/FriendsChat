package com.usermindarchive.h.friendschat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.usermindarchive.h.friendschat.Model.Dagger.Component;
import com.usermindarchive.h.friendschat.Model.Dagger.DaggerComponent;
import com.usermindarchive.h.friendschat.Model.Dagger.FirebaseDependency;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.View.MainClass.GroupChat;
import com.usermindarchive.h.friendschat.View.MainClass.Login;
import com.usermindarchive.h.friendschat.View.MainClass.Profile;
import com.usermindarchive.h.friendschat.View.MainClass.UserWelcome;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main)
    FrameLayout main;
    Login loginpage;
    Firebase firebase;
    Component firebaseComponent;
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        firebase=new Firebase(getApplicationContext(),this);
        mainpage();

    }

    @Override
    protected void onResume() {
        super.onResume();

        firebase.userStatus("ONLINE");

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.userStatus("OFFLINE");
    }

    public void mainpage(){
        if(firebase.userexits()) {
            loginpage();
        }
        else
            userpage();
    }

    public  void loginpage(){
        loginpage=new Login();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main, loginpage);

        transaction.commit();

    }

    public void userpage(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main, new UserWelcome());
//        transaction.addToBackStack("tag");
        transaction.commit();
    }

//not used
    public void Dagger(){

        firebaseComponent= DaggerComponent.builder()
                .firebaseDependency(new FirebaseDependency(this)).build();
        firebaseComponent.inject(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.signout).setVisible(false);
        menu.findItem(R.id.profile).setVisible(false);
        menu.findItem(R.id.groupchat).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.profile:
                // Do Activity menu item stuff here
                userinfo();
                return true;
            case R.id.signout:
                alter();
                return true;
            case R.id.groupchat:
//                groupChat();
                Toast.makeText(MainActivity.this,"Feature is under working",Toast.LENGTH_LONG).show();
                return true;
            default:
                break;
        }

        return false;
    }

    public void userinfo(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main, new Profile());
        transaction.addToBackStack("tag");
        transaction.commit();
    }
    public void groupChat(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main, new GroupChat());
        transaction.addToBackStack("group");
        transaction.commit();
    }

    public void alter(){
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Log Out")
                .setMessage("Are you sure you want to LOG OUT ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        firebase.userLogout();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


}
