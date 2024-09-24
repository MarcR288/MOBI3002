package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;


/**
 * Created by Marc on 22/09/2024.
 */
public class Square {

    float size = 80;      // Square's side length
    float x;              // Square's center (x, y)
    float y;
    float speedX;         // Square's speed
    float speedY;
    private final RectF bounds; // Needed for Canvas.drawRect
    private final Paint paint;  // The paint style, color used for drawing


    //Cooldown
    private long lastCollisionTime = 0; // Last time the ball collided
    private static final long COLLISION_COOLDOWN = 1000; // Cooldown duration in milliseconds (1 second)

    public boolean checkCollisionCooldown() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastCollisionTime >= COLLISION_COOLDOWN) {
            lastCollisionTime = currentTime; // Update the last collision time
            return true; // Collision can be counted
        }
        return false; // Still in cooldown
    }

    // Constructor with parameters for position and speed
    public Square(int color, float x, float y, float speedX, float speedY) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // Use parameter position and speed
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void moveWithCollisionDetection(Box box) {
        // Get new (x, y) position
        x += speedX;
        y += speedY;

        // Detect collision and react
        if (x + size / 2 > box.xMax) {
            speedX = -speedX;
            x = box.xMax - size / 2;
        } else if (x - size / 2 < box.xMin) {
            speedX = -speedX;
            x = box.xMin + size / 2;
        }
        if (y + size / 2 > box.yMax) {
            speedY = -speedY;
            y = box.yMax - size / 2;
        } else if (y - size / 2 < box.yMin) {
            speedY = -speedY;
            y = box.yMin + size / 2;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set(x - size / 2, y - size / 2, x + size / 2, y + size / 2);
        canvas.drawRect(bounds, paint);
    }
}
