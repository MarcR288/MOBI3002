package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;


/**
 * Created by Russ on 08/04/2014.
 */
public class Ball {
    private String name;
    float radius = 50;      // Ball's radius
    float x;                // Ball's center (x,y)
    float y;
    double speedX;           // Ball's speed
    double speedY;
    private RectF bounds;   // Needed for Canvas.drawOval
    private Paint paint;    // The paint style, color used for drawing
    private Paint textPaint; //Paint for text

    // Add accelerometer
    // Add ... implements SensorEventListener
    private double ax, ay, az = 0; // acceration from different axis

    // Constructor
    public Ball(int color, String name, float x, float y, double speedX, double speedY) {
        this.name = name;
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // Initialize text paint
        textPaint = new Paint();
        textPaint.setColor(Color.RED); // Set the text color
        textPaint.setTextSize(50); // Set the text size

        // Set position and speed
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public Ball(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
        this.x = x;
        this.y = y;   
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void setAcc(double ax, double ay, double az){
        this.ax = ax;
        this.ay = ay;
        this.az = az;
    }

    public void moveWithCollisionDetection(Box box) {
        // Get new (x,y) position
        x += speedX;
        y += speedY;

        // Add acceleration to speed
        speedX += ax;
        speedY += ay;

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
    }

    public void draw(Canvas canvas) {
        bounds.set(x - radius, y - radius, x + radius, y + radius);
        canvas.drawOval(bounds, paint);

        //Challenge #1
        // Draw the name above the ball
        if (name != null && !name.isEmpty()) {
            float textX = x - textPaint.measureText(name) / 2; // Center the text
            float textY = y - radius - 10; // Position above the ball
            canvas.drawText(name, textX, textY, textPaint);
        }
    }

}
