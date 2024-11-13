/*
 * Copyright (c) 2024.
 * All rights reserved.
 *
 * Author: Marc Remillard
 *
 * Licensed under the Marc Apps License, Version 1.0;
 * you may not use this file except in compliance with the License.
 *
 */
package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MainActivity is the entry point for the Calculator app. This activity handles the
 * user interface and performs basic arithmetic operations (add, subtract, multiply, divide).
 * The user can input two numbers and select an operation. The result is displayed on the screen.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Initializes the activity and sets up button click listeners.
     * This method is called when the activity is created.
     *
     * @param savedInstanceState The saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Action when "Add" button is pressed
        ImageButton addButton = findViewById(R.id.b_Add);
        addButton.setOnClickListener(view -> performAddition());

        // Action when "Subtract" button is pressed
        ImageButton subtractButton = findViewById(R.id.b_Subtract);
        subtractButton.setOnClickListener(view -> performSubtraction());

        // Action when "Multiply" button is pressed
        ImageButton multiplyButton = findViewById(R.id.b_Multiply);
        multiplyButton.setOnClickListener(view -> performMultiplication());

        // Action when "Divide" button is pressed
        ImageButton divideButton = findViewById(R.id.b_Divide);
        divideButton.setOnClickListener(view -> performDivision());

    }

    /**
     * Performs addition of two numbers and displays the result.
     * Retrieves the numbers from the EditText fields and calculates their sum.
     * The result is displayed in the 'ANS' EditText field.
     */
    private void performAddition() {

        EditText textN1 = findViewById(R.id.Num1);
        EditText textN2 = findViewById(R.id.Num2);
        EditText textANS = findViewById(R.id.ANS);

        double[] nums = parseInputs(textN1, textN2);
        double answer;

        try {
            answer = nums[0] + nums[1];
        } catch (ArithmeticException noInputs) {
            answer = 0;
            Log.w("ADD BUTTON", "Add Selected with no inputs ... " + answer);
        }

        textANS.setText(Double.toString(answer));
        Log.w("ADD BUTTON", "Add Selected with => " + nums[0] + " + " + nums[1] + "=" + answer);
    }

    /**
     * Performs subtraction of two numbers and displays the result.
     * Retrieves the numbers from the EditText fields and calculates their difference.
     * The result is displayed in the 'ANS' EditText field.
     */
    private void performSubtraction() {

        EditText textN1 = findViewById(R.id.Num1);
        EditText textN2 = findViewById(R.id.Num2);
        EditText textANS = findViewById(R.id.ANS);

        double[] nums = parseInputs(textN1, textN2);
        double answer;

        try {
            answer = nums[0] - nums[1];
        } catch (ArithmeticException noInputs) {
            answer = 0;
            Log.w("SUBTRACT BUTTON", "Subtract Selected with no inputs ... " + answer);
        }

        textANS.setText(Double.toString(answer));
        Log.w("SUBTRACT BUTTON", "Subtract Selected with => " + nums[0] + " - " + nums[1] + "=" + answer);
    }

    /**
     * Performs multiplication of two numbers and displays the result.
     * Retrieves the numbers from the EditText fields and calculates their product.
     * The result is displayed in the 'ANS' EditText field.
     */
    private void performMultiplication(){

        EditText textN1 = findViewById(R.id.Num1);
        EditText textN2 = findViewById(R.id.Num2);
        EditText textANS = findViewById(R.id.ANS);

        double[] nums = parseInputs(textN1, textN2);
        double answer;

        try{
            answer = nums[0] * nums[1];
        } catch (ArithmeticException noInputs) {
            answer = 0;
            Log.w("MULTIPLY BUTTON", "Multiply Selected with no inputs ... " + answer);
        }

        textANS.setText(Double.toString(answer));
        Log.w("MULTIPLY BUTTON", "Multiply Selected with => " + nums[0] + " * " + nums[1] + "=" + answer);
    }

    /**
     * Performs division of two numbers and displays the result.
     * Retrieves the numbers from the EditText fields and calculates their quotient.
     * The result is displayed in the 'ANS' EditText field.
     * If an error occurs (e.g., division by zero), a warning is logged.
     */
    private void performDivision(){

        EditText textN1 = findViewById(R.id.Num1);
        EditText textN2 = findViewById(R.id.Num2);
        EditText textANS = findViewById(R.id.ANS);

        double[] nums = parseInputs(textN1, textN2);
        double answer;

        try {
            // Check if num2 is zero to prevent division by zero
            if (nums[0] == 0) {
                // Handle division by zero error
                answer = Double.NaN;
                Log.w("DIVIDE BUTTON", "Division by zero attempted.");
            } else {
                // Perform division if num2 is not zero
                answer = nums[0] / nums[1];
            }
            //Handle invalid inputs
        } catch (ArithmeticException divideByZero) {
            answer = 0;
            Log.w("DIVIDE BUTTON", "Divide Selected with invalid inputs ... " + divideByZero.getMessage());
        }

        // Set the answer in the UI, which will show NaN or a custom error message in case of a division by zero
        textANS.setText(Double.toString(answer));
        Log.w("DIVIDE BUTTON", "Divide Selected with => " + nums[0] + " / " + nums[1] + "=" + answer);
    }

    /**
     * Parses the input from two EditText fields and returns both numbers as an array.
     * If the input is invalid, it logs the error and returns 0.0 for both numbers.
     *
     * @param editText1 The first EditText to parse the input from.
     * @param editText2 The second EditText to parse the input from.
     * @return An array of two doubles: [num1, num2].
     */
    private double[] parseInputs(EditText editText1, EditText editText2){
        double[] numbers = new double[2];
        try {
            numbers[0] = Double.parseDouble(editText1.getText().toString());
            numbers[1] = Double.parseDouble(editText2.getText().toString());
        } catch (NumberFormatException e) {
            Log.w("PARSE INPUT", "Invalid input detected: " +
                    editText1.getText().toString() + " or " + editText2.getText().toString());
            numbers[0] = 0.0;  // Default value
            numbers[1] = 0.0;  // Default value
        }
        return numbers;
    }
}
