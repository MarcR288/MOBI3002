package com.example.m10_intents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    //Holds the image to draw on
    private Bitmap bitmap;
    //Used to draw on the bitmap
    private Canvas canvas;
    //Defines the drawing (color, style, etc)
    private Paint paint;
    //Represents the drawing path
    private Path path;


    //Constructors - allow the view to be created
    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //initializes the Paint obj with specific styles
    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED); // Set the color of the paint
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10); // 10 pixels wide
        path = new Path(); // init a new path obj to store the drawing path
    }


    /*
    It retrieves the current dimensions of the view.
    Scales the bitmap to fit the view's dimensions.
    Initializes the Canvas with the scaled bitmap.
    */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;

        if (bitmap != null) {
            // Get the dimensions of the view
            int width = getWidth();
            int height = getHeight();

            // Create a scaled bitmap that fits the view
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            canvas = new Canvas(scaledBitmap); // Initialize the canvas with the scaled bitmap
            this.bitmap = scaledBitmap; // Set the scaled bitmap as the bitmap for drawing
        }
        invalidate(); // Redraw the view
    }

    /*
    This method is called to draw the view
    draws the bitmap at top-left corner (0, 0).
    it draws the current path using the defined paint, allowing the user’s drawing to appear
    over the bitmap.
    */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
        canvas.drawPath(path, paint);
    }

    //tracks user touch events to track drawing
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (bitmap == null || canvas == null) {
            return false; // Prevent drawing if no bitmap is set
        }

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path, paint);
                path.reset(); // Reset the path for new strokes
                break;
            default:
                return false;
        }
        invalidate(); // Request to redraw
        return true;
    }

    public void setColor(int color) {
        paint.setColor(color);
        invalidate(); // Redraw the view with the new color
    }

    public void setStrokeWidth(float width) {
        paint.setStrokeWidth(width);
        invalidate(); // Redraw the view with the new stroke width
    }

    //Setting default bitmap size when activity starts for Free Draw
    public void initializeBlankCanvas(int width, int height) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        invalidate(); // Redraw the view
    }
}
