package com.example.alarm_clock_trial;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String activeAlarm = "";
    //public static String category = "";
    private ListView listView;
    private static final int REQUEST_CODE = 1000;

    public static List<Alarm> alarmList = new ArrayList<>();
    private CustomAdapter customAdapter;
    private static final int PERMISSION_REQUEST_CODE = 123;

    private DBHelper db = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewListAlarm();

        requestAlarmPermission();

    }

    private void viewListAlarm() {
        Button button = findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
        listView = findViewById(R.id.listView);
        List<Alarm> list = db.getAllAlarms();

        alarmList.addAll(list);
        customAdapter = new CustomAdapter(getApplicationContext(), alarmList);
        listView.setAdapter(customAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            boolean needRefresh = data.getExtras().getBoolean("needRefresh");
            if (needRefresh) {
                alarmList.clear();
                List<Alarm> list = db.getAllAlarms();
                alarmList.addAll(list);
                customAdapter.notifyDataSetChanged();
            }
        }
    }


    // Hàm kiểm tra xem quyền đã được cấp hay chưa
    private boolean isAlarmPermissionGranted() {
        // Kiểm tra xem quyền đã được cấp hay chưa
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    // Yêu cầu quyền từ người dùng
    private void requestAlarmPermission() {
        if (!isAlarmPermissionGranted()) {
            // Nếu quyền chưa được cấp, hiển thị thông báo yêu cầu quyền
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Quyền đã được cấp trước đó
            // Tiến hành công việc liên quan đến báo thức
            // ...
            Log.d("TranANh", "requestAlarmPermission: quyen da cap");
        }
    }

    // Xử lý kết quả cấp quyền từ người dùng
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp
                // Tiến hành công việc liên quan đến báo thức

                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                // ...
            } else {
                // Người dùng từ chối cấp quyền
                // Xử lý theo yêu cầu của bạn
                // ...
                requestAlarmPermission();
                Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                Log.d("TranANh", "onRequestPermissionsResult: chua cap");
            }
        }
    }


}