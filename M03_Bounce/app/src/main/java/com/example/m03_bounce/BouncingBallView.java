package com.example.m03_bounce;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Paint;
import java.util.ArrayList;
import java.util.Random;




/**
 * Created by Russ on 08/04/2014.
 */
public class BouncingBallView extends View {

    private ArrayList<Ball> balls = new ArrayList<>(); // list of Balls
    private Ball ball_1;  // use this to reference first ball in arraylist
    private Box box;

    private Paint textPaint;
    public int collisionCount;

    //Adding Squares
    private ArrayList<Square> squares = new ArrayList<>();
    private Square square_1;

    //Adding the rectangle
    private ArrayList<Rectangle> rectangles = new ArrayList<>();
    private Rectangle rectangle_1;


    private long lastTime;

    // For touch inputs - previous touch (x, y)
    private float previousX;
    private float previousY;


    // Check if the ball's position is within the rectangle's bounds
    public boolean checkCollisionBall(Rectangle rectangle, Ball ball) {
                /*
                ball.x + ball.radius / 2: Rightmost edge of the ball.
                ball.x - ball.radius / 2: Leftmost edge of the ball.
                ball.y + ball.radius / 2: Bottommost edge of the ball.
                ball.y - ball.radius / 2: Topmost edge of the ball.

                rectangle.x - rectangle.width / 2: Left edge of the rectangle.
                rectangle.x + rectangle.width / 2: Right edge of the rectangle.
                rectangle.y - rectangle.height / 2: Top edge of the rectangle.
                rectangle.y + rectangle.height / 2: Bottom edge of the rectangle.

                Returns true when all of these conditions are met
                 */
        return (ball.x + ball.radius / 2 > rectangle.x - rectangle.width / 2 &&
                ball.x - ball.radius / 2 < rectangle.x + rectangle.width / 2 &&
                ball.y + ball.radius / 2 > rectangle.y - rectangle.height / 2 &&
                ball.y - ball.radius / 2 < rectangle.y + rectangle.height / 2);
    }

    // Check if the square's position is within the rectangle's bounds
    public boolean checkCollisionSquare(Rectangle rectangle, Square square){
        return (square.x + square.size / 2 > rectangle.x - rectangle.width / 2 &&
                square.x - square.size / 2 > rectangle.x + rectangle.width / 2 &&
                square.y + square.size / 2 > rectangle.y - rectangle.height / 2 &&
                square.y - square.size / 2 > rectangle.y + rectangle.height / 2);
    }



    public BouncingBallView(Context context, AttributeSet attrs) {
        super(context, attrs);


        Log.v("BouncingBallView", "Constructor BouncingBallView");

        // create the box
        box = new Box(getColorBasedOnCollisionCount(collisionCount));

        // Initialize paint for text
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK); // Set text color
        textPaint.setTextSize(50); // Set text size

        //ball_1 = new Ball(Color.GREEN);
        balls.add(new Ball(Color.GREEN));
        ball_1 = balls.get(0);  // points ball_1 to the first; (zero-ith) element of list
        Log.w("BouncingBallLog", "Just added a bouncing ball");

        //ball_2 = new Ball(Color.CYAN);
        balls.add(new Ball(Color.CYAN));
        Log.w("BouncingBallLog", "Just added another bouncing ball");

        float initialX = 1;
        float initialY = 1;
        float initialSpeedX = 5;
        float initialSpeedY = 5;
        float width = 80;
        float height = 40;

        rectangles.add(new Rectangle(Color.MAGENTA, initialX, initialY, initialSpeedX, initialSpeedY, width, height));
        rectangle_1 = rectangles.get(0);

        // To enable keypad
        this.setFocusable(true);
        this.requestFocus();
        // To enable touch mode
        this.setFocusableInTouchMode(true);
    }

    private int getColorBasedOnCollisionCount(int count){
        if (count < 100) {
            return Color.WHITE;
        } else if ((count >= 100) & (count < 399)){
            return Color.YELLOW;
        } else if ((count >= 400) & (count <750)){
            return Color.BLUE;
        } else if ((count >= 750) & (count < 1000)){
            return Color.CYAN;
        } else if (count > 1000){
            return Color.MAGENTA;
        } else {
            return Color.MAGENTA;
        }
    }

    // Called back to draw the view. Also called after invalidate().
    @Override
    protected void onDraw(Canvas canvas) {

        Log.v("BouncingBallView", "onDraw");

        // Draw the components
        box.draw(canvas);
        //canvas.drawARGB(0,25,25,25);
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        for (Ball b : balls) {
            b.draw(canvas);  //draw each ball in the list
            b.moveWithCollisionDetection(box);  // Update the position of the ball
        }

        for (Square square : squares) { //iterates over each square object in the sqares Arraylist
            square.draw(canvas); //calls the draw method. Responsible for rendering the square on the 'Canvas'
            square.moveWithCollisionDetection(box); //updates the squares position based on speed and checks for collision with 'box'
        }

        for (Rectangle rectangle : rectangles){
            rectangle.draw(canvas);
            rectangle.moveWithCollisionDetection(box);
        }

        // Counting Ball Collisions
        for (Ball b : balls) {
            boolean collided = checkCollisionBall(rectangle_1, b);
            if (collided && b.checkCollisionCooldown()) {
                collisionCount++;
                Log.d("COLLISION - BALL", "Collision detected! Count: " + collisionCount);

                // Update box color based on new collisionCount
                box.setColor(getColorBasedOnCollisionCount(collisionCount));
            }
        }

        // Counting Square Collisions
        for (Square square : squares) {
            boolean collided = checkCollisionSquare(rectangle_1, square);
            if (collided && square.checkCollisionCooldown()) {
                collisionCount++;
                Log.d("COLLISION - SQUARE", "Collision detected! Count: " + collisionCount);

                // Update box color based on new collisionCount
                box.setColor(getColorBasedOnCollisionCount(collisionCount));

                }
            }

        // Draw the collision count
        canvas.drawText("Score: " + collisionCount, 50, 100, textPaint);

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
        float deltaX, deltaY;
        long currentTime = System.currentTimeMillis(); //get current time
        float scalingFactor = 5.0f / ((box.xMax > box.yMax) ? box.yMax : box.xMax);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // Store initial touch position and time
                previousX = currentX;
                previousY = currentY;
                lastTime = currentTime;
                break;

            case MotionEvent.ACTION_MOVE:

                //calculate the distance moved and the time elapsed
                deltaX = currentX - previousX;;
                deltaY = currentY - previousY;
                long timeElapsed = currentTime - lastTime;


                //Calculate distance and speed
                float distance = (float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
                float speed = distance / timeElapsed;
                if (timeElapsed > 0) { //so swipes that occur simultaneously don't lead to a divide by 0 error
                    // Define the threshold for speed required
                    float speedThreshold = 0.9f;

                    if (speed > speedThreshold) {
                        Log.d("SWIPE", "Fast swipe detected");

                        if (square_1 == null) {
                            // Initialize square_1 if it hasn't been created yet
                            square_1 = new Square(Color.GREEN, previousX, previousY, 0, 0);
                        }
                        square_1.speedX += deltaX * scalingFactor;
                        square_1.speedY += deltaY * scalingFactor;
                        squares.add(new Square(Color.GREEN, previousX, previousY, deltaX, deltaY));
                    } else {
                        Log.d("SWIPE", "Slow swipe detected");
                        ball_1.speedX += deltaX * scalingFactor;
                        ball_1.speedY += deltaY * scalingFactor;
                        Log.w("BouncingBallLog", "x,y= " + previousX + " ," + previousY + "  Xdiff=" + deltaX + " Ydiff=" + deltaY);
                        balls.add(new Ball(Color.BLUE, previousX, previousY, deltaX, deltaY));  // Add ball at every touch event
                    }
                    // A way to clear the list when too many balls
                    if (balls.size() > 60) {
                        Log.v("BouncingBallLog", "Too many balls, clear back to 1");
                        balls.clear();
                        balls.add(new Ball(Color.RED));
                        ball_1 = balls.get(0);  // Points ball_1 to the first (zero-ith) element of the list
                    }
                    // Save current x, y and last time
                    previousX = currentX;
                    previousY = currentY;
                    lastTime = currentTime;
                } else {
                    // Optionally handle the case where timeElapsed is not greater than 0
                    Log.w("SWIPE", "Time elapsed is zero, ignoring swipe");
                }

            case MotionEvent.ACTION_UP:
                break;
        }


        // Try this (remove other invalidate(); )
        //this.invalidate();

        return true;  // Event handled
    }

    Random rand = new Random();
    // called when button is pressed
    public void marcButtonPressed() {
        Log.d("BouncingBallView  BUTTON", "User tapped the  button ... VIEW");

        //get half of the width and height as we are working with a circle
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();



        // make random x,y, velocity
        int x = rand.nextInt(viewWidth);
        int y = rand.nextInt(viewHeight);

        //Logic to make random colored balls either go super fast or super slow
        Random random = new Random();
        int speed = random.nextInt(2); //Random between 0 and 1

        //Slow case
        if (speed == 0){
            int dx = (4);
            int dy = (4);
            int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            balls.add(new Ball(randomColor, x, y, dx, dy)); // add ball at every touch event
            //Super speed
        } else {
            int dx = (100);
            int dy = (50);
            int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            balls.add(new Ball(randomColor, x, y, dx, dy)); // add ball at every touch event
        }

        //this.invalidate();

    }



}
