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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupChatModelViewHolder;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupUserMapModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.UserModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.UserModelViewHolder;
import com.usermindarchive.h.friendschat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HERO on 12/24/2017.
 * This code will give the list of all users
 * and option to select the users to create the group
 *
 */

public class GroupChatUserList extends Fragment {

    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference databaseReference;
    Context context;
    Firebase firebase;
    private BroadcastReceiver broadcastReceiver;
    String Username;
    @BindView(R.id.groupchatpage)
    RecyclerView groupchat;
    StringBuffer data=new StringBuffer();
    View view;
    Map<String,GroupUserMapModel> userDetails=new HashMap<String,GroupUserMapModel>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        firebase=new Firebase(context);
        firebase.getUsername();

    }

    public void username(){
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Username=intent.getStringExtra("data");
                recy();
            }
        };

        IntentFilter action=new IntentFilter("action.Update");

        context.registerReceiver(broadcastReceiver,action);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.groupchat,container,false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        username();


        return view;
    }

    public void recy(){

        groupchat.setHasFixedSize(true);
        groupchat.setLayoutManager(new LinearLayoutManager(context));
        databaseReference= FirebaseDatabase.getInstance().getReference().child("All User");
        adapter= new FirebaseRecyclerAdapter<UserModel, GroupChatModelViewHolder>(UserModel.class, R.layout.groupchatsingle,GroupChatModelViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(GroupChatModelViewHolder viewHolder, final UserModel model, final int position) {
                if(model.getUsername().equals(Username)) {
                    viewHolder.hide();
                    addToGroup(getRef(position).getKey(),model.getUsername());
                }
                viewHolder.setName(model.getUsername());
                viewHolder.setstatus(model.getStatus());
                viewHolder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b){
                            addToGroup(getRef(position).getKey(),model.getUsername());
                        }else{
                            deleteFromGroup(getRef(position).getKey());
                        }
                        print();

                    }
                });

            }
        };

        groupchat.setAdapter(adapter);
    }

    private void print() {
        for (GroupUserMapModel value : userDetails.values()) {
            data.append(value.getUserID()+"\n"+value.getUserName()+"\n");

        }
        Log.e("Selected users", String.valueOf(data));
        data.setLength(0);

    }

    private void deleteFromGroup(String key) {
        if(userDetails.containsKey(key)){
            userDetails.remove(key);

        }
    }

    private void addToGroup(String key, String username) {
        userDetails.put(key,new GroupUserMapModel(key,username));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,view).unbind();
        context.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.groupadd,menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.creategrp:
                if(userDetails.size()<=1){
                    getFragmentManager().popBackStackImmediate();
                    Toast.makeText(context,"No User Selected \nGroup Creation Cancelled",Toast.LENGTH_LONG).show();
                }else {
                    groupname();
                }
                return true;

            default:
                break;
        }

        return false;
    }

    private void groupname() {

            AlertDialog.Builder builder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getActivity());
            }
            builder.setTitle("Group Name")
                    .setMessage("Select a Group Name ");
            final EditText input = new EditText(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            builder.setView(input);



            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(input.getText().toString().trim().isEmpty()){
                        groupname();
                        Toast.makeText(context,"Enter the Group Name",Toast.LENGTH_LONG).show();
                    }else {
                        firebase.createGroup(input.getText().toString().trim(), userDetails);
                        getFragmentManager().popBackStack();

                    }

                }
            })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_secure)
                    .show();

    }
}
