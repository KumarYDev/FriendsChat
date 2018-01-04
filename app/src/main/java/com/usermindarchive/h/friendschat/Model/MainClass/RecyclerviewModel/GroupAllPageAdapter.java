package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermindarchive.h.friendschat.R;
import com.usermindarchive.h.friendschat.View.MainClass.GroupAllChatPage;

import java.util.List;
import java.util.Map;

/**
 * Created by HERO on 1/3/2018.
 */

public class GroupAllPageAdapter extends RecyclerView.Adapter<GroupListModelViewHolder> {
    List<GroupInfoModel> data;
    private FragmentManager fragmentManager;


    public GroupAllPageAdapter(List<GroupInfoModel> data, FragmentManager context) {
        this.data = data;
        this.fragmentManager=context;
    }



    @Override
    public GroupListModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grouplistingle,parent,false);
        return new GroupListModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupListModelViewHolder holder, int position) {
        final String name=data.get(position).getGroupname();
        final String id=data.get(position).getGroupid();
        holder.setName(data.get(position).getGroupname());
        View view=holder.getView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupAllChatPage fragment=new GroupAllChatPage();
                Bundle argument=new Bundle();
                argument.putString("groupname",name);
                argument.putString("groupid",id);
                fragment.setArguments(argument);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main, fragment);
                transaction.addToBackStack("groupall");
                transaction.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
