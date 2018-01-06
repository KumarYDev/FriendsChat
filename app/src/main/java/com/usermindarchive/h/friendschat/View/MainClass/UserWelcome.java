package com.usermindarchive.h.friendschat.View.MainClass;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.*;
import com.usermindarchive.h.friendschat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HERO on 12/21/2017.
 */

public class UserWelcome extends Fragment {
    @BindView(R.id.userdetails)
    RecyclerView users;
    Context context;
    Firebase firebase;
    View view;
    String Username;
     DatabaseReference UsersDatabse;
    FirebaseRecyclerAdapter adapter;
    DatabaseReference databaseReference;
     BroadcastReceiver broadcastReceiver;
    String userName,userId,userStatus;
    private DatabaseReference userInfo;
    List<UserModel> msg=new ArrayList<>();
    private UserModelAdapter manualAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        firebase=new Firebase(context);
        firebase.getUsername();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.signout).setVisible(true);
        menu.findItem(R.id.profile).setVisible(true);
        menu.findItem(R.id.groupcomm).setVisible(true);
        menu.findItem(R.id.groupchat).setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.userpage,container,false);
        ButterKnife.bind(this,view);
        recy();
//        Recycler();

        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Username=intent.getStringExtra("data");
//                recy();
            }
        };

        IntentFilter action=new IntentFilter("action.Update");

        context.registerReceiver(broadcastReceiver,action);

        //CODE NOT USED
// CODE FOR BACK CLICK EVENT IN FRAGMENT
//        view.setFocusableInTouchMode(true);
//        view.requestFocus();
//        view.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//                    Log.i("test", "onKey Back listener is working!!!");
//
//
//
//                    return true;
//                }
//                return false;
//            }
//        });
//


        return view;
    }

    public void Recycler(){
        users.setHasFixedSize(true);
        users.setLayoutManager(new LinearLayoutManager(context));
        manualAdapter=new UserModelAdapter(msg);
        users.setAdapter(manualAdapter);
        userStatus();

    }

    public void userStatus(){

        userInfo= FirebaseDatabase.getInstance().getReference("All User");
        userInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                UserModel mg = dataSnapshot.getValue(UserModel.class);
//                msg.add(mg);
//                Log.e("UserData",msg.get(0).getStatus()+"\n"+msg.get(0).getUsername());
//                manualAdapter.notifyDataSetChanged();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    UserModel person = postSnapshot.getValue(UserModel.class);

                    //add person to your list
                    msg.add(person);
                    //create a list view, and add the apapter, passing in your list
                }
                //                Log.e("UserData",msg.get(0).getStatus()+"\n"+msg.get(0).getUsername());
                manualAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        userInfo.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                UserModel mg = dataSnapshot.getValue(UserModel.class);
//                msg.add(mg);
//                for(DataSnapshot data:dataSnapshot.getChildren()) {
//
//
//                }
//
//
//
//                manualAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public void recy(){
        databaseReference=FirebaseDatabase.getInstance().getReference().child("All User");
        users.setHasFixedSize(true);
        users.setLayoutManager(new LinearLayoutManager(context));
        adapter= new FirebaseRecyclerAdapter<UserModel, UserModelViewHolder>(UserModel.class, R.layout.userinfo,UserModelViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(UserModelViewHolder viewHolder, final UserModel model, int position) {
                viewHolder.setName(model.getUsername());
                viewHolder.setstatus(model.getStatus());

                final String uid=getRef(position).getKey();
                if(model.getUsername().equals(Username)) {
                    userName=model.getUsername();
                    userId=uid;
                    userStatus=model.getStatus();
                    viewHolder.hide();
                }
                View view=viewHolder.getView();

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ChatPage fragment=new ChatPage();

                        Bundle arguments = new Bundle();
                        arguments.putString( "chatUserId" , uid);
                        arguments.putString( "chatUserName" , model.getUsername());
                        arguments.putString( "chatUserStatus" , model.getStatus());
                        arguments.putString( "UserId" , userId);
                        arguments.putString( "UserName" , userName);
                        arguments.putString( "UserStatus" , userStatus);

                        fragment.setArguments(arguments);

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.main, fragment);
                        transaction.addToBackStack("chat");
                        transaction.commit();

                    }
                });

            }
        };
        users.setAdapter(adapter);
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onStart() {
        super.onStart();
//        recy();


    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,view).unbind();
        context.unregisterReceiver(broadcastReceiver);
//        adapter.cleanup();

    }





}
