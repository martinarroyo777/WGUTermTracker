package com.martinarroyo.wgutermtracker.notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.martinarroyo.wgutermtracker.R;

public class NotificationHelper extends ContextWrapper {

    //public static final String channelID = "channelID";
    //public static final String channelName = "Channel Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base, String channelId, String channelName) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel(channelId,channelName);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel(String channelId, String channelName) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message, String channelId) {

        return new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_alert_icon);
    }
}
