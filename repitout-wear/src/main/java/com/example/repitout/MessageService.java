package com.example.repitout;

import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        //find the path
        if (messageEvent.getPath().equals("/my_path")) {
            //need to retreive the message
            final String message = new String(messageEvent.getData());
            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("reps", message);

            //Broadcast the received Data Layer messages locally
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);

        }

        else {
            super.onMessageReceived(messageEvent);
        }
    }
}