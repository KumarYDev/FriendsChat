package com.usermindarchive.h.friendschat.View.MainClass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupChatModelViewHolder;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.UserModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.UserModelViewHolder;
import com.usermindarchive.h.friendschat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HERO on 12/24/2017.
 */

public class GroupChat extends Fragment {

    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference databaseReference;
    Context context;
    Firebase firebase;
    private BroadcastReceiver broadcastReceiver;
    String Username;
    @BindView(R.id.groupchatpage)
    RecyclerView groupchat;
    View view;

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
        username();
        recy();

        return view;
    }

    public void recy(){
        groupchat.setHasFixedSize(true);
        groupchat.setLayoutManager(new LinearLayoutManager(context));
        databaseReference= FirebaseDatabase.getInstance().getReference().child("All User");
        adapter= new FirebaseRecyclerAdapter<UserModel, GroupChatModelViewHolder>(UserModel.class, R.layout.groupchatsingle,GroupChatModelViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(GroupChatModelViewHolder viewHolder, final UserModel model, int position) {
                viewHolder.setName(model.getUsername());
                viewHolder.setstatus(model.getStatus());

                final String uid=getRef(position).getKey();
                if(model.getUsername().equals(Username)) {
                    viewHolder.hide();
                }


            }
        };

        groupchat.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,view).unbind();
        context.unregisterReceiver(broadcastReceiver);
    }
}
