package com.example.helloworld;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Job started");
        doBackground(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }

    private void doBackground(final JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Handler myHandler = new Handler(getMainLooper());
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Succes", "Test Job Scheduler");
                            Toast.makeText(getApplicationContext(), "Test!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (jobCancelled) {
                        //break;
                        return;
                    }
                    try {
                        Thread.sleep(3000);
                        Log.d(TAG, "run: 3 detik");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
