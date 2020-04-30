package com.example.wings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    // play again button
    private Button play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // set id for play button
        play = findViewById(R.id.play_btn1);

        // when play button is clicked
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on click return to main activity
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                // start the activity
                startActivity(intent);
            }
        });

    }


}
