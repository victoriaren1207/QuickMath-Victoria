package com.example.quickmathgame;

import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textLevel;
    private TextView textRightAnswered;
    private TextView textQuestion;
    private TextView textTimer;

    private Button buttonOp1;
    private Button buttonOp2;
    private Button buttonOp3;
    private Button buttonOp4;

    private int level = 0;
    private int rightAnswer = 0;
    private String realOperation = "";
    private int numCorrect = 0;

    private static final long TIMER_INTERVAL = 1000; // 1 second
    private CountDownTimer timer;
    private long totalTimeInMillis = 10000; // Total time in milliseconds (10 seconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        textLevel = findViewById(R.id.textQuestionNumber);
        textRightAnswered = findViewById(R.id.textRightAnswered);
        textQuestion = findViewById(R.id.textQuestion);
        textTimer = findViewById(R.id.textTimer);

        // Initialize Buttons
        buttonOp1 = findViewById(R.id.buttonOption1);
        buttonOp2 = findViewById(R.id.buttonOption2);
        buttonOp3 = findViewById(R.id.buttonOption3);
        buttonOp4 = findViewById(R.id.buttonOption4);

        // Set the background drawable for the buttons
        buttonOp1.setBackground(ContextCompat.getDrawable(this, R.drawable.button_selector));
        buttonOp2.setBackground(ContextCompat.getDrawable(this, R.drawable.button_selector));
        buttonOp3.setBackground(ContextCompat.getDrawable(this, R.drawable.button_selector));
        buttonOp4.setBackground(ContextCompat.getDrawable(this, R.drawable.button_selector));

        textLevel.setText("QUESTION : " + (level) + " / 10");
        textRightAnswered.setText("NUMCORRECT : " + rightAnswer + " / 10");

        if (level < 10) {
            getARandomQuestion();
        }

        // Start the timer
        timer = new CountDownTimer(totalTimeInMillis, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                // Update the timer text view with the remaining time
                textTimer.setText("Time: " + secondsRemaining + "s");
            }

            @Override
            public void onFinish() {
                // Perform actions when the timer is finished
                // For example, switch to TimeUpActivity
                Intent intent = new Intent(MainActivity.this, TimeUpActivity.class);
                intent.putExtra("correctAnswers", rightAnswer);
                intent.putExtra("totalQuestions", 10);
                startActivity(intent);
                finish();
            }
        };

        timer.start();

        buttonOp1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handleAnswerSelection(buttonOp1);
            }
        });

        buttonOp2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handleAnswerSelection(buttonOp2);
            }
        });

        buttonOp3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handleAnswerSelection(buttonOp3);
            }
        });

        buttonOp4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                handleAnswerSelection(buttonOp4);
            }
        });
    }


    private void handleAnswerSelection(Button selectedButton) {
        boolean isCorrect = selectedButton.getText().equals(String.valueOf(rightAnswer));

        if (isCorrect) {
            selectedButton.setBackgroundResource(R.drawable.right_answer_bg);
            numCorrect++; // Increment numCorrect by 1
        } else {
            selectedButton.setBackgroundResource(R.drawable.wrong_answer_bg);
            timer.cancel();
        }

        level++;
        textLevel.setText("QUESTION : " + level + " / 10");
        textRightAnswered.setText("NUMCORRECT : " + numCorrect + " / " + (level));


        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (level < 10) {
                    getARandomQuestion();
                    timer.start(); // Start the timer for the next question
                } else {
                    // Transition to TimeUpActivity
                    Intent intent = new Intent(MainActivity.this, TimeUpActivity.class);
                    intent.putExtra("correctAnswers", rightAnswer);
                    intent.putExtra("totalQuestions", level);
                    startActivity(intent);
                    finish();
                }
            }
        }, 500);
    }



    private void getARandomQuestion() {
        int number1 = new Random().nextInt(11);
        int number2 = new Random().nextInt(11);
        int operation = new Random().nextInt(4);
        int result = 0;

        switch (operation) {
            case 0: // Addition
                realOperation = "+";
                result = number1 + number2;
                break;
            case 1: // Subtraction
                realOperation = "-";
                result = number1 - number2;
                break;
            case 2: // Multiplication
                realOperation = "*";
                result = number1 * number2;
                break;
            case 3: // Division
                realOperation = "/";
                result = number1 / number2;
                break;
        }

        textQuestion.setText(number1 + " " + realOperation + " " + number2);
        rightAnswer = result;

        // Generate random options
        int[] options = generateOptions(result);

        // Assign the correct answer to a random button
        Button[] buttons = {buttonOp1, buttonOp2, buttonOp3, buttonOp4};
        int correctButtonIndex = new Random().nextInt(4);
        buttons[correctButtonIndex].setText(String.valueOf(result));
        buttons[correctButtonIndex].setBackgroundResource(R.drawable.button_selector);

        // Assign the remaining options to the other buttons
        int optionIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (i != correctButtonIndex) {
                buttons[i].setText(String.valueOf(options[optionIndex]));
                buttons[i].setBackgroundResource(R.drawable.button_selector);
                optionIndex++;
            }
        }
    }

    private int[] generateOptions(int result) {
        int[] options = new int[3];

        for (int i = 0; i < 3; i++) {
            int option = result + new Random().nextInt(10) + 1;

            while (contains(options, option)) {
                option = result + new Random().nextInt(10) + 1;
            }

            options[i] = option;
        }

        return options;
    }

    private boolean contains(int[] options, int option) {
        for (int num : options) {
            if (num == option) {
                return true;
            }
        }
        return false;
    }
}
