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

    private int scoreCount = 0;

    private int healthCount = 3;

    private Bitmap bird[] = new Bitmap[2];

    private Bitmap background;

    private boolean tap = false;

    private Bitmap life[] = new Bitmap[3];

    private Paint score = new Paint();

    private int birdPosX = 10;

    private int birdPosY;

    private int speed;

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
            canvas.drawBitmap(bird[1], birdPosX, birdPosY, null  ); // draw 2nd bitmap to illustrate bird flapping
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
            scoreCount = scoreCount + 10; // increment score by 10 points

            pointsBallX = -100;
        }

        // if points ball is of the screen
        if(pointsBallX < 0)
        {
            pointsBallX = screenWidth + 20;

            pointsBallY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;   // set random Y position
        }

        canvas.drawCircle(pointsBallX, pointsBallY, 20, pointsBallPaint);   // draw points ball

        // Moves Life ball towards bird
        lifeBallX = lifeBallX - lifeBallSpeed;

        // if collided with the bird
        if(testCollision(lifeBallX, lifeBallY))
        {
            scoreCount = scoreCount + 20;   // increase score count by 20

            lifeBallX = -100;
        }

        // if life ball is off the screen
        if(lifeBallX < 0)
        {
            lifeBallX = screenWidth + 20;

            lifeBallY = (int) Math.floor(Math.random() * (maxY - minY)) + minY; // set random Y value for life ball
        }

        canvas.drawCircle(lifeBallX, lifeBallY, 30, lifeBallPaint);     // draw life ball


        // send bomb towards the bird
        bombPosX = bombPosX - bombSpeed;

        // min and max Y values of the bomb
        int bombMinY = bomb[0].getWidth();
        int bombMaxY = screenHeight - bomb[0].getHeight() * 2;


        // if collided with the bird
        if(testCollision(bombPosX, bombPosY))
        {


            healthCount = healthCount -1;
            bombPosX = -100;

            if(healthCount == 0){
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent gameOver = new Intent(getContext(), GameOverActivity.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(gameOver);
            }

        }

        // if bomb is off the screen
        if(bombPosX < 0) {
            bombPosX = screenWidth + 20;

            bombPosY = (int) Math.floor(Math.random() * (bombMaxY - bombMinY)) + bombMinY;  // set random Y values for bomb
        }

        // display bomb
        canvas.drawBitmap(bomb[0], bombPosX, bombPosY, null);

        // display score text
        canvas.drawText("Score : " + scoreCount, 30, 60, score);

        for(int i = 0; i < 3; i++){
            int xPos = (int) (680 + life[0].getWidth() * 1.1 * i);
            int yPos = 30;

            if( i < healthCount){
                canvas.drawBitmap(life[0], xPos, yPos, null);
            }
            else{
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
            tap = true;

            speed = -40;
        }
        return true;
    }
}
