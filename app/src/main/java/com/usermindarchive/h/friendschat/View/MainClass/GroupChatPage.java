package com.usermindarchive.h.friendschat.View.MainClass;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupChatModelViewHolder;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupListModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupListModelViewHolder;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.UserModel;
import com.usermindarchive.h.friendschat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HERO on 1/1/2018.
 */

public class GroupChatPage extends Fragment {

    private View view;
    private Context context;
    Firebase firebase;
    @BindView(R.id.grouplist)
    RecyclerView groupList;
    @BindView(R.id.groupadd)
    FloatingActionButton groupAdd;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        firebase=new Firebase(context);
        firebase.getUsername();

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.groupchatpage,container,false);
        ButterKnife.bind(this,view);

        
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
        groupList.setHasFixedSize(true);
        groupList.setLayoutManager(new LinearLayoutManager(context));
        databaseReference= FirebaseDatabase.getInstance().getReference().child("GroupChat");
        adapter= new FirebaseRecyclerAdapter<GroupListModel, GroupListModelViewHolder>(GroupListModel.class, R.layout.grouplistingle,GroupListModelViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(GroupListModelViewHolder viewHolder, final GroupListModel model, int position) {



            }
        };

        groupList.setAdapter(adapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,view).unbind();

    }
}
