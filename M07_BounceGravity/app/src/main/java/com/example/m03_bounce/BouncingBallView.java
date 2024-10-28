package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View implements SensorEventListener {


    private ArrayList<Ball> balls = new ArrayList<Ball>(); // list of Balls
    private Ball ball_1;  // use this to reference first ball in arraylist
    private Box box;

    private TextView scoreView;


    private EnemyBall enemyBall;


    private boolean powerUpUsedatCurrentScore;

    private CopyOnWriteArrayList<EnemyBall> enemyBalls = new CopyOnWriteArrayList<>();

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;

    int canvasWidth = getWidth();
    int canvasHeight = getHeight();
    double ax = 0;   // Store here for logging to screen
    double ay = 0;   //
    double az = 0;   //
    private Button powerUpButton;



    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Random rand = new Random();
        Log.v("BouncingBallView", "Constructor BouncingBallView");
        // create the box
        box = new Box(Color.BLACK);  // ARGB
        // Initialize enemyBall properly
        enemyBall = new EnemyBall(Color.RED, rand.nextInt(800), rand.nextInt(800), 2, "enemy", this);
        enemyBalls.add(enemyBall); // Add it to the list if needed
        //ball_1 = new Ball(Color.BLUE);
        balls.add(new Ball(Color.BLUE, "hero"));
        ball_1 = balls.get(0);  // points ball_1 to the first; (zero-ith) element of list
        Log.w("BouncingBallLog", "Just added a bouncing ball");

        //ball_2 = new Ball(Color.CYAN);
        balls.add(new Ball(Color.CYAN, "hero"));
        Log.w("BouncingBallLog", "Just added another bouncing ball");

        // To enable keypad
        this.setFocusable(true);
        this.requestFocus();
        // To enable touch mode
        this.setFocusableInTouchMode(true);
    }

    // Called back to draw the view. Also called after invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        box.draw(canvas);
        for (Ball b : balls) {
            b.draw(canvas);  //draw each ball in the list
            b.moveWithCollisionDetection(box, enemyBalls);  // Update the position of the ball
        }
        synchronized (enemyBalls){
            for (EnemyBall eb : enemyBalls){
                eb.draw(canvas);
            }
        }
        updateScoreDisplay();
        this.invalidate();
    }

    // Called back when the view is first created or its size changes.
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {
        // Set the movement bounds for the ball
        box.set(0, 0, w, h);
        Log.w("BouncingBallLog", "onSizeChanged w=" + w + " h=" + h);
    }

    // Touch-input handler
    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float deltaX, deltaY;
        float scalingFactor = 5f / ((box.xMax > box.yMax) ? box.yMax : box.xMax);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // Modify rotational angles according to movement
                deltaX = currentX - previousX;
                deltaY = currentY - previousY;
                ball_1.speedX += deltaX * scalingFactor;
                ball_1.speedY += deltaY * scalingFactor;
                //Log.w("BouncingBallLog", " Xspeed=" + ball_1.speedX + " Yspeed=" + ball_1.speedY);
                Log.w("BouncingBallLog", "x,y= " + previousX + " ," + previousY + "  Xdiff=" + deltaX + " Ydiff=" + deltaY);
                balls.add(new Ball(Color.BLUE, previousX, previousY, deltaX, deltaY, false, "hero"));  // add ball at every touch event

                // A way to clear list when too many balls
                if (balls.size() > 20) {
                    // leave first ball, remove the rest
                    Log.v("BouncingBallLog", "too many balls, clear back to 1");
                    balls.clear();

                    ball_1 = balls.get(0);  // points ball_1 to the first (zero-ith) element of list
                }

        }
        // Save current x, y
        previousX = currentX;
        previousY = currentY;

        // Try this (remove other invalidate(); )
        //this.invalidate();

        return true;  // Event handled
    }
    */


    @Override
    public void onSensorChanged(SensorEvent event) {
        //Log.v("onSensorChanged", "event=" + event.toString());
        // Lots of sensor types...get which one, unpack accordingly
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            ax = -event.values[0];  // turns out x is backwards...on my screen?
            ay = event.values[1];   // y component of Accelerometer
            az = event.values[2];   // z component of Accelerometer
            for (Ball b : balls) {
                b.setAcc(ax, ay, az);  //draw each ball in the list
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.v("onAccuracyChanged", "event=" + sensor.toString());
    }
    public void callMe() {
        Log.v("xxxxx", "yyyyy");
//        this.callMe();
    }

    public void updateScoreDisplay() {
        scoreView.setText("Score: " + EnemyBall.getScoreCount());
    }

    public void enablePowerButton() {
        powerUpButton.setEnabled(true);
    }

    public void setScoreView(TextView scoreView){
        this.scoreView = scoreView;
    }

    public void addEnemyBall(){
        Random rand = new Random();
        enemyBalls.add(new EnemyBall(Color.RED, rand.nextInt(800), rand.nextInt(800), 2, "enemy", this));
    }

    public void powerUpButtonPressed(){
        balls.add(new Ball(Color.GREEN, "hero"));
    }

    public void setPowerUpButton(Button button){
        this.powerUpButton = button;
    }
}
