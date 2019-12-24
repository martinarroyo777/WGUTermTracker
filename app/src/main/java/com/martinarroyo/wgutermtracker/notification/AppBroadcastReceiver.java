package com.martinarroyo.wgutermtracker.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class AppBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "You picked today!", Toast.LENGTH_SHORT).show();
        String title = intent.getStringExtra("COURSE START");
        String message = intent.getStringExtra("COURSE START MESSAGE");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(title,message);
        notificationHelper.getManager().notify(1,nb.build());
    }
}
