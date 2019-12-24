package com.martinarroyo.wgutermtracker.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class AppBroadcastReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_TITLE = "Notification Title";
    public static final String NOTIFICATION_MESSAGE = "Notification Message";
    public static final String NOTIFICATION_ID = "Notification ID";
    public static final String NOTIFICATION_CHANNEL_ID = "Notification Channel ID";
    public static final String NOTIFICATION_CHANNEL_NAME = "Notification Channel NAME";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "You picked today!", Toast.LENGTH_SHORT).show();
        String title = intent.getStringExtra(NOTIFICATION_TITLE);
        String message = intent.getStringExtra(NOTIFICATION_MESSAGE);
        String channelId = intent.getStringExtra(NOTIFICATION_CHANNEL_ID);
        String channelName = intent.getStringExtra(NOTIFICATION_CHANNEL_NAME);
        int notificationId = intent.getIntExtra(NOTIFICATION_ID,-1);
        NotificationHelper notificationHelper = new NotificationHelper(context,channelId,channelName);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(title,message,channelId);
        notificationHelper.getManager().notify(notificationId,nb.build());
    }
}
