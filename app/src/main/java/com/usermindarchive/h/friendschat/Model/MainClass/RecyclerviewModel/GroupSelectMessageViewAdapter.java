package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermindarchive.h.friendschat.R;

import java.util.List;

/**
 * Created by HERO on 1/4/2018.
 */

public class GroupSelectMessageViewAdapter extends RecyclerView.Adapter<GroupSelectMessageViewHolder> {
    private  List<GroupuSelectMessageModel> msg;
    private String uid;

    public GroupSelectMessageViewAdapter(List<GroupuSelectMessageModel> msg, String uid) {
        this.msg=msg;
        this.uid=uid;
    }

    @Override
    public GroupSelectMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent, parent, false);

            return new GroupSelectMessageViewHolder.sent(view);
        } else {
            View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupselectreceive, parent, false);

            return new GroupSelectMessageViewHolder.received(view2);
        }
    }

    @Override
    public void onBindViewHolder(GroupSelectMessageViewHolder holder, int position) {
        GroupuSelectMessageModel m=msg.get(position);
        switch (holder.getItemViewType()) {
            case 1:
                ((GroupSelectMessageViewHolder.sent)holder).msg.setText(msg.get(position).getMessage());
                break;
            case 0:
                ((GroupSelectMessageViewHolder.received)holder).rec.setText(msg.get(position).getMessage());
                ((GroupSelectMessageViewHolder.received)holder).setuser(msg.get(position).getUsername());
                break;
        }
    }
    @Override
    public int getItemViewType(int position) {
        Log.e("UID",msg.get(position).getUid()+"\n"+uid);
        if(msg.get(position).getUid().toString().equals(uid)){

            return 1;
        }else
            return 0;
    }

    @Override
    public int getItemCount() {
        return msg.size();
    }
}
