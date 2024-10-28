package com.example.m03_bounce;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

// Found tutorial to do put buttons over view here:
// https://code.tutsplus.com/tutorials/android-sdk-creating-custom-views--mobile-14548

public class MainActivity extends AppCompatActivity {
    // bbView is our bouncing ball view
    private BouncingBallView bouncingBallView;
    private TextView scoreView;
    private Button powerUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the scoreView
        scoreView = findViewById(R.id.scoreView);

        // Get the view object so we can reference it later
        bouncingBallView = (BouncingBallView) findViewById(R.id.custView);

        // Set the scoreView in BouncingBallView
        bouncingBallView.setScoreView(scoreView);

        powerUpButton = findViewById(R.id.powerUpButton);
        bouncingBallView.setPowerUpButton(powerUpButton); // Pass the button reference

        // Check sensors
        setupSensors();
    }
    // Sensors
    private SensorManager mSensorManager;
    private Sensor my_Sensor;

    private void setupSensors() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.v("SENSORS", "Sensor List=" + deviceSensors.toString());

        // Use the accelerometer.
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            my_Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            //my_Sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            Log.v("SENSORS", "my_Sensor=" + my_Sensor.toString() );
        }
        else{
            // Sorry, there are no accelerometers on your device.
            // You can't play this game.
            Log.v("SENSORS", "NO SENSOR TYPE?" );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bouncingBallView!=null) {
            Log.v("SENSORS", "onResume bouncingBallView=" + bouncingBallView.toString());
            if (my_Sensor !=null) {
                Log.v("SENSORS", "onResume my_Sensor=" + my_Sensor.toString());
                mSensorManager.registerListener((SensorEventListener) bouncingBallView, my_Sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        } else {
            Log.v("SENSORS", "onResume bouncingBallView=null");
        }
        Log.v("SENSORS", "onResume ACCELLEROMETER" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener) bouncingBallView);
        Log.v("SENSORS", "onPause ACCELLEROMETER" );
    }

    // button action

    public void onPowerUpClick(View v) {
        Log.d("PowerUp  BUTTON", "User tapped the  PowerUp button");
        // let the view do something
        bouncingBallView.powerUpButtonPressed();
        powerUpButton.setEnabled(false);
    }
}