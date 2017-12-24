package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usermindarchive.h.friendschat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HERO on 12/23/2017.
 */

public class MessageViewAdapter extends RecyclerView.Adapter<MessageViewAdapter.ViewHolder> {

    List<MessageModel> message;

    public MessageViewAdapter(List<MessageModel> messages) {
        this.message=messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1){

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sent,parent,false);
            Log.e("Type","sent");
        return new sent(view);
        }else{
        View view2= LayoutInflater.from(parent.getContext()).inflate(R.layout.received,parent,false);
            Log.e("Type","received");
            return new received(view2);

        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageModel m=message.get(position);
        switch (holder.getItemViewType()) {
            case 1:
                ((sent)holder).msg.setText(message.get(position).getMessage());
                break;
            case 0:
                ((received)holder).rec.setText(message.get(position).getMessage());
                break;
        }


    }

    @Override
    public int getItemViewType(int position) {

        if(message.get(position).getType().toString().equals("sent"))
        return 1;
        else
            return 0;
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class sent extends ViewHolder{

        TextView msg;
        public sent(View itemView) {
            super(itemView);
            msg=itemView.findViewById(R.id.sentmessage);

        }
    }

    public class received extends ViewHolder{
        TextView rec;
        public received(View itemView) {
            super(itemView);
            rec=itemView.findViewById(R.id.message);

        }
    }
}
