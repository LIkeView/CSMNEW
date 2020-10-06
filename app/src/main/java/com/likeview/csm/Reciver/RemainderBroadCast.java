package com.likeview.csm.Reciver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.likeview.csm.Activity.ClientDetailActivity;

public class RemainderBroadCast extends BroadcastReceiver {

    private static final String Channelid = "CMS";

    @Override
    public void onReceive(Context context, Intent intent) {

        // Call MainActivity when notification is tapped.
        Intent mainIntent = new Intent(context, ClientDetailActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        // NotificationManager
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For API 26 and above
            CharSequence channelName = "My Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(Channelid, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }

        // Prepare Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Channelid)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("CMS")
                .setContentText("Hello")
                .setContentIntent(contentIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Notify
        notificationManager.notify(0, builder.build());

//       Intent mainIntent = new Intent(context,MainActivity.class);
//       PendingIntent contentIntent= PendingIntent.getActivity(context,0,mainIntent,0);
//
//
//
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(context, Channelid)
//                        .setSmallIcon(R.drawable.ic_baseline_message_24)
//                        .setContentTitle("Notifications Example")
//                        .setContentText("This is a test notification")
//                        .setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
//        manager.notify(0, builder.build());
    }
}
