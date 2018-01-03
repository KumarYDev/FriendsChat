package com.usermindarchive.h.friendschat.View.MainClass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupAllPageAdapter;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupInfoModel;
import com.usermindarchive.h.friendschat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HERO on 1/1/2018.
 */

public class GroupAllPage extends Fragment {

    private View view;
    private Context context;
    Firebase firebase;
    @BindView(R.id.grouplist)
    RecyclerView groupList;
    @BindView(R.id.groupadd)
    FloatingActionButton groupAdd;
    private DatabaseReference databaseReference;


    String userID;
    private GroupAllPageAdapter adapter;
    private List<GroupInfoModel> data=new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        firebase=new Firebase(context);
        firebase.getUsername();
        userID=firebase.getUserID();

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.groupchatpage,container,false);
        ButterKnife.bind(this,view);
        recy();
        
        return view;
    }
    @OnClick(R.id.groupadd)
    public void groupAdd(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main, new GroupChatUserList());
        transaction.addToBackStack("group");
        transaction.commit();
    }

    public void recy(){


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Group Chat");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data.clear();
                String name,groupid;

                Map uid=new HashMap();
                Log.e("details",dataSnapshot.getKey().toString());
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    Log.e("details",d.getKey().toString());
                    Log.e("details",d.child("name").getValue().toString());
                    name=d.child("name").getValue().toString();
                    groupid=d.getKey().toString();
                    data.add(new GroupInfoModel(groupid,name));
                    for(DataSnapshot u:d.child("Users").getChildren()){
                        Log.e("details",u.getKey().toString());
                        uid.put(u.getKey().toString(),u.getKey().toString());
                    }
                    if(uid.containsValue(userID)){
                        Log.e("details","Group is there\n"+d.child("name").getValue().toString());

                        adapter.notifyDataSetChanged();
                    }else data.clear();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter=new GroupAllPageAdapter(data);
        groupList.setHasFixedSize(true);
        groupList.setLayoutManager(new LinearLayoutManager(context));



        groupList.setAdapter(adapter);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,view).unbind();

    }
}
