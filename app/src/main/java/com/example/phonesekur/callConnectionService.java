package com.example.phonesekur;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public class callConnectionService extends ConnectionService {

    public Connection onCreateOutgoingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest request){
        System.out.println("here is CallConnectionService outgoing...");

        Toast.makeText(this, "here is CallConnectionService outgoing...", Toast.LENGTH_LONG).show();
        CallConnection callConnection = new CallConnection(getApplicationContext());
        callConnection.setAddress(request.getAddress(), TelecomManager.PRESENTATION_ALLOWED);
        callConnection.setInitializing();
        callConnection.setActive();
        return callConnection;
    }

    public void onCreateOutgoingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest request){
        super.onCreateOutgoingConnectionFailed(phoneAccountHandle,request);
        System.out.println("Outgoing call Failed");
        Toast.makeText(this, "Outgoing call Failed", Toast.LENGTH_LONG).show();
    }

    public Connection onCreateIncomingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest request){
        System.out.println("here is CallConnectionService incomming...");
        Toast.makeText(this, "here is CallConnectionService incomming...", Toast.LENGTH_LONG).show();
        CallConnection callConnection = new CallConnection(getApplicationContext());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1){
            callConnection.setConnectionProperties(Connection.PROPERTY_SELF_MANAGED);
        }
        callConnection.setCallerDisplayName("Test Call", TelecomManager.PRESENTATION_ALLOWED);
        callConnection.setAddress(request.getAddress(), TelecomManager.PRESENTATION_ALLOWED);
        callConnection.setInitializing();
        callConnection.setActive();
        return callConnection;
    }

    public void onCreateIncomingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest request){
        super.onCreateIncomingConnectionFailed(phoneAccountHandle,request);
        System.out.println("Incoming call Failed");
        Toast.makeText(this, "Incoming call Failed", Toast.LENGTH_LONG).show();
    }


}
