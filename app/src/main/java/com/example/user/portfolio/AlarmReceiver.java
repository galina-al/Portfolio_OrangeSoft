package com.example.user.portfolio;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.example.user.portfolio.Activity.NoticeActivity;

import static com.example.user.portfolio.util.CONSTANTS.CONTENT_TEXT;

public class AlarmReceiver extends BroadcastReceiver {
    private NoticeActivity activity;

    public AlarmReceiver(NoticeActivity activity) {
        this.activity = activity;
    }

    public AlarmReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getExtras() != null) {
            String contentText = intent.getStringExtra(CONTENT_TEXT);
            Log.d("CONTENT_TEXT", contentText);
            createNotification(context, contentText);
        } else {
            createNotification(context, "///");
            Log.d("CONTENT_TEXT", "NULL");
        }

    }

    public void createNotification(Context context, String contentText) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.support.v4.R.drawable.notification_icon_background)
                        .setContentTitle("Notice")
                        .setContentText(contentText);
        Intent resultIntent = new Intent(context, NoticeActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NoticeActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        Notification note = mBuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, note);
    }
}