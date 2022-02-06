package com.example.phonesekur;

import android.net.Uri;
import android.os.Build;
import android.telecom.Call;
import android.telecom.CallScreeningService;
import android.telecom.Connection;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ScreeningService extends CallScreeningService {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onScreenCall(@NonNull Call.Details details) {
            boolean isIncoming = details.getCallDirection() == Call.Details.DIRECTION_INCOMING;
            if(isIncoming){
                Uri handle = details.getHandle();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    switch (details.getCallerNumberVerificationStatus()){
                        case Connection
                                .VERIFICATION_STATUS_FAILED:
                            Toast.makeText(this, "VERIFICATION FAILED", Toast.LENGTH_SHORT).show();
                            break;
                        case Connection.
                                VERIFICATION_STATUS_PASSED:
                            Toast.makeText(this, "VERIFICATION PASSED", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(this, "STATUS NOT VERIFIED", Toast.LENGTH_SHORT).show();



                    }
                }
            }

    }
}
