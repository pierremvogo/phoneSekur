package com.example.phonesekur;
import android.Manifest;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telecom.Connection;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telecom.VideoProfile;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import com.android.internal.telephony.ITelephony;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.net.URISyntaxException;

@RequiresApi(api = Build.VERSION_CODES.M)
public class phoneCallStateListener extends PhoneStateListener {
    private Context context;
    private Socket mSocket;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent intent;
    MainActivity mainActivity;
    String number;
    String incoming_Number;
    public phoneCallStateListener(Context context, Intent intent){
        this.context = context;
        this.intent = intent;
    }

        @Override
        public void onCallStateChanged(int state, String incomingNumber){
            super.onCallStateChanged(state, incomingNumber);
            db.collection("users")
                    .get()
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                System.out.println(documentSnapshot.getId() + " => "+ documentSnapshot.getData().toString());
                                Toast.makeText(context, documentSnapshot.getId() + " => "+ documentSnapshot.getData(), Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(context, "Error getting data", Toast.LENGTH_LONG).show();
                        }
                    });

            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
           // Toast.makeText(context, "Incoming SMS or Call: " + incomingNumber, Toast.LENGTH_LONG).show();
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    incoming_Number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    String blockNumber = prefs.getString("blockNumber", null);
                    Toast.makeText(context, "Incoming Call has been rejected:", Toast.LENGTH_LONG).show();

                    audioManager.setStreamMute(AudioManager.STREAM_RING,true);
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    try{
                        Class clazz = Class.forName(telephonyManager.getClass().getName());
                        Method method = clazz.getDeclaredMethod("getITelephony");
                        method.setAccessible(true);
                        ITelephony telephonyService = (ITelephony) method.invoke((telephonyManager));
                        //System.out.println("Call ------------------>>>>>>>" +""+blockNumber);
                        if( Infos.active == 1 && (
                                incoming_Number.equalsIgnoreCase("+237697094438") || incoming_Number.equalsIgnoreCase("697094438")||
                                incoming_Number.equalsIgnoreCase("+237698114902") || incoming_Number.equalsIgnoreCase("698114902")||
                                incoming_Number.equalsIgnoreCase("+237697399772") || incoming_Number.equalsIgnoreCase("697399772")||
                                incoming_Number.equalsIgnoreCase("+237674991248") || incoming_Number.equalsIgnoreCase("674991248")||
                                incoming_Number.equalsIgnoreCase("+237697591396") || incoming_Number.equalsIgnoreCase("697591396")
                                )){
                            telephonyService = (ITelephony) method.invoke(telephonyManager);
                            telephonyService.silenceRinger();
                           // System.out.println("in  "+blockNumber);
                            telephonyService.endCall();
                            Infos.phoneNumberBlock = incomingNumber;
                            Toast.makeText(context, "Call Ended", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Attention!!! Appel suspect!!!", Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception err){
                        Toast.makeText(context, err.toString(), Toast.LENGTH_LONG).show();
                    }
                   // audioManager.setStreamVolume(AudioManager.STREAM_RING,false,);
                    break;

                case PhoneStateListener.LISTEN_CALL_STATE:
                    Toast.makeText(context, "lISTEN CALL STATE", Toast.LENGTH_LONG).show();

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    //incomingNumber = number;
                    Toast.makeText(context, "---Call number--->"+number, Toast.LENGTH_LONG).show();
                    if(incomingNumber == ""){
                        Toast.makeText(context, "---Empty Call number--->"+incomingNumber, Toast.LENGTH_LONG).show();
                    }

                    if( Infos.active == 1 && (
                                    number.equalsIgnoreCase("+237697094438") || number.equalsIgnoreCase("697094438")||
                                    number.equalsIgnoreCase("+237698114902") || number.equalsIgnoreCase("698114902")||
                                    number.equalsIgnoreCase("+237697399772") || number.equalsIgnoreCase("697399772")||
                                    number.equalsIgnoreCase("+237674991248") || number.equalsIgnoreCase("674991248")||
                                    number.equalsIgnoreCase("+237697591396") || number.equalsIgnoreCase("697591396")
                    )){

                    String blockNumbero = prefs.getString("blockNumber", null);
                    Toast.makeText(context, "PREFS --------"+prefs.getString("blockNumber", null), Toast.LENGTH_LONG).show();
                    AudioManager audioManager0 = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager0.setStreamMute(AudioManager.STREAM_RING,true);
                    TelephonyManager telephonyManagero = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    try{

                        Toast.makeText(context, "in  "+blockNumbero, Toast.LENGTH_LONG).show();
                        Class clazz = Class.forName(telephonyManagero.getClass().getName());
                        Method method = clazz.getDeclaredMethod("getITelephony");
                        method.setAccessible(true);
                        ITelephony telephonyService = (ITelephony) method.invoke((telephonyManagero));
                        System.out.println("Call "+blockNumbero);

                            telephonyService = (ITelephony) method.invoke(telephonyManagero);
                            telephonyService.silenceRinger();
                            System.out.println("in  "+blockNumbero);
                            telephonyService.endCall();
                            Infos.phoneNumberBlock = "Reject Call From: " + number;
                            sendNotification(number,context);
                            Toast.makeText(context, "Call Ended", Toast.LENGTH_LONG).show();


                    }catch (Exception err){
                        Toast.makeText(context, err.toString(), Toast.LENGTH_LONG).show();
                    }
                    }else{
                        Toast.makeText(context, "Attention!!! Appel suspect!!!", Toast.LENGTH_LONG).show();
                    }


            }

        }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startOutgoigCall() {
        Bundle extras = new Bundle();
        extras.putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, true);
        TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
        PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(new ComponentName(context.getApplicationContext().getPackageName(), callConnectionService.class.getName()),"phonesekur");
        Bundle test = new Bundle();
        test.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE,phoneAccountHandle);
        test.putInt(TelecomManager.EXTRA_START_CALL_WITH_VIDEO_STATE, VideoProfile.STATE_BIDIRECTIONAL);
        test.putParcelable(TelecomManager.EXTRA_OUTGOING_CALL_EXTRAS, extras);
        Toast.makeText(context, "Before stop call :  "+telecomManager.getLine1Number(phoneAccountHandle), Toast.LENGTH_LONG).show();
        try{

           telecomManager.placeCall(Uri.parse("tel:$number"), test);
           Toast.makeText(context, "Telecom Manager:  "+telecomManager.getLine1Number(phoneAccountHandle), Toast.LENGTH_LONG).show();
        }catch (SecurityException e){e.printStackTrace();}

    }
/*
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startIncomingCall(String number) {
        Bundle extras = new Bundle();
        if(context.checkSelfPermission(Manifest.permission.MANAGE_OWN_CALLS) == PackageManager.PERMISSION_GRANTED){
            Uri uri = Uri.fromParts(PhoneAccount.SCHEME_TEL, number, null);
            extras.putParcelable(TelecomManager.EXTRA_INCOMING_CALL_ADDRESS, uri);
            extras.putParcelable(TelecomManager.EXTRA_PHONE_ACCOUNT_HANDLE,phoneAccountHandle);
            extras.putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, true);
            boolean isCallPermitted = false;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                try{
                    System.out.println("Test phoneAccount exception");
                    telecomManager.isIncomingCallPermitted(phoneAccountHandle);}
                catch (NullPointerException e){e.printStackTrace();}

            }else{
                isCallPermitted = true;
            }
            System.out.println("Is call permitted ? "+isCallPermitted);
            telecomManager.addNewIncomingCall(phoneAccountHandle,extras);
        }

    }*/


    public void sendNotification(String phoneNumber, Context context){
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context,"chan0")
                .setSmallIcon(android.R.drawable.stat_sys_phone_call)
                .setContentTitle(Infos.titleCallNotification)
                .setOngoing(true)
                .setSound(null)
                .setContentText("Numéro Malveillant "+"[ "+phoneNumber+" ]");
        Notification notification = notificationCompat.build();
        NotificationManagerCompat notificationManagerCompat =  NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(2,notification);
    }

}
