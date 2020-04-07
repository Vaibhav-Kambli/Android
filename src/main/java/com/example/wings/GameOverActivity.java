package com.example.wings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {


    // play again button
    private Button playAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playAgain = (Button) findViewById(R.id.play_btn);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on click return to main activity
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                // start the activity
                startActivity(intent);
            }
        });
    }
}
