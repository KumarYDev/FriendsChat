package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermindarchive.h.friendschat.R;

import java.util.List;

/**
 * Created by HERO on 12/24/2017.
 */

public class UserModelAdapter extends RecyclerView.Adapter<UserModelViewHolder> {

    private final List<UserModel> msg;

    public UserModelAdapter(List<UserModel> msg) {
        this.msg=msg;
    }

    @Override
    public UserModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.userinfo,parent,false);
        return new UserModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserModelViewHolder holder, int position) {
        holder.setName(msg.get(position).getUsername());
        holder.setstatus(msg.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return msg.size();
    }
}
