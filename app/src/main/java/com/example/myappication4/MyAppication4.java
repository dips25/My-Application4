package com.example.myappication4;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;

public class MyAppication4 extends MultiDexApplication {

    public static final String NOTIFICATION_CHANNEL_NAME = "PostNotification";
    public static final String NOTIFICATION_CHANNEL_ID = "PostChannelId";
    public static final String NOTIFICATION_CHANNEL_DESCRIPTION = "PostNotification";


    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        FirebaseApp.initializeApp(getApplicationContext());

        createNotificationChannel();


    }

    public void createNotificationChannel() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID , NOTIFICATION_CHANNEL_NAME , NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(NOTIFICATION_CHANNEL_DESCRIPTION);

            notificationManager.createNotificationChannel(notificationChannel);


        }


    }
}
