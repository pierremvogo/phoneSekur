package com.example.phonesekur;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telecom.VideoProfile;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.lang.reflect.Method;

public class callReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony =  (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        phoneCallStateListener customPhoneListener = new phoneCallStateListener(context,intent);
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //Toast.makeText(context, "--Audio MODE--"+audioManager.getMode(), Toast.LENGTH_LONG).show();

        if(Infos.active == 1 && intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
           //Toast.makeText(context, "----------Incoming SMS------------", Toast.LENGTH_LONG).show();
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String msg_from;
            if(bundle != null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        sendNotification(msgBody,context);
                        Infos.smsToBlock = "Reject Sms: " + "("+msgBody+")" + "  From: " + msg_from;
                        Toast.makeText(context, "From: " + msg_from + ", Body:  " + (msgBody), Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
       /* if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            showToast(context,number + "  start call ........");
        } else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
            showToast(context,"call Ended........");
        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            showToast(context,"Incomming call ........");
        }*/
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startOutgoigCall(Context context) {
        Bundle extras = new Bundle();
        extras.putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, true);
        TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
        PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(new ComponentName(context.getApplicationContext().getPackageName(), callConnectionService.class.getName()),"phonesekur");
        Bundle test = new Bundle();
        test.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE,phoneAccountHandle);
        test.putInt(TelecomManager.EXTRA_START_CALL_WITH_VIDEO_STATE, VideoProfile.STATE_BIDIRECTIONAL);
        test.putParcelable(TelecomManager.EXTRA_OUTGOING_CALL_EXTRAS, extras);
        System.out.println("Before stop call :  "+test.toString());
        Toast.makeText(context, "Before stop call :  "+test.toString(), Toast.LENGTH_LONG).show();
        try{

            telecomManager.placeCall(Uri.parse("tel:$number"), test);
            Toast.makeText(context, "Telecom Manager:  "+telecomManager.getLine1Number(phoneAccountHandle), Toast.LENGTH_LONG).show();
        }catch (SecurityException e){e.printStackTrace();}

    }
    public void sendNotification(String message, Context context){
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context,"chan0")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle(Infos.titleMessageNotification)
                .setOngoing(true)
                .setSound(null)
                .setContentText(message);
        Notification notification = notificationCompat.build();
        NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,notification);
    }

}
