package com.example.m03_bounce;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View {

    private ArrayList<Ball> balls = new ArrayList<Ball>(); // list of Balls
    private Ball ball_1;  // use this to reference first ball in arraylist
    private Box box;
    private boolean ballCreated = false;

    //color bar
    public int currentColor = Color.RED;
    public int currentX = 0;
    public int currentY = 0;
    public double currentDX = 0;
    public double currentDY = 0;

    DBClass DBtest;  // class level declaration

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;

    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.v("BouncingBallView", "Constructor BouncingBallView");

        // create the box
        box = new Box(Color.BLACK);  // ARGB

        // Get from DB
        DBtest = new DBClass(context);
        initializeBalls();
        List<DataModel> ALL = DBtest.findAll();
        for (DataModel one : ALL) {
            Log.w("DataModel", "Item => " + one.toString());

        }

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
            b.moveWithCollisionDetection(box);  // Update the position of the ball
        }
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
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();
        float scalingFactor = 5000.0f / Math.max(box.xMax, box.yMax);
        float deltaX = 0, deltaY = 0; // Initialize deltas

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Reset ballCreated for a new touch event
                ballCreated = false;
                previousX = currentX;
                previousY = currentY;
                break;

            case MotionEvent.ACTION_MOVE:
                // Calculate deltas while moving
                deltaX = currentX - previousX; // Calculate delta based on previous position
                deltaY = currentY - previousY;

                // Update previous coordinates after calculating deltas
                previousX = currentX;
                previousY = currentY;

                float speedX = deltaX * scalingFactor;
                float speedY = deltaY * scalingFactor;
                if (!ballCreated) {
                    balls.add(new Ball(Color.BLUE, currentX, currentY, speedX, speedY));
                    DataModel newBall = new DataModel(0,(float)currentX,(float)currentY,(float)speedX,(float)speedY);
                    DBtest.save(newBall);
                    ballCreated = true; // Mark that a ball has been created
                    Log.v("TouchBall ADDED", "X: " + currentX + " Y: " + currentY + " SpeedX: " + speedX + " SpeedY: " + speedY);
                }
                // Optional: You can visualize the movement here if needed
                break;
            case MotionEvent.ACTION_UP:
                // Use the last delta calculated to determine speeds
                break;
        }

        // Request a redraw of the view
        this.invalidate();

        return true;  // Event handled
    }


    Random rand = new Random();

    // called when button is pressed
    public void AddButtonPressed(EditText ballNameEditText) {
        if (ballNameEditText == null) {
            Log.e("BouncingBallView", "ballNameEditText is null");
            return; // Exit if it's null
        }

        Log.d("BouncingBallView  BUTTON", "User tapped the  button ... VIEW");
        //get half of the width and height as we are working with a circle
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();


        String ballName = ballNameEditText.getText().toString();
        balls.add(new Ball(currentColor, ballName, currentX, currentY, currentDX, currentDY));  // add ball at every touch event

        Log.v("BALL ADDED", "Adding Ball: " + ballName);
        DataModel newBall = new DataModel(0,(float)currentX,(float)currentY,(float)currentDX,(float)currentDY);
        DBtest.save(newBall);

    }

    //Clear all balls
    public void clearButtonPressed(){
        balls.clear();
        DBtest.clearAllBalls();
    }

    public void initializeBalls() {
        List<DataModel> savedBalls = DBtest.findAll();
        for (DataModel data : savedBalls) {
            balls.add(new Ball(Color.BLUE, data.getModelX(), data.getModelY(), data.getModelDX(), data.getModelDY()));
        }
    }
}
