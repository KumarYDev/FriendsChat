package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.usermindarchive.h.friendschat.R;

/**
 * Created by HERO on 12/24/2017.
 */

public class GroupChatModelViewHolder extends RecyclerView.ViewHolder {


    View view;
    TextView name,state;



    CheckBox checkBox;


    public CheckBox getCheckBox() {
        return checkBox;
    }
    public GroupChatModelViewHolder(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.userid);
        state=itemView.findViewById(R.id.status);
        checkBox=itemView.findViewById(R.id.checkBox);
        view=itemView;
    }

    public void setName(String username) {
        name.setText(username);
    }
    public View getView() {
        return view;
    }

    public void setstatus(String status) {
        state.setText(status);
        if(!state.getText().equals("OFFLINE"))
            state.setBackgroundColor(Color.GREEN);
        else state.setBackgroundColor(Color.RED);
    }

    public void hide() {
        name.setVisibility(View.GONE);
        state.setVisibility(View.GONE);
        checkBox.setVisibility(View.GONE);
    }
}
