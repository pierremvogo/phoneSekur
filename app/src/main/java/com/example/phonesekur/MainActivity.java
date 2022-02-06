package com.example.phonesekur;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telecom.VideoProfile;
import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.phonesekur.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    TextView notif;
    //private Socket mSocket;
    public Switch switch_active;
    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User user = new User("mvogo","mvogo@gmail.com","+237674991248","25689","Cameroun", Arrays.asList("+237697094438","+237674991248"));
        TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
        super.onCreate(savedInstanceState);
        /*firebaseFirestore.collection("users")
                .add(user)
                .addOnSuccessListener(documentReference ->
                        Toast.makeText(MainActivity.this, "User Successfully writen with Id: "+documentReference.getId(), Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    System.out.println("-------------Exception for add collection "+ e.toString());
                    Toast.makeText(MainActivity.this, "Error ", Toast.LENGTH_SHORT).show();
                });*/
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       /* HttpGetRequest request = new HttpGetRequest();
        request.execute();*/

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
       //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.RECEIVE_SMS};
                    requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);

            }


            try {
                PhoneAccountHandle phoneAccountHandle = new PhoneAccountHandle(
                        new ComponentName(getApplicationContext().getPackageName(), callConnectionService.class.getName()), "phonesekur"
                );
                PhoneAccount phoneAccount = PhoneAccount.builder(phoneAccountHandle, "phonesekur")
                        .setCapabilities(PhoneAccount.CAPABILITY_SELF_MANAGED).build();
                telecomManager.registerPhoneAccount(phoneAccount);
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.example.phonesekur", "com.example.phonesekur.MainActivity"));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            }catch (SecurityException e){e.printStackTrace();}
        }

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("chan0","Fisrt channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }


        switch_active = (Switch)findViewById(R.id.switch_active_app);
        try {
            if(Infos.active == 1){
                switch_active.setChecked(true);
            }
            else{
                switch_active.setChecked(false);

            }

        }
        catch (Exception e){
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        try{
            switch_active.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(Infos.active == 0){
                    Infos.active = 1;
                    Toast.makeText(MainActivity.this, "Protection activée sur votre mobile", Toast.LENGTH_SHORT).show();
                }
                else{
                    Infos.active = 0;
                    Toast.makeText(MainActivity.this, "Attention!!! Protection désactivée!!", Toast.LENGTH_LONG).show();
                }

            });


        }catch (Exception e){
            Toast.makeText(MainActivity.this,"Methode 2  " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /*private boolean isNotificationServiceEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                "ENABLED_NOTIFICATION_LISTENERS");
        if(!TextUtils.isEmpty(flat)){
            final String[] names = flat.split(":");
            for (String name: names){
                final ComponentName componentName = ComponentName.unflattenFromString(name);
                if(componentName != null){
                    if(TextUtils.equals(pkgName,componentName.getPackageName())){
                        return true;
                    }
                }
            }
        }
        return false;
    }*/

    /*public class HttpGetRequest extends AsyncTask<Void,Void,String>{
        static final String REQUEST_METHOD = "GET";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            String inputLine = "";
            try{
                URL url = new URL(SERVER);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod(REQUEST_METHOD);
                urlConnection.setReadTimeout(READ_TIMEOUT);
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                urlConnection.connect();
                System.out.println("-------------------------------Successful connect--------------------------------");
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                while((inputLine = bufferedReader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                bufferedReader.close();
                inputStreamReader.close();
                result = stringBuilder.toString();
                System.out.println("------------Result-------------------");
                System.out.println(result);
            }catch (IOException e){e.printStackTrace(); result="Error";}

            return result;
        }*/



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission not granted", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }




}