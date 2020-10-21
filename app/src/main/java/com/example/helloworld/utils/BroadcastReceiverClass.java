package com.example.helloworld.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.helloworld.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class BroadcastReceiverClass extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = getConnectivityStatus(context);
        onNotificationReceive(context, status);
        Toast.makeText(context, "Broadcast Receiver", Toast.LENGTH_LONG);
    }

    public String getConnectivityStatus(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return "Connected";
        }
        return "Not Connected";
    }

    private void onNotificationReceive(Context context, String status){
        String CHANNEL_ID = "MY_NOTIF_CHANNEL";
        NotificationChannel myChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            myChannel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notifcationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notifcationManager.createNotificationChannel(myChannel);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Wifi Connection")
                .setContentText(status)
                .build();

        int notificationID = 0;
        notificationManager.notify(notificationID, notification);
    }
}
