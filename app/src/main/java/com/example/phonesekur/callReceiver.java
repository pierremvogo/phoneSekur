package com.example.phonesekur;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import java.lang.reflect.Method;

public class callReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony =  (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        phoneCallStateListener customPhoneListener = new phoneCallStateListener(context);
        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            showToast(context,number + "  start call ........");
        } else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
            showToast(context,"call Ended........");
        } else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            showToast(context,"Incomming call ........");
        }
    }

    void showToast (Context context, String message) {
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
