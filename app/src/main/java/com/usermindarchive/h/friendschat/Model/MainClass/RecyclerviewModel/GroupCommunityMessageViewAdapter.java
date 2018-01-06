package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermindarchive.h.friendschat.R;

import java.util.List;

/**
 * Created by HERO on 1/2/2018.
 */

public class GroupCommunityMessageViewAdapter extends RecyclerView.Adapter<GroupCommunityMessageViewHolder> {

    List<GroupuCommMessageModel> msg;
    String uid;

    public GroupCommunityMessageViewAdapter(List<GroupuCommMessageModel> msg, String uid) {
        this.msg=msg;
        this.uid=uid;
        Log.e("UID","\n"+uid);
    }


    @Override
    public GroupCommunityMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){

            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sent,parent,false);
            Log.e("Type","sent");
            return new GroupCommunityMessageViewHolder.sent(view);
        }else{
            View view2= LayoutInflater.from(parent.getContext()).inflate(R.layout.groupreceive,parent,false);
            Log.e("Type","received");
            return new GroupCommunityMessageViewHolder.received(view2);

        }
    }

    @Override
    public void onBindViewHolder(GroupCommunityMessageViewHolder holder, int position) {
        GroupuCommMessageModel m=msg.get(position);
        switch (holder.getItemViewType()) {
            case 1:
                ((GroupCommunityMessageViewHolder.sent)holder).msg.setText(msg.get(position).getMessage());
                break;
            case 0:
                ((GroupCommunityMessageViewHolder.received)holder).rec.setText(msg.get(position).getMessage());
                ((GroupCommunityMessageViewHolder.received)holder).setuser(msg.get(position).getUsername());
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
