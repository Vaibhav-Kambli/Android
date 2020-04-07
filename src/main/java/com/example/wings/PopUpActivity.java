package com.example.wings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                try{
                    sleep( 7000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(PopUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        thread1.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
