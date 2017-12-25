package com.usermindarchive.h.friendschat.View.MainClass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.MessageModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.MessageViewAdapter;
import com.usermindarchive.h.friendschat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HERO on 12/22/2017.
 */

public class ChatPage extends Fragment {
    Context context;
    private Firebase firebase;
    private View view;
    @BindView(R.id.message)
    EditText message;
    @BindView(R.id.send)
    Button send;
    @BindView(R.id.chat)RecyclerView chat;
    Map data;
    List<MessageModel> msg=new ArrayList<>();

    String userName,userId,userStatus,chatUserName,chatUserId,chatUserStatus;
    private BroadcastReceiver broadcastReceiver;
    private MessageViewAdapter adapter;
    private DatabaseReference retriveMessages;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        firebase=new Firebase(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        context.unregisterReceiver (broadcastReceiver);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.chatpage,container,false);
        ButterKnife.bind(this,view);
        chatInfo();
//        firebase.retriveData();
        Recycler();
//        broadcastReceiver=new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                msg.add((MessageModel) intent.getParcelableExtra("mess"));
//
//                Log.e("size", String.valueOf(msg.size()));
//                adapter.notifyDataSetChanged();
//
//            }
//        };
//
//        IntentFilter action=new IntentFilter("action.Messages");
//
//        context.registerReceiver(broadcastReceiver,action);

        return view;
    }

    public void load(){
        retriveMessages= FirebaseDatabase.getInstance().getReference("Chat").child("Messages")
                .child(data.get("UserId").toString()).child(data.get("chatUserId").toString());

        retriveMessages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageModel mg=dataSnapshot.getValue(MessageModel.class);
                msg.add(mg);
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
        adapter=new MessageViewAdapter(msg);
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
            chat.scrollToPosition(msg.size());

        }
    }

    public void chatInfo(){
        Bundle info=getArguments();
        userName=info.getString("UserName");
        userId=info.getString("UserId");
        userStatus=info.getString("UserStatus");
        chatUserName=info.getString("chatUserName");
        chatUserId=info.getString("chatUserId");
        chatUserStatus=info.getString("chatUserStatus");
        data=new HashMap();
        data.put("UserName",userName);
        data.put("UserId",userId);
        data.put("UserStatus",userStatus);
        data.put("chatUserName",chatUserName);
        data.put("chatUserStatus",chatUserStatus);
        data.put("chatUserId",chatUserId);
        firebase.userdata(data);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.bind(this,view).unbind();

    }
}
