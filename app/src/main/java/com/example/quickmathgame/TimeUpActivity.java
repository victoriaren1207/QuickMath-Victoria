package com.example.quickmathgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimeUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_up);

        // Retrieve values from intent
        Intent intent = getIntent();
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 10);

        // Display values
        TextView correctAnswersTextView = findViewById(R.id.correctAnswersTextView);
        correctAnswersTextView.setText("Correct Answers: " + correctAnswers + " out of " + totalQuestions);

        // "Try Again" button click listener
        Button tryAgainButton = findViewById(R.id.tryAgainButton);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainActivity
                Intent intent = new Intent(TimeUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the TimeUpActivity
            }
        });
    }
}
