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
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);
        double percentage = ((double) correctAnswers / totalQuestions) * 100;

        // Display values
        TextView correctAnswersTextView = findViewById(R.id.correctAnswersTextView);
        correctAnswersTextView.setText("Correct Answers: " + correctAnswers);

        TextView percentageTextView = findViewById(R.id.percentageTextView);
        percentageTextView.setText("Percentage Correct: " + String.format("%.2f", percentage) + "%");

        // "Try Again" button click listener
        Button tryAgainButton = findViewById(R.id.tryAgainButton);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the StartActivity
                Intent intent = new Intent(TimeUpActivity.this, StartActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the TimeUpActivity
            }
        });
    }
}
