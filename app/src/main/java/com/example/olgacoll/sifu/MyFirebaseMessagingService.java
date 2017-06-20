package com.example.olgacoll.sifu;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by olgacoll on 19/6/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService{

    public MyFirebaseMessagingService(){

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
            Log.e("FIREBASE", remoteMessage.getNotification().getBody());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
