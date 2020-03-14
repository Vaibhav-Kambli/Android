package com.example.wings;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

public class BirdView extends View {

    // Variable Declaration

    private int scoreCount = 0;

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


    public BirdView(Context context)
    {
        super(context);

        // Background image for the game
        background = BitmapFactory.decodeResource(getResources(), R.drawable.sky);

        // Bird image
        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.bird11);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.bird22);

        // Yellow points ball
        pointsBallPaint.setColor(Color.YELLOW);

        // Green life ball
        lifeBallPaint.setColor(Color.GREEN);

        // Score formatting
        score.setTextSize(60);
        score.setColor(Color.BLUE);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        pointsBallPaint.setAntiAlias(false);
        lifeBallPaint.setAntiAlias(false);

        // Health bars for bird's health
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart11);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart22);
        life[2] = BitmapFactory.decodeResource(getResources(), R.drawable.heart33);

        // position of the bird on the Y axis
        birdPosY = 500;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background image on canvas
        canvas.drawBitmap(background, 0, 0, null);

        screenWidth = canvas.getWidth();

        screenHeight = canvas.getHeight();

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

        if(tap)
        {
            canvas.drawBitmap(bird[1], birdPosX, birdPosY, null  );
            tap = false;
        }
        else
        {
            canvas.drawBitmap(bird[0], birdPosX, birdPosY, null);
        }


        // Points ball
        pointsBallX = pointsBallX - pointsBallSpeed;

        // if collision with yellow point ball then increment score by 10 points
        if(testCollision(pointsBallX, pointsBallY))
        {
            scoreCount = scoreCount + 10;

            pointsBallX = -100;
        }


        if(pointsBallX < 0)
        {
            pointsBallX = screenWidth + 20;

            pointsBallY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }

        canvas.drawCircle(pointsBallX, pointsBallY, 20, pointsBallPaint);

        // Life ball
        lifeBallX = lifeBallX - lifeBallSpeed;

        // if collision with yellow point ball then increment score by 10 points
        if(testCollision(lifeBallX, lifeBallY))
        {
            scoreCount = scoreCount + 20;


            lifeBallX = -100;
        }


        if(lifeBallX < 0)
        {

            lifeBallX = screenWidth + 20;

            lifeBallY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }

        canvas.drawCircle(lifeBallX, lifeBallY, 30, lifeBallPaint);


        canvas.drawText("Score : " + scoreCount, 20, 60, score);

        canvas.drawBitmap(life[0], 800, 10, null);
    }

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
        return  true;
    }
}
