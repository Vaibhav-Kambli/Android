package com.example.wings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;



public class BirdView extends View {

    // Variable Declaration

    // set initial score
    private int scoreCount = 0;

    // set initial health count of the bird
    private int healthCount = 3;

    // bitmap for birds
    private Bitmap bird[] = new Bitmap[2];

    // background image bitmap
    private Bitmap background;

    // set initial user tap to false
    private boolean tap = false;

    // bitmap image for health icons
    private Bitmap life[] = new Bitmap[2];

    private Paint score = new Paint();

    // set initial X coordinate of bird
    private int birdPosX = 10;

    // Y coordinate of bird
    private int birdPosY;

    // speed of the bird
    private int speed;

    // dimension variables
    private int screenWidth;
    private int screenHeight;


    // Declarations for points ball
    private int pointsBallX;
    private int pointsBallY;
    private int pointsBallSpeed = 15;

    private Paint pointsBallPaint = new Paint();

    // Declarations for life ball
    private int lifeBallX;
    private int lifeBallY;
    private int lifeBallSpeed = 20;

    private Paint lifeBallPaint = new Paint();

    private Paint redBallPaint = new Paint();

    // declarations for bomb
    private Bitmap bomb[] = new Bitmap[1];  // newly added
    private int bombPosX = 0;
    private int bombPosY;
    private int bombSpeed = 15;



    // constructor
    public BirdView(Context context)
    {
        super(context);

        // get background image for the game from drawable
        background = BitmapFactory.decodeResource(getResources(), R.drawable.sky1);

        // get bird image from drawable
        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird11);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird22);

        // get bomb image from drawable
        bomb[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bomb1);

        // Yellow points ball
        pointsBallPaint.setColor(Color.YELLOW);

        // Green life ball
        lifeBallPaint.setColor(Color.GREEN);

        redBallPaint.setColor(Color.RED);
        redBallPaint.setAntiAlias(false);

        // Score formatting
        score.setTextSize(60);
        score.setColor(Color.WHITE);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        pointsBallPaint.setAntiAlias(false);
        lifeBallPaint.setAntiAlias(false);

        // Health bars for bird's health
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.grey_heart);

        // initial position of the bird on the Y axis
        birdPosY = 500;

        // initial position of the bomb
        bombPosY = -screenHeight;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background image on canvas
        canvas.drawBitmap(background, 0, 0, null);

        // dimension variables
        screenWidth = canvas.getWidth();
        screenHeight = canvas.getHeight();


        // min and max height of the bird
        int minY = bird[0].getHeight();
        int maxY = screenHeight - bird[0].getHeight() * 2;


        birdPosY = birdPosY + speed;

        if(birdPosY < minY)
        {
            birdPosY = minY;
        }

        if(birdPosY > maxY)
        {
            birdPosY = maxY;
        }
        speed = speed + 5;

        // when user taps on screen
        if(tap)
        {

            // draw 2nd bitmap to illustrate bird flapping
            canvas.drawBitmap(bird[1], birdPosX, birdPosY, null  );

            // set tap value to false
            tap = false;
        }
        else
        {
            canvas.drawBitmap(bird[0], birdPosX, birdPosY, null);
        }


        // send point ball towards the bird
        pointsBallX = pointsBallX - pointsBallSpeed;

        // if collided with the bird
        if(testCollision(pointsBallX, pointsBallY))
        {
            // increment score by 10 points
            scoreCount = scoreCount + 10;

            pointsBallX = -100;
        }

        // if points ball is of the screen
        if(pointsBallX < 0)
        {
            // set X coordinate of points ball
            pointsBallX = screenWidth + 20;

            // set Y coordinate randomly of points ball
            pointsBallY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }

        // draw points ball
        canvas.drawCircle(pointsBallX, pointsBallY, 20, pointsBallPaint);

        // Moves Life ball towards bird
        lifeBallX = lifeBallX - lifeBallSpeed;

        // if collided with the bird
        if(testCollision(lifeBallX, lifeBallY))
        {
            // increase score count by 20
            scoreCount = scoreCount + 20;

            // set x coordinate for the life ball
            lifeBallX = -100;
        }

        // if life ball is off the screen
        if(lifeBallX < 0)
        {
            // set X coordinate of life ball
            lifeBallX = screenWidth + 20;

            // set Y coordinate randomly of life ball
            lifeBallY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }

        // Draw life ball
        canvas.drawCircle(lifeBallX, lifeBallY, 30, lifeBallPaint);


        // send bomb towards the bird
        bombPosX = bombPosX - bombSpeed;

        // set min and max Y coordinates of the bomb
        int bombMinY = bomb[0].getWidth();
        int bombMaxY = screenHeight - bomb[0].getHeight() * 2;


        // if collided with the bird
        if(testCollision(bombPosX, bombPosY))
        {


            healthCount = healthCount -1;
            // set X coordinate outside the screen of the bomb
            bombPosX = -100;

            // Check if the health is 0
            if(healthCount == 0){

                // Pop up Game Over message
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                // Display Game Over page
                Intent gameOver = new Intent(getContext(), GameOverActivity.class);

                // set intent flag to clear any existing task associated with the activity and create a new activity
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // start activity
                getContext().startActivity(gameOver);
            }

        }

        // if bomb is off the screen
        if(bombPosX < 0) {
            // calculate X coordinate of the Bomb
            bombPosX = screenWidth + 20;

            // calculate Y coordinate randomly of the Bomb
            bombPosY = (int) Math.floor(Math.random() * (bombMaxY - bombMinY)) + bombMinY;  // set random Y values for bomb
        }

        // display bomb
        canvas.drawBitmap(bomb[0], bombPosX, bombPosY, null);

        // display score text
        canvas.drawText("Score : " + scoreCount, 30, 60, score);

        for(int i = 0; i < 3; i++){
            // set x coordinate for the health icons
            int xPos = (int) (680 + life[0].getWidth() * 1.1 * i);

            // set x coordinate for the health icons
            int yPos = 20;

            // if bird's health is > 1
            if( i < healthCount){
                // draw red hearts at X and Y coordinate
                canvas.drawBitmap(life[0], xPos, yPos, null);
            }
            else{

                // draw grey heart at X and Y coordinate
                canvas.drawBitmap(life[1], xPos, yPos, null);
            }
        }

    }

    // test for collision with bird
    public boolean testCollision(int x, int y)
    {
        if (birdPosX < x && x < (birdPosX + bird[0].getWidth()) && birdPosY < y && y < (birdPosY + bird[0].getHeight()) )
        {
            return true;
        }
        return false;
    }

    // event for when user taps on the screen
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            // set tap value to true
            tap = true;

            speed = -40;
        }
        return true;
    }
}
