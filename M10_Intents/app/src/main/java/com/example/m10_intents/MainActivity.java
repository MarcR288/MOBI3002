package com.example.m10_intents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private int selectedColor = Color.BLACK;


    // Needs to reference this App path
    public final static String EXTRA_MESSAGE = "com.example.m10_intent_01.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity-INTENT", "onCreate: ");

        //Drawiung View
        DrawingView drawingView = findViewById(R.id.drawingView);

        Button redButton = findViewById(R.id.redButton); //Red paint
        Button blueButton = findViewById(R.id.blueButton); // Blue paint
        Button blankDrawButton = findViewById(R.id.blankDrawButton); //Free draw
        SeekBar widthSeekBar = findViewById(R.id.widthSeekBar); //Seeker bar


        // Set button click listeners to change color/disable itself and enable other
        redButton.setOnClickListener(v -> {
            drawingView.setColor(Color.RED);
            selectedColor = Color.RED;
            redButton.setEnabled(false);
            blueButton.setEnabled(true);
            Log.w("Color", "Red color selected");
        });

        blueButton.setOnClickListener(v -> {
            drawingView.setColor(Color.BLUE);
            selectedColor = Color.BLUE;
            blueButton.setEnabled(false);
            redButton.setEnabled(true);
            Log.w("Color", "Blue color selected");
        });


        // Set seek bar change listener to change stroke width
        widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float width = progress; // Assuming you want the width to directly match the seek bar value
                drawingView.setStrokeWidth(width);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        //Start a new activity with intent
        blankDrawButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BlankDrawingActivity.class);
            intent.putExtra("selected_color", selectedColor);
            startActivity(intent);
            Log.w("INTENT", "Free Draw Started");
        });
    }


    /**
     * Called when the user clicks the Send button
     * Send custom intent to another internal activity
     * http://developer.android.com/training/basics/firstapp/starting-activity.html
     */
    public void sendMessage1(View view) {
        // Send custom intent to another internal activity

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);

        Log.w("MainActivity-INTENT", "sendMessage1: " + message);

        startActivity(intent);
    }


    /**
     * Intent Action=View Data=text
     * http://developer.android.com/guide/components/intents-common.html
     */
    public void sendMessage2(View view) {
        // ACTION_SEND intent with EXTRA_TEXT ... goes to messaging, email, ...

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();

        Intent intent = new Intent();
        intent.setAction(intent.ACTION_SEND);
        intent.putExtra(intent.EXTRA_TEXT, "This is my text to send: " + message);
        intent.setType("text/plain");

        Log.w("MainActivity-INTENT", "sendMessage2: " + message);

        startActivity(intent);
    }

    /**
     * See Android Web Site
     * http://developer.android.com/training/sharing/send.html
     */
    public void sendMessage3(View view) {
        // ACTION_SEND intent with EXTRA_TEXT ... goes to messaging, email, ...

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        Log.w("MainActivity-INTENT", "sendMessage3-1: " + message);

        Uri webpage = Uri.parse(message);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        Log.w("MainActivity-INTENT", "sendMessage3-2: " + message);
        startActivity(intent);
    }


    private int PICK_IMAGE_REQUEST = 17;

    /**
     * Intent Action=ACTION_GET_CONTENT Data=text
     * Ask for a photo from the gallery
     * http://codetheory.in/android-pick-select-image-from-gallery-with-intents/
     */
    public void sendMessage4(View view) {
        // ACTION_SEND intent with EXTRA_TEXT ... goes to messaging, email, ...

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        Log.w("MainActivity-INTENT", "sendMessage4-1: " + message);

        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        Log.w("MainActivity-INTENT", "sendMessage4-2: " + message);
    }


    /**
     * Returns after photo picked from gallery
     * http://codetheory.in/android-pick-select-image-from-gallery-with-intents/
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                // Load the bitmap from the URI
                Bitmap originalBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                // Create a mutable bitmap
                Bitmap mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

                // Set the mutable bitmap in your drawing view
                DrawingView drawingView = findViewById(R.id.drawingView);
                drawingView.setBitmap(mutableBitmap); // Set the mutable bitmap here
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.w("INTENT", "PHOTO DRAW SELECTED");
    }
}