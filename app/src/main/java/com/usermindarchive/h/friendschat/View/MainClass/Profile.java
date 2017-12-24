package com.usermindarchive.h.friendschat.View.MainClass;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;
import com.usermindarchive.h.friendschat.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HERO on 12/21/2017.
 */

public class Profile extends Fragment {
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.save)
    Button save;
    String name;
    View view;
    Firebase firebase;

    BroadcastReceiver broadcastReceiver;

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        firebase=new Firebase(context);
        this.context=context;


    }

    @Override
    public void onDetach() {
        super.onDetach();
        context.unregisterReceiver (broadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.profile,container,false);
        ButterKnife.bind(this,view);

        name=firebase.getUsername();
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                username.setText(intent.getStringExtra("data"));

            }
        };

        IntentFilter action=new IntentFilter("action.Update");

        context.registerReceiver(broadcastReceiver,action);



        return view;
    }

    @OnClick(R.id.save)
    public void save(){
        setUsername();

//        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.bind(this,view).unbind();
    }


    public void setUsername(){
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getActivity());
        }
        builder.setTitle("Username Change")
                .setMessage("Type User Name");
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);



        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!input.getText().toString().isEmpty()){
                    firebase.UserName(input.getText().toString());

                    getFragmentManager().popBackStackImmediate();
                }else{
                    firebase.UserName(name);
                }
            }
        })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        getFragmentManager().popBackStackImmediate();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}
