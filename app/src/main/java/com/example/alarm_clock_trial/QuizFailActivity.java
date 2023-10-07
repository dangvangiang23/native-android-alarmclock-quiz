package com.example.alarm_clock_trial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class QuizFailActivity extends AppCompatActivity {
    private Button exitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_fail);
        bindingView();
        bindingAction();



    }



    private void bindingView() {
        exitButton = findViewById(R.id.exitFailButton);
    }
    private void bindingAction() {
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(intent);

            }
        });
    }

}

