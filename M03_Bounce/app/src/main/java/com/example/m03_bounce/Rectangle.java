package com.example.m03_bounce;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by Marc on 22/09/2024.
 */
public class Rectangle {

    float width = 80;     // Rectangle's width
    float height = 40;    // Rectangle's height
    float x;              // Rectangle's center (x, y)
    float y;
    float speedX;         // Rectangle's speed
    float speedY;
    private final RectF bounds; // Needed for Canvas.drawRect
    private final Paint paint;  // The paint style, color used for drawing

    // Constructor with parameters for position, speed, width, and height
    public Rectangle(int color, float x, float y, float speedX, float speedY, float width, float height) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);

        // Use parameter position, speed, width, and height
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.width = width;
        this.height = height;
    }

    public Rectangle(int color) {
        bounds = new RectF();
        paint = new Paint();
        paint.setColor(color);
    }

    public void moveWithCollisionDetection(Box box) {
        // Get new (x, y) position
        x += speedX;
        y += speedY;

        // Detect collision and react
        if (x + width / 2 > box.xMax) {
            speedX = -speedX;
            x = box.xMax - width / 2;
        } else if (x - width / 2 < box.xMin) {
            speedX = -speedX;
            x = box.xMin + width / 2;
        }
        if (y + height / 2 > box.yMax) {
            speedY = -speedY;
            y = box.yMax - height / 2;
        } else if (y - height / 2 < box.yMin) {
            speedY = -speedY;
            y = box.yMin + height / 2;
        }
    }

    public void draw(Canvas canvas) {
        bounds.set(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
        canvas.drawRect(bounds, paint);
    }
}