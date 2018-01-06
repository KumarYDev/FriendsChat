package com.usermindarchive.h.friendschat.Model.MainClass.Firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.usermindarchive.h.friendschat.MainActivity;
import com.usermindarchive.h.friendschat.Model.MainClass.CreateUser.CreateUserModel;
import com.usermindarchive.h.friendschat.Model.MainClass.Login.LoginAuth;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupUserMapModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupuCommMessageModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.GroupuSelectMessageModel;
import com.usermindarchive.h.friendschat.Model.MainClass.RecyclerviewModel.MessageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;



public class Firebase {
    private FirebaseAuth mAuth;
    Context context;
    boolean sign, create;
    Background back;
    String email, password;
    CreateUserModel createUserModel;
    LoginAuth loginModel;
    MainActivity mainActivity;
    DatabaseReference userData;
    DatabaseReference allData;
    DatabaseReference userstatus;
    DatabaseReference sendMessage,retriveMessages;

    String profile="Profile",message="Message",username="Username";
    Boolean status=false;
    String name;
    Map data;
    private DatabaseReference createGroup;


    public Firebase(Context context) {
        mAuth = FirebaseAuth.getInstance();
        this.context = context;


    }

    public Firebase(Context applicationContext, MainActivity mainActivity) {
        this.mainActivity=mainActivity;
        mAuth = FirebaseAuth.getInstance();
    }

    public Firebase() {
        mAuth = FirebaseAuth.getInstance();
    }


    public boolean createUser(String email, String password, CreateUserModel createUserModel) {
        back=new Background();
        this.email = email;
        this.password = password;
        this.createUserModel=createUserModel;

        back.execute(1);


        return create;

    }

    public void signUser(String email, String password, LoginAuth loginModel) {
        back=new Background();
        this.email = email;
        this.password = password;

        this.loginModel=loginModel;

        back.execute(2);


    }

    public void userLogout() {
        mAuth.signOut();
        Log.e("userLoggin","User Logged Out");
        mainActivity.loginpage();

    }

    public boolean userexits() {
        Boolean user=false;
//        Log.e("Userdata",mAuth.getCurrentUser().getUid()+"\n"+mAuth.getCurrentUser().getDisplayName()+"\n"+mAuth.getCurrentUser().getEmail());

        if(mAuth.getCurrentUser()==null)
            user=true;
        return user;
    }

    public void UserName(String s) {
        userData= FirebaseDatabase.getInstance().getReference("All User").child(mAuth.getCurrentUser().getUid());
        if(s.isEmpty()||s==null) {
           s="Guest"+TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        }
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(s).build();
        mAuth.getCurrentUser().updateProfile(profileUpdates);

            userData.child(username).setValue(s).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(context,"Username Updated",Toast.LENGTH_LONG).show();
                    }
                }
            });
    }


    public String getUsername(){

        userData= FirebaseDatabase.getInstance().getReference("All User").child(mAuth.getCurrentUser().getUid()).child(username);

        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name=dataSnapshot.getValue().toString();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();
                mAuth.getCurrentUser().updateProfile(profileUpdates);

                Intent uname=new Intent("action.Update");
                uname.putExtra("data",name);
                context.sendBroadcast(uname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return name;
    }

    public void getAllData(){
        allData=FirebaseDatabase.getInstance().getReference("All User");
        allData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                name=dataSnapshot.getValue().toString();
                Intent data=new Intent("action.alldata");
                data.putExtra("data",name);
                context.sendBroadcast(data);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                name=dataSnapshot.getValue().toString();
                Intent data=new Intent("action.alldata");
                data.putExtra("data",name);
                context.sendBroadcast(data);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                name=dataSnapshot.getValue().toString();
                Intent data=new Intent("action.alldata");
                data.putExtra("data",name);
                context.sendBroadcast(data);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                name=dataSnapshot.getValue().toString();
                Intent data=new Intent("action.alldata");
                data.putExtra("data",name);
                context.sendBroadcast(data);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("dataError","No data is returened");

            }
        });

    }

    public void userStatus(String b){
        if(!userexits()) {
            userstatus = FirebaseDatabase.getInstance().getReference("All User").child(mAuth.getCurrentUser().getUid()).child("status");
            userstatus.setValue(b);

        }
    }

    public void createGroup(String name, Map<String, GroupUserMapModel> userDetails){
        createGroup=FirebaseDatabase.getInstance().getReference("Group Chat");
        DatabaseReference pushid=createGroup.push();
        String push_id=pushid.getKey();

        Map details=new HashMap();
        for(GroupUserMapModel map: userDetails.values()){
            details.put(push_id+"/Users/"+map.getUserID().toString()+"/UserName",map.getUserName().toString());
        }

        details.put(push_id+"/name",name);
        createGroup.updateChildren(details,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError!=null){
                    Log.e("Error-Chat",databaseError.getMessage().toString());
                }
            }
        });

    }
    public void usersGroup(String id,String name){
        Map user=new HashMap();
        user.put("UserName",name);


    }

    public void sendMessage(Map data) {
        sendMessage=FirebaseDatabase.getInstance().getReference("Chat").child("Messages");

        String mainUser=data.get("UserId").toString()+"/"+data.get("chatUserId").toString();
        String chatUser=data.get("chatUserId").toString()+"/"+data.get("UserId").toString();

        DatabaseReference push_id=sendMessage.child(data.get("UserName").toString()).child(data.get("chatUserName").toString()).push();
        String pushid=push_id.getKey();
        Map messageMain=new HashMap();
        messageMain.put("message",data.get("message").toString());
        messageMain.put("type","sent");
        messageMain.put("time", ServerValue.TIMESTAMP);

        Map message=new HashMap();
        message.put("message",data.get("message").toString());
        message.put("type","received");
        message.put("time", ServerValue.TIMESTAMP);
        Map userMap=new HashMap();
        userMap.put(mainUser+"/"+pushid,messageMain);


        userMap.put(chatUser+"/"+pushid,message);




        sendMessage.updateChildren(userMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError!=null){
                    Log.e("Error-Chat",databaseError.getMessage().toString());
                }
            }
        });



    }

    public void userdata(Map data) {
        this.data=data;

    }

    public void retriveData() {
        retriveMessages=FirebaseDatabase.getInstance().getReference("Chat").child("Messages")
                .child(data.get("UserId").toString()).child(data.get("chatUserId").toString());

        retriveMessages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageModel msg=dataSnapshot.getValue(MessageModel.class);

                List<MessageModel>md=new ArrayList<>();
                md.add(msg);
                Log.e("size", String.valueOf(md.size()));
                Log.e("message",md.get(0).getMessage().toString());


                Intent uname=new Intent("action.Messages");
//                uname.putExtra("mess",msg);
                context.sendBroadcast(uname);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void sendGroupCommMessage(String s) {

        if(!FirebaseAuth.getInstance().getCurrentUser().getDisplayName().isEmpty()) {

            FirebaseDatabase.getInstance()
            .getReference("Group Community")
            .push()
            .setValue(new GroupuCommMessageModel(s,
                    FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                    FirebaseAuth.getInstance().getCurrentUser().getUid())
            );

        }else setusername();
    }

    private void setusername() {
        userData= FirebaseDatabase.getInstance().getReference("All User").child(mAuth.getCurrentUser().getUid()).child(username);

        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name=dataSnapshot.getValue().toString();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();

                mAuth.getCurrentUser().updateProfile(profileUpdates);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public String getUserID() {
        return mAuth.getCurrentUser().getUid().toString();
    }

    public void sendGroupSelectMessage(String message, String groupid) {
        if(!FirebaseAuth.getInstance().getCurrentUser().getDisplayName().isEmpty()) {

            FirebaseDatabase.getInstance()
                    .getReference("Group Chat")
                    .child(groupid)
                    .child("messages")
                    .push()
                    .setValue(new GroupuSelectMessageModel(message,
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid())
                    );

        }else {
            userData= FirebaseDatabase.getInstance().getReference("All User").child(mAuth.getCurrentUser().getUid()).child(username);

            userData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    name=dataSnapshot.getValue().toString();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();

                    mAuth.getCurrentUser().updateProfile(profileUpdates);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            FirebaseDatabase.getInstance()
                    .getReference("Group Chat")
                    .child(groupid)
                    .child("messages")
                    .push()
                    .setValue(new GroupuSelectMessageModel(message,
                            FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid())
                    );


        };
    }


    class Background extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... integers) {
            if (integers[0] == 1) {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    create = true;

                                    userData= FirebaseDatabase.getInstance().getReference("All User").child(mAuth.getCurrentUser().getUid());
                                    userData.child("status").setValue("ONLINE").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                userData.child(profile).child("UID").setValue(mAuth.getCurrentUser().getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                            createUserModel.status(true);

                                                    }
                                                });
                                            }
                                        }
                                    });

                                    // Sign in success, update UI with the signed-in user's information
                                    Log.e(TAG, "createUserWithEmail:success"+ TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

                                } else {
                                    create = false;


                                    Intent uname=new Intent("action.Email");
                                    uname.putExtra("email",task.getException().getMessage().toString());
                                    context.sendBroadcast(uname);

                                    // If sign in fails, display a message to the user.
                                    Log.e(TAG, "createUserWithEmail:failure", task.getException());
//                                    Toast.makeText(context, "Authentication failed./n "+task.getException().getMessage(),
//                                            Toast.LENGTH_SHORT).show();
                                    createUserModel.status(create);
                                }

                            }
                        });

            }

            if (integers[0]==2){
                sign = false;
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    loginModel.Status(true);

                                    // Sign in success, update UI with the signed-in user's information
                                    Log.e(TAG, "signInWithEmail:success");
                                    sign = true;
                                } else {
                                    loginModel.Status(false);
                                    // If sign in fails, display a message to the user.
                                    Log.e(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(context, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }


            return create;
        }

    }
}