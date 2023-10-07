package com.example.alarm_clock_trial;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity {
    public static String category = "";

    private RadioGroup rbGroup;
    private RadioButton button1;
    private RadioButton button2;
    private RadioButton button3;
    private RadioButton button4;
    private Button buttonSubmit;

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;

    private TextView textViewCorrect, textViewWrong;


    private ArrayList<Question> questionArrayList;
    private int questionCounter;
    private int questionTotalCount;
    private Question currentQuestion;
    private boolean answered;
    private ColorStateList colorButton;

    private int correctAns = 0, wrongAns = 0, score = 0;


    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_activity);

        bindingViews();
        fetchDB();

        colorButton = button1.getTextColors();


    }

    private void bindingViews() {
        textViewCorrect = findViewById(R.id.textCorrect);
        textViewWrong = findViewById(R.id.textWrong);

        textViewQuestionCount = findViewById(R.id.textViewTotalQuestions);
        textViewScore = findViewById(R.id.textScore);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        rbGroup = findViewById(R.id.optionsRadioGroup);
        button1 = findViewById(R.id.option1RadioButton);
        button2 = findViewById(R.id.option2RadioButton);
        button3 = findViewById(R.id.option3RadioButton);
        button4 = findViewById(R.id.option4RadioButton);
        buttonSubmit = findViewById(R.id.submitButton);
    }

    private void fetchDB() {

        questionArrayList = readFile();
        for (int i = 0; i < questionArrayList.size(); i++) {

            Log.e("question", questionArrayList.get(i).toString());
        }
        startQuiz();
    }


    private ArrayList<Question> readFile() {

        ArrayList list = new ArrayList();
        String str = "";
        try {

            InputStream inputStream = getAssets().open(category + ".txt"); //get the category name (package name)
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            str = new String(buffer);
            Scanner scanner = new Scanner(str);


            String line;
            String question = "";
            String[] alternatives = null;

            int answer = 0;
            int counter = 0;

            do {

                do {
                    if (counter == 0) {
                        alternatives = new String[4];
                    }
                    line = scanner.nextLine();

                    if (line.contains("Q.")) { //stores the question
                        question = line;
                    } else if (line.contains(")")) { //stores the alternatives
                        alternatives[counter++] = line;
                    } else if (Character.isDigit(line.indexOf(0)) || counter == 4) { //Stores the answer
                        answer = Integer.valueOf(line);
                    }

                } while (answer == 0);

                list.add(new Question(question, alternatives, answer, category));
                counter = 0;
                answer = 0;

            } while (scanner.hasNext());
            inputStream.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

        return list;
    }


    private void startQuiz() {


        questionTotalCount = questionArrayList.size();
        Collections.shuffle(questionArrayList);

        showQuestion();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.option1RadioButton) {
                    Log.d("CheckedChange", "CheckedID: " + checkedId + " " + R.id.option1RadioButton);
                    button1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.option_selected_background));
                    button2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
                    button3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
                    button4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));


                } else if (checkedId == R.id.option2RadioButton) {
                    Log.d("CheckedChange", "CheckedID: " + checkedId + " " + R.id.option2RadioButton);
                    button1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
                    button2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.option_selected_background));
                    button3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
                    button4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));

                } else if (checkedId == R.id.option3RadioButton) {
                    button1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
                    button2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
                    button3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.option_selected_background));
                    button4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));

                } else if (checkedId == R.id.option4RadioButton) {
                    button1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
                    button2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
                    button3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
                    button4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.option_selected_background));

                } else {
                    Log.d("CheckedChange", "run to default of set color when checked");
                }
//// Gọi hàm cần kiểm tra thời gian chạy ở đây
//                long endTime = System.currentTimeMillis();
//                long executionTime = endTime - startTime;
//                Log.d("CheckTimeRun","Thời gian chạy: " + executionTime + "ms");
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (button1.isChecked() || button2.isChecked() || button3.isChecked() || button4.isChecked()) {
                        quizOperation();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select 1 option", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void quizOperation() {
        answered = true;
        RadioButton radioButtonSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answer = rbGroup.indexOfChild(radioButtonSelected) + 1;

        checkSolution(answer);
    }

    private void checkSolution(int answer) {


        if (currentQuestion.getAnswer() == answer) {
            // Đoạn này có thể thêm hiệu ứng khi trả lời đúng
            Toast.makeText(QuizActivity.this, "Correct!!!", Toast.LENGTH_SHORT).show();
            showQuestion();
            correctAns++;
            score += 2;
            textViewCorrect.setText("Correct: " + String.valueOf(correctAns));
            textViewScore.setText("Score: " + String.valueOf(score));
        } else {
            // Hiệu ứng khi trả lời sai
            Toast.makeText(QuizActivity.this, "Wrong!!!", Toast.LENGTH_SHORT).show();
            showQuestion();
            wrongAns++;
            score--;
            if (score < 0) {
                score = 0;
            }
            if (score > 10) {
                score = 10;
            }
            textViewWrong.setText("Wrong: " + String.valueOf(wrongAns));
            textViewScore.setText("Score: " + String.valueOf(score));

        }
        if (score >= 10) {

            Intent intent = new Intent(getApplicationContext(), QuizSuccessActivity.class);
            startActivity(intent);
            Intent musicIntent = new Intent(getApplicationContext(), Music.class);
            this.stopService(musicIntent);

        }


        // Đổi button báo người dùng câu hỏi cuối
        if (questionCounter < questionTotalCount) {

        }
    }

    private void showQuestion() {

        rbGroup.clearCheck();
        button1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
        button2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
        button3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));
        button4.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.button_radio_background));


        // check sl câu hỏi đã hiển thị
        if (questionCounter < questionTotalCount) {
            // Lấy câu hỏi từ trong list ra và hiển thị
            //String question, String[] alternatives, int answer, String category


            currentQuestion = questionArrayList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            button1.setText(currentQuestion.getAlternatives()[0]);
            button2.setText(currentQuestion.getAlternatives()[1]);
            button3.setText(currentQuestion.getAlternatives()[2]);
            button4.setText(currentQuestion.getAlternatives()[3]);

            questionCounter++;
            answered = false;
            buttonSubmit.setText("Confirm");
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionTotalCount);

        } else {
            // nếu như chạy hết câu hỏi thì chuyển về (QuizActivity?)
            // -> đoạn này sau cho chuyển về màn hình tắt báo thức


            // nếu điểm lớn hơn =10 thì sang màn hình thoát

//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
//                    startActivity(intent);
//                }
//            }, 1000);
            Intent intent = new Intent(getApplicationContext(), QuizFailActivity.class);
            startActivity(intent);

        }
    }


}

