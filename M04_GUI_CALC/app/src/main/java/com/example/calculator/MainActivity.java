package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action when "Add" button is pressed
        ImageButton addButton = findViewById(R.id.b_Add);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAddition();
            }
        });

        // Action when "Subtract" button is pressed
        ImageButton subtractButton = findViewById(R.id.b_Subtract);
        subtractButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performSubtraction();
            }
        });

        // Action when "Multiply" button is pressed
        ImageButton multiplyButton = findViewById(R.id.b_Multiply);
        multiplyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                performMultiplication();
            }
        });

        // Action when "Divide" button is pressed
        ImageButton divideButton = findViewById(R.id.b_Divide);
        divideButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                performDivision();
            }
        });


    }

    // Method to perform addition
    private void performAddition() {
        Log.d("ADD BUTTON", "User tapped the Add button");

        double d1 = 0.0;
        double d2 = 0.0;
        double answer = 0.0;

        EditText textN1 = findViewById(R.id.Num1);
        EditText textN2 = findViewById(R.id.Num2);
        EditText textANS = findViewById(R.id.ANS);

        try {

            d1 = Double.parseDouble(textN1.getText().toString());
            d2 = Double.parseDouble(textN2.getText().toString());
            answer = d1 + d2;
        } catch (Exception e) {
            Log.w("ADD BUTTON", "Add Selected with no inputs ... " + answer);
        }

        textANS.setText(Double.toString(answer));
        Log.w("ADD BUTTON", "Add Selected with => " + d1 + " + " + d2 + "=" + answer);
    }

    // Method to perform subtraction
    private void performSubtraction() {
        Log.d("SUBTRACT BUTTON", "User tapped the Subtract button");

        double d1 = 0.0;
        double d2 = 0.0;
        double answer = 0.0;

        EditText textN1 = findViewById(R.id.Num1);
        EditText textN2 = findViewById(R.id.Num2);
        EditText textANS = findViewById(R.id.ANS);

        try {
            d1 = Double.parseDouble(textN1.getText().toString());
            d2 = Double.parseDouble(textN2.getText().toString());
            answer = d1 - d2;
        } catch (Exception e) {
            Log.w("SUBTRACT BUTTON", "Subtract Selected with no inputs ... " + answer);
        }

        textANS.setText(Double.toString(answer));
        Log.w("SUBTRACT BUTTON", "Subtract Selected with => " + d1 + " - " + d2 + "=" + answer);
    }

    //Method to perform Multiplication
    private void performMultiplication(){
        Log.d("MULTIPLY BUTTON", "User tapped the Multiply button");

        double d1 = 0.0;
        double d2 = 0.0;
        double answer = 0.0;

        EditText textN1 = findViewById(R.id.Num1);
        EditText textN2 = findViewById(R.id.Num2);
        EditText textANS = findViewById(R.id.ANS);

        try{
            d1 = Double.parseDouble(textN1.getText().toString());
            d2 = Double.parseDouble(textN2.getText().toString());
            answer = d1 * d2;
        } catch (Exception e) {
            Log.w("MULTIPLY BUTTON", "Multiply Selected with no inputs ... " + answer);
        }

        textANS.setText(Double.toString(answer));
        Log.w("MULTIPLY BUTTON", "Multiply Selected with => " + d1 + " * " + d2 + "=" + answer);
    }

    //Method to perform Division
    private void performDivision(){
        Log.d("DIVIDE BUTTON", "User tapped the Divide button");

        double d1 = 0.0;
        double d2 = 0.0;
        double answer = 0.0;

        EditText textN1 = findViewById(R.id.Num1);
        EditText textN2 = findViewById(R.id.Num2);
        EditText textANS = findViewById(R.id.ANS);

        try{
            d1 = Double.parseDouble(textN1.getText().toString());
            d2 = Double.parseDouble(textN2.getText().toString());
            answer = d1 / d2;
        } catch (Exception e){
            Log.w("DIVIDE BUTTON", "Divide Selected with no inputs ... " + answer);
        }
        textANS.setText(Double.toString(answer));
        Log.w("DIVIDE BUTTON", "Divide Selected with => " + d1 + " / " + d2 + "=" + answer);
    }
}
