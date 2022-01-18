package com.example.phonesekur;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

public class phoneCallStateListener extends PhoneStateListener {
    private Context context;
    public phoneCallStateListener(Context context){
        this.context = context;
    }

        @Override
        public void onCallStateChanged(int state, String incomingNumber){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    String blockNumber = prefs.getString("blockNumber", "123");
                    Toast.makeText(context, "PREFS --------"+prefs.getString("blockNumber", null), Toast.LENGTH_LONG).show();
                    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setStreamMute(AudioManager.STREAM_RING,true);
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    try{
                        Toast.makeText(context, "in  "+blockNumber, Toast.LENGTH_LONG).show();
                        Class clazz = Class.forName(telephonyManager.getClass().getName());
                        Method method = clazz.getDeclaredMethod("getITelephony");
                        method.setAccessible(true);
                        ITelephony telephonyService = (ITelephony) method.invoke((telephonyManager));
                        System.out.println("Call "+blockNumber);
                        if( Infos.active == 1 && (
                                incomingNumber.equalsIgnoreCase("+237697094438") || incomingNumber.equalsIgnoreCase("697094438")||
                                incomingNumber.equalsIgnoreCase("+237698114902") || incomingNumber.equalsIgnoreCase("698114902")||
                                incomingNumber.equalsIgnoreCase("+237697399772") || incomingNumber.equalsIgnoreCase("697399772")
                                )){
                            telephonyService = (ITelephony) method.invoke(telephonyManager);
                            telephonyService.silenceRinger();
                            System.out.println("in  "+blockNumber);
                            telephonyService.endCall();
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
                    String blockNumbero = prefs.getString("blockNumber", "123");
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
                        if( Infos.active == 1 && (
                                        incomingNumber.equalsIgnoreCase("+237697094438") || incomingNumber.equalsIgnoreCase("697094438")||
                                        incomingNumber.equalsIgnoreCase("+237698114902") || incomingNumber.equalsIgnoreCase("698114902")||
                                        incomingNumber.equalsIgnoreCase("+237697399772") || incomingNumber.equalsIgnoreCase("697399772")
                        )){
                            telephonyService = (ITelephony) method.invoke(telephonyManagero);
                            telephonyService.silenceRinger();
                            System.out.println("in  "+blockNumbero);
                            telephonyService.endCall();
                            Toast.makeText(context, "Call Ended", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Attention!!! Appel suspect!!!", Toast.LENGTH_LONG).show();
                        }



                    }catch (Exception err){
                        Toast.makeText(context, err.toString(), Toast.LENGTH_LONG).show();
                    }


            }
            super.onCallStateChanged(state, incomingNumber);
        }

}
