package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.usermindarchive.h.friendschat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HERO on 12/22/2017.
 */

public class UserModelViewHolder extends RecyclerView.ViewHolder {


    View view;
   TextView name,state;


    public UserModelViewHolder(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.userid);
        state=itemView.findViewById(R.id.status);
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
    }
}