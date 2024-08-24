package edu.msu.moranti1.project1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Future;

public class FirebaseService extends FirebaseMessagingService {

    private static final String TAG = "Firebase Message";
    private String user = "moranti1";

    public FirebaseService() {
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // Send the token to your server for storage
        saveToken(user, token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
    // ...
    // TODO(developer): Handle FCM messages here.
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    Log.d("FCM Message", "From: " + remoteMessage.getFrom());
    Log.d("FCM Message", "Data: " + remoteMessage.getData());

    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
    //
    // Handle the message, typically pass it to the main activity
    //
    }

        // Also if you intend on generating your own notifications as a result of a received FCM
    // message, here is where that should be initiated. See sendNotification method below.
    }

    public void saveToken(String user, String fcmid) {
        Cloud c = new Cloud();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean success = c.registerFCMId(user, fcmid);
                } catch (IOException e) {
                    Log.e("FCM", "There was an error when saving the fcmid " + e.getMessage());
                }


            }
        }).start();
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}