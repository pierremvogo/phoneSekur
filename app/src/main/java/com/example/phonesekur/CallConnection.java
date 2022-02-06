package com.example.phonesekur;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.telecom.CallAudioState;
import android.telecom.Connection;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.motion.widget.MotionController;

@RequiresApi(api = Build.VERSION_CODES.M)
public class CallConnection extends Connection {
    Context context;
    String TAG = "CallConnection";
    PendingIntent pendingIntent;
    Notification.Builder builder;

    public CallConnection(Context applicationContext) {
        this.context = applicationContext;
    }

    public void onShowIncomingCallUi(){
        super.onShowIncomingCallUi();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        intent.setClass(context,  MainActivity.class);
        pendingIntent.getActivity(context,1,intent,0);
        builder = new Notification.Builder(context);
        builder.setOngoing(true);
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("My Incomming Call");
        builder.setContentText("Call content");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.notify("Call Notification",37,builder.build());

    }
    public void onCallAudioStateChanged(CallAudioState audioState){
        System.out.println("onCallAudioStateChanged");
    }
    public void onAnswer(){
        System.out.println("onAnswer");
    }
    public void onDisconnect(){
        System.out.println("onDisconnect");
    }
    public void onHold(){
        System.out.println("onHold");
    }
    public void onUnhold(){
        System.out.println("onUnhold");
    }
    public void onReject(){
        System.out.println("onReject");
    }
}
