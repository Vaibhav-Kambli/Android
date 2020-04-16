package com.example.wings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    // Text view to display score
    private TextView highScore;

    // play again button
    private Button playAgain;

    // variable to hold score value
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        playAgain = findViewById(R.id.play_btn);

        highScore = findViewById(R.id.highScore);

        // get passed value of score from the intent
        score = getIntent().getExtras().get("High Score").toString();

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on click return to main activity
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                // start the activity
                startActivity(intent);
            }
        });

        // display score
        highScore.setText("Your Score " + score);
    }
}
