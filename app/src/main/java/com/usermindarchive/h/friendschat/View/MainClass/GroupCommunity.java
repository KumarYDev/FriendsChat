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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupCommunityMessageViewAdapter;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupuCommMessageModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.MessageModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.MessageViewAdapter;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.UserModel;
import com.usermindarchive.h.friendschat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HERO on 1/2/2018.
 */

public class GroupCommunity extends Fragment {
    Context context;
    private Firebase firebase;
    @BindView(R.id.grpmsg)
    EditText message;
    @BindView(R.id.grpsend)
    Button send;
    private View view;

    @BindView(R.id.group)RecyclerView chat;
    private DatabaseReference userInfo;
    private List<GroupuCommMessageModel> msg=new ArrayList<>();
    private GroupCommunityMessageViewAdapter manualAdapter;
    String uid;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,view).unbind();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        firebase=new Firebase(context);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.groupcomm,container,false);
        ButterKnife.bind(this,view);
        Recycler();
        return view;

    }


    public void Recycler(){



        userInfo= FirebaseDatabase.getInstance().getReference("Group Community");
        userInfo.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                GroupuCommMessageModel person = dataSnapshot.getValue(GroupuCommMessageModel.class);

                //add person to your list
                msg.add(person);
                manualAdapter.notifyDataSetChanged();
                chat.scrollToPosition(msg.size()-1);
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
//        userInfo.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    //Getting the data from snapshot
//                    GroupuCommMessageModel person = postSnapshot.getValue(GroupuCommMessageModel.class);
//
//                    //add person to your list
//                    msg.add(person);
//                    //create a list view, and add the apapter, passing in your list
//                }
//                //                Log.e("UserData",msg.get(0).getStatus()+"\n"+msg.get(0).getUsername());
//                manualAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        chat.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(true);
        manualAdapter=new GroupCommunityMessageViewAdapter(msg,uid );
        chat.setLayoutManager(layoutManager);
        chat.setAdapter(manualAdapter);


    }

    @OnClick(R.id.grpsend)
    public void sendmsg(){
        if(!message.getText().toString().trim().isEmpty()) {
            firebase.sendGroupCommMessage(message.getText().toString());
            message.setText("");
            chat.scrollToPosition(msg.size()-1);

        }
    }
}
