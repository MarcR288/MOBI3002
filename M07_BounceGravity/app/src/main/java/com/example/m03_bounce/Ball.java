package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * Created by Russ on 08/04/2014.
 */
public class Ball {

    double radius = 50;      // Ball's radius
    double x;                // Ball's center (x,y)
    double y;
    double speedX;           // Ball's speed
    double speedY;
    double speed_resistance = 0.99f; //amount of slow-down
    double acc_resistance = 0.99f; //amount of slow-down
    private RectF bounds;   // Needed for Canvas.drawOval
    Paint paint;    // The paint style, color used for drawing
    private boolean isEnemy;
    private String name;
    private Paint textPaint;
    // Add accelerometer
    // Add ... implements SensorEventListener


    EnemyBall enemyBall;

    private double ax, ay, az = 0; // acceration from different axis

    public Ball(float x, float y, float radius, Paint paint) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.paint = paint;
        this.bounds = new RectF(); // Initialize bounds here
    }



    public Ball(){

    }

    public void setAcc(double ax, double ay, double az){
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    Random r = new Random();  // seed random number generator

    // Constructor
    public Ball(int color, String name) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        textPaint = new Paint();
        textPaint.setColor(color); // Set the text color
        textPaint.setTextSize(50); // Set the text size

        // random position and speed
        x = radius + r.nextInt(800);
        y = radius + r.nextInt(800);
        speedX = r.nextInt(10) - 5;
        speedY = r.nextInt(10) - 5;

        this.name = name;
    }

    // Constructor
    public Ball(int color, float x, float y, float speedX, float speedY, boolean isEnemy, String name) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        textPaint = new Paint();
        textPaint.setColor(Color.RED); // Set the text color
        textPaint.setTextSize(50); // Set the text size

        // use parameter position and speed
        this.name = name;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.isEnemy = isEnemy;
    }

    public void moveWithCollisionDetection(Box box, List<EnemyBall> enemyBalls) {
        // Get new (x,y) position
        x += speedX;
        y += speedY;

        // Add acceleration to speed
        speedX = (speedX) * speed_resistance + ax * acc_resistance;
        speedY = (speedY) * speed_resistance + ay * acc_resistance;

        // Detect collision and react
        if (x + radius > box.xMax) {
            speedX = -speedX;
            x = box.xMax - radius;
        } else if (x - radius < box.xMin) {
            speedX = -speedX;
            x = box.xMin + radius;
        }
        if (y + radius > box.yMax) {
            speedY = -speedY;
            y = box.yMax - radius;
        } else if (y - radius < box.yMin) {
            speedY = -speedY;
            y = box.yMin + radius;
        }

        // Use a synchronized block to safely access enemyBalls
        List<EnemyBall> toRemove = new ArrayList<>(); // Temporary list for removals

        synchronized (enemyBalls) {
            for (EnemyBall enemyBall : enemyBalls) {
                double dx = x - enemyBall.getX();
                double dy = y - enemyBall.getY();
                double distance = Math.sqrt((dx * dx) + (dy * dy));

                // Check for collisions
                if (distance < (radius + enemyBall.getRadius())) {
                    speedX = -speedX;
                    speedY = -speedY;
                    Log.v("COLLISION", "Collision Detected");
                    enemyBall.reduceHealth(1);
                    Log.v("COLLISION", "Enemy Health: " + enemyBall.getHealth());

                    // Add enemyBall to removal list if health is depleted
                    if (enemyBall.getHealth() <= 0) {
                        toRemove.add(enemyBall);
                    }
                }
            }
            // Remove all marked enemy balls
            enemyBalls.removeAll(toRemove);
        }
    }

    public void draw(Canvas canvas) {
        // convert to float for bounds
        bounds.set((float) (x - radius),
                (float) (y - radius),
                (float) (x + radius),
                (float) (y + radius));
        canvas.drawOval(bounds, paint);
        if (name != null && !name.isEmpty()) {
            float textX = (float) (x - textPaint.measureText(name) / 2); // Center the text
            float textY = (float) (y - radius - 10); // Position above the ball
            canvas.drawText(name, textX, textY, textPaint);
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }
}
