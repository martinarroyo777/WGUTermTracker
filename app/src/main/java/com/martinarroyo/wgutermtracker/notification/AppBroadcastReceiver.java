package com.martinarroyo.wgutermtracker.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AppBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO add notification builder
        Toast.makeText(context, "You picked today!", Toast.LENGTH_SHORT).show();

    }
}
