package com.example.alarm_clock_trial;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity implements SoundAdapter.Clickable {
    private TimePicker timePicker;
    private EditText editText;
    private ImageView buttonSave, buttonCancel;
    private Alarm alarm;
    private boolean needRefresh;
    private TextView namesound;
    private ImageView imgsound;
    Spinner spinner;
    String selectedOption;
    private List<String> newlistfiles = new ArrayList<>();
    private AssetManager assetManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        bindingView();
        bindingAction();


    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("needRefresh", needRefresh);
        this.setResult(RESULT_OK, data);
        super.finish();
    }

    public void bindingView() {
        timePicker = findViewById(R.id.timePicker);
        editText = findViewById(R.id.name);
        buttonSave = findViewById(R.id.button_save);
        buttonCancel = findViewById(R.id.button_cancel);
        namesound = findViewById(R.id.namesound);
        imgsound = findViewById(R.id.imgsound);
        spinner = findViewById(R.id.mySpinner);
    }

    public List<String> getAllFilesAssets() {
        String[] list;
        assetManager = getAssets();
        newlistfiles = new ArrayList<>();
        try {
            list = assetManager.list("");

            for (int i = 0; i < list.length; i++) {
                if (list[i].endsWith(".txt")) {
                    //cut duoi file
                   list[i] = list[i].replaceAll(".txt", "");
                    newlistfiles.add(list[i]);
                }
            }
            return newlistfiles;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void bindingAction() {

        //  Adapter spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        for (String s : getAllFilesAssets()) {
            adapter.add(s);
            Log.e("logg", s);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = (String) parent.getItemAtPosition(position);
                // Hiển thị mục đã chọn lên thanh dropdown
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có mục nào được chọn
                spinner.setSelection(1);
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                String name = editText.getText().toString();
                String selectedSpinner = spinner.getSelectedItem().toString();
                DBHelper db = new DBHelper(getApplicationContext());
                if (namesound.getText().equals("Choose sound")) {
                    alarm = new Alarm(hour, minute, true, name, "supermario", selectedOption);
                    Toast.makeText(AddActivity.this, "selectcate " + selectedOption, Toast.LENGTH_LONG).show();
                } else {
                    alarm = new Alarm(hour, minute, true, name, namesound.getText().toString(), selectedOption);
                    Toast.makeText(AddActivity.this, "selectcate " + selectedOption, Toast.LENGTH_LONG).show();
                }
                db.addAlarm(alarm);
                needRefresh = true;
                onBackPressed();


            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        namesound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goChooseSound(v);
            }
        });
        imgsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goChooseSound(v);
            }
        });

    }


    @Override
    public void getSound(@NonNull Sound sound) {
        Intent intent = new Intent();
        intent.putExtra("result", String.valueOf(sound.getSound_name()));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void goChooseSound(View view) {
        Intent intent = new Intent(this, SoundListPicker.class);
        startActivityForResult(intent, 1);
        // startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            namesound.setText(data.getStringExtra("result"));
        }

    }
}
