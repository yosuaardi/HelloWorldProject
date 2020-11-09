package com.example.helloworld;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
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
    private Button btnStart = null;
    private Button btnStop = null;
    private Button btnLogout = null;

    public static final String KEY_EMAIL = "KEY_EMAIL";
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
        btnStart = findViewById(R.id.btnStartJob);
        btnStop = findViewById(R.id.btnStopJob);
        btnLogout = findViewById(R.id.btnLogout);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    scheduleJob();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cancelJob();
                }
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
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

    public void scheduleJob(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ComponentName comp = new ComponentName(this, MyJobService.class);

            JobInfo jobInfo = new JobInfo.Builder(123, comp)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setPersisted(true)
                    .setPeriodic(15 * 60 * 1000)
                    .build();

            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            int resultCode = jobScheduler.schedule(jobInfo);
            if(resultCode == JobScheduler.RESULT_SUCCESS){
                Log.d("Success", "Job Success");
            }else{
                Log.d("Failed", "Job Failed");
            }
        }
    }

    public void cancelJob(){
        JobScheduler jobScheduler = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            jobScheduler.cancel(123);
            Log.d("Success", "Job Cancelled");
        }
    }

    public void logout(){
        SharedPreferences.Editor editor = LoginActivity.sharedPreferences.edit();
        editor.remove(KEY_EMAIL);
        editor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("STATUS_LOGOUT", "Berhasil");
        startActivity(intent);
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