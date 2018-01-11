package com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase_Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by HERO on 1/9/2018.
 */

public class Push_Notification extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        getNotification(remoteMessage.getNotification().getBody());


    }
    private void getNotification(String content) {
        NotificationManager notification= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mult = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle("Scheduled")
                .setContentText(content);
//        NotificationCompat.InboxStyle inbox=new NotificationCompat.InboxStyle(mult);
//        mult.setContentIntent(crt());
//        notification.notify(2,inbox.setSummaryText("this is inbox style")
//                .addLine("first line")
//                .addLine("second line").build());
        notification.notify(1,mult.build());;
    }
}

