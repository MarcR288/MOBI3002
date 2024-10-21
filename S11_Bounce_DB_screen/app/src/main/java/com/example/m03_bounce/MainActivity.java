package com.example.m03_bounce;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

// Found tutorial to do put buttons over view here:
// https://code.tutsplus.com/tutorials/android-sdk-creating-custom-views--mobile-14548

public class MainActivity extends AppCompatActivity {
    // bbView is our bouncing ball view
    private BouncingBallView bbView;
    private int viewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the view object so we can reference it later
        bbView = (BouncingBallView) findViewById(R.id.custView);
        //SeekerBars Setup
        //Set up color seekbar
        SeekBar seekbar_color = (SeekBar) findViewById(R.id.seekBar_Color);
        seekbar_color.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                bbView.currentColor = getColorFromProgress(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar){
            }
        });

        // Set up SeekbarX
        SeekBar seekbar_x = (SeekBar) findViewById(R.id.seekBar_X);
        seekbar_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                bbView.currentX = (progress * 14);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Set up SeekbarY
        SeekBar seekbar_y = (SeekBar) findViewById(R.id.seekBar_Y);
        seekbar_y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                bbView.currentY = (progress * 28);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Set up SeekbarDX
        SeekBar seekbar_dx = (SeekBar) findViewById(R.id.seekBar_DX);
        seekbar_dx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                bbView.currentDX = (progress / 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Set up SeekbarDY
        SeekBar seekbar_dy = (SeekBar) findViewById(R.id.seekBar_DY);
        seekbar_dy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                bbView.currentDY = (progress / 10);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
    // button action
    public void onAddButtonClick(View v) {
        Log.d("MainActivity  BUTTON", "User tapped the  button ... MAIN");
        // get spinner values
        SeekBar seekbar_color = (SeekBar) findViewById(R.id.seekBar_Color);
        SeekBar seekbar_x = (SeekBar) findViewById(R.id.seekBar_X);
        SeekBar seekbar_y = (SeekBar) findViewById(R.id.seekBar_Y);
        SeekBar seekbar_dx = (SeekBar) findViewById(R.id.seekBar_DX);
        SeekBar seekbar_dy = (SeekBar) findViewById(R.id.seekBar_DY);

        int string_color = seekbar_color.getProgress();
        int string_x = seekbar_x.getProgress();
        int string_y = seekbar_y.getProgress();
        int string_dx = seekbar_dx.getProgress();
        int string_dy = seekbar_dy.getProgress();

        Log.d("MainActivity  BUTTON", "Color="+string_color+" X="+string_x+" Y="+string_y+" DX="+string_dx+" DY="+string_dy);
        // let the view do something
        EditText BallNameEditText = findViewById(R.id.ball_name_edit_text);
        bbView.AddButtonPressed(BallNameEditText);
    }

    public void onClearButtonClick(View v){
        bbView.clearButtonPressed();
    }

    //Change Colour
    private int getColorFromProgress(int progress) {
        int red, green, blue;
        if (progress < 85) {
            red = 255;
            green = progress * 3;
            blue = 0;
        } else if (progress < 170) {
            red = progress * 3;
            green = 255;
            blue = 0;
        } else {
            red = 0;
            green = 255;
            blue = progress * 3;
        }
        return Color.rgb(red, green, blue);
    }

}