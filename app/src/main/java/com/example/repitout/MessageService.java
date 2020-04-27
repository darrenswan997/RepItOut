package com.example.repitout;

import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        //need to find the path of the message

        if (messageEvent.getPath().equals("/my_path")){
            //retreive the message

            final String message = new String(messageEvent.getData());

            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("reps", message);

            //Broadcast the received Data layer locally
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        }
        /*if (messageEvent.getPath().equals("/exercise")) {
            //retreive the message

            final String message = new String(messageEvent.getData());

            Intent messageIntent1 = new Intent();
            messageIntent1.setAction(Intent.ACTION_SEND);
            messageIntent1.putExtra("exercise", message);

            //Broadcast the received Data layer locally
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent1);
        }*/else {
            super.onMessageReceived(messageEvent);
        }

    }
}
