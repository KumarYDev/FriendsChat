package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermindarchive.h.friendschat.R;

import java.util.List;
import java.util.Map;

/**
 * Created by HERO on 1/3/2018.
 */

public class GroupAllPageAdapter extends RecyclerView.Adapter<GroupListModelViewHolder> {
    public GroupAllPageAdapter(List<GroupInfoModel> data) {
        this.data = data;
    }

    List<GroupInfoModel> data;

    @Override
    public GroupListModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grouplistingle,parent,false);
        return new GroupListModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupListModelViewHolder holder, int position) {
        holder.setName(data.get(position).getGroupname());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
