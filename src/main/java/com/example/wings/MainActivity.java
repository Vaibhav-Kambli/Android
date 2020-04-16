package com.example.wings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private BirdView birdView;
    private Handler handler = new Handler();
    private final static long Interval = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // once created, render the BirdView layout on screen
        birdView = new BirdView(this);
        setContentView(birdView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        birdView.invalidate();
                    }
                });
            }
        }, 0, Interval);
    }
}
