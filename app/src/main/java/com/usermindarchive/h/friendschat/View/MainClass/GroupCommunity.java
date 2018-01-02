package com.usermindarchive.h.friendschat.View.MainClass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupCommunityMessageViewAdapter;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.MessageModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.MessageViewAdapter;
import com.usermindarchive.h.friendschat.R;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HERO on 1/2/2018.
 */

public class GroupCommunity extends Fragment {
    Context context;
    private Firebase firebase;
    @BindView(R.id.message)
    EditText message;
    @BindView(R.id.send)
    Button send;
    private View view;
    private DatabaseReference retriveMessages;
    private GroupCommunityMessageViewAdapter adapter;
    @BindView(R.id.group)RecyclerView chat;
    private Map data;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        firebase=new Firebase(context);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.groupcomm,container,false);
        return view;
    }

    public void load(){
        retriveMessages= FirebaseDatabase.getInstance().getReference("Group Coummunity");

        retriveMessages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageModel mg=dataSnapshot.getValue(MessageModel.class);
//                msg.add(mg);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void Recycler(){
//        adapter=new GroupCommunityMessageViewAdapter(msg);
        chat.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(true);
        chat.setLayoutManager(layoutManager);

        chat.setAdapter(adapter);
        load();
    }

    @OnClick(R.id.send)
    public void send(){
        if(!message.getText().toString().isEmpty()) {
            data.put("message",message.getText().toString());
            firebase.sendMessage(data);
            message.setText("");
//            chat.scrollToPosition(msg.size());

        }
    }
}
