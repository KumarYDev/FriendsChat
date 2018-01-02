package com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.usermindarchive.h.friendschat.R;

/**
 * Created by HERO on 1/1/2018.
 */

public class GroupListModelViewHolder extends RecyclerView.ViewHolder {




    View view;
    TextView name;


    public GroupListModelViewHolder(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.groupid);

        view=itemView;
    }

    public void setName(String username) {
        name.setText(username);
    }
    public View getView() {
        return view;
    }



    public void hide() {
        name.setVisibility(View.GONE);

    }
}
