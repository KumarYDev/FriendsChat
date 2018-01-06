package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.usermindarchive.h.friendschat.R;

/**
 * Created by HERO on 1/2/2018.
 */

class GroupCommunityMessageViewHolder extends RecyclerView.ViewHolder {


    public GroupCommunityMessageViewHolder(View itemView) {
        super(itemView);
    }


    public static class sent extends GroupCommunityMessageViewHolder {

        TextView msg;

        public sent(View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.sentmessage);

        }
        public void setmessage(String s){
            msg.setText(s);
        }
    }

    public static class received extends GroupCommunityMessageViewHolder {
        TextView rec,user;

        public received(View itemView) {
            super(itemView);
            rec = itemView.findViewById(R.id.message);
            user = itemView.findViewById(R.id.user);

        }
        public void setmessage(String s){
            rec.setText(s);
        }
        public void setuser(String s){
            user.setText(s);
        }
    }
}
