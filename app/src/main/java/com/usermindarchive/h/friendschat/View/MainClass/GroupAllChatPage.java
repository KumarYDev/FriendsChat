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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupSelectMessageViewAdapter;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupuSelectMessageModel;
import com.usermindarchive.h.friendschat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HERO on 1/4/2018.
 */

public class GroupAllChatPage extends Fragment {

    private String name,id;

    Context context;
    private Firebase firebase;
    @BindView(R.id.grpmsg)
    EditText message;
    @BindView(R.id.grpsend)
    Button send;
    private View view;

    @BindView(R.id.group)RecyclerView chat;
    private String uid;
    private DatabaseReference userInfo;
    private List<GroupuSelectMessageModel> msg=new ArrayList<>();
    private GroupSelectMessageViewAdapter  manualAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        firebase=new Firebase(context);
        firebase.getUsername();
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.groupselectchatpage,container,false);
        ButterKnife.bind(this,view);

        Bundle arg=getArguments();

        name=arg.getString("groupname");
        id=arg.getString("groupid");
        Recycler();


        return view;
    }
    public void Recycler(){



        userInfo= FirebaseDatabase.getInstance().getReference("Group Chat").child(id).child("messages");
        userInfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
msg.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    GroupuSelectMessageModel person = postSnapshot.getValue(GroupuSelectMessageModel.class);

                    //add person to your list
                    msg.add(person);
                    //create a list view, and add the apapter, passing in your list
                }
                //                Log.e("UserData",msg.get(0).getStatus()+"\n"+msg.get(0).getUsername());
                manualAdapter.notifyDataSetChanged();
                chat.scrollToPosition(msg.size()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        chat.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(true);
        manualAdapter=new GroupSelectMessageViewAdapter(msg,uid );
        chat.setLayoutManager(layoutManager);
        chat.setAdapter(manualAdapter);


    }

    @OnClick(R.id.grpsend)
    public void sendmsg(){
        if(!message.getText().toString().trim().isEmpty()) {
            firebase.sendGroupSelectMessage(message.getText().toString(),id);
            message.setText("");
            chat.scrollToPosition(msg.size()-1);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,view).unbind();

    }
}
