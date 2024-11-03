package com.example.m10_intents;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class BlankDrawingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank_drawing);

        DrawingView drawingView = findViewById(R.id.drawingView);
        drawingView.initializeBlankCanvas(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);

        // Retrieve the color from the Intent
        int selectedColor = getIntent().getIntExtra("selected_color", Color.BLACK); // Default to black if not found
        drawingView.setColor(selectedColor); // Set the color on your drawing view
    }
}
