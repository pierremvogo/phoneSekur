package com.example.phonesekur;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class notificationStateListener extends NotificationListenerService {
    private Context context;
    private Intent intent;
    public static final String TAG = "NotificationListener";
    public static final String WA_PACKAGE = "com.whatsapp";

    public void onListenerConnected(){
        Toast.makeText(context, "Notification Listener Connected", Toast.LENGTH_LONG).show();
    }
    public void onNotificationPosted(StatusBarNotification statusBarNotification){
        if(!statusBarNotification.getPackageName().equals(WA_PACKAGE)){
            return;
        }else{
            Notification notification = statusBarNotification.getNotification();
            Bundle bundle = notification.extras;
            String from = bundle.getString(NotificationCompat.EXTRA_TITLE);
            String message = bundle.getString(NotificationCompat.EXTRA_TEXT);

            Toast.makeText(context, "Message FROM ("+from+") "+"Body ("+message+")", Toast.LENGTH_LONG).show();
        }


    }
}
