package com.example.alarm_clock_trial;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class QuizSuccessActivity extends AppCompatActivity {
    private Button exitButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_success);
        bindingView();
        bindingAction();

    }


    private void bindingView() {
        exitButton = findViewById(R.id.exitSuccessButton);
    }
    private void bindingAction() {
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.alarmList.clear();
                startActivity(intent);

            }
        });
    }

}

