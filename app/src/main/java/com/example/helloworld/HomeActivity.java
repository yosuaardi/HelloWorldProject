package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.helloworld.utils.BroadcastReceiverClass;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {
    private TabItem tab1;
    private TabItem tab2;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tab1 = findViewById(R.id.tabItem1);
        tab2 = findViewById(R.id.tabItem2);
        tabLayout = findViewById(R.id.tabs);
        String getExtra = getIntent().getStringExtra("COBA_INTENT_EXTRA");
        BroadcastReceiverClass bcReceiver = new BroadcastReceiverClass();
        IntentFilter filter = new IntentFilter(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
        this.registerReceiver(bcReceiver, filter);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPlaceholder, new FragmentTop());
        fragmentTransaction.commit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentPlaceholder, new FragmentTop());
                    fragmentTransaction.commit();
                    Log.d("success", "Tab 1");
                }else if(tab.getPosition() == 1){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentPlaceholder, new FragmentBottom());
                    fragmentTransaction.commit();
                    Log.d("success", "Tab 2");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270){
            Log.d("Success", "onResume: Landscape");
        }else{
            Log.d("Success", "onResume: Potrait");
        }
    }

//    private void onNotificationReceive(Context context, String status){
//        String CHANNEL_ID = "MY_NOTIF_CHANNEL";
//        NotificationChannel myChannel = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            myChannel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager notifcationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notifcationManager.createNotificationChannel(myChannel);
//        }
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentTitle("Wifi Connection")
//                .setContentText(status)
//                .build();
//
//        int notificationID = 0;
//        notificationManager.notify(notificationID, notification);
//    }
}