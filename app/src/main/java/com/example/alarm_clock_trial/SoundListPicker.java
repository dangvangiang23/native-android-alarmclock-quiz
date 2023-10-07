package com.example.alarm_clock_trial;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SoundListPicker extends AppCompatActivity implements SoundAdapter.Clickable {
    private RecyclerView rcvSound;
    private SoundAdapter soundAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rcv_sound);
        rcvSound = findViewById(R.id.rcv_sound);
        soundAdapter = new SoundAdapter(this, this);
        LinearLayoutManager linearLayoutMn = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvSound.setLayoutManager(linearLayoutMn);
        soundAdapter.setData(getlistSound());
        rcvSound.setAdapter(soundAdapter);
    }



    private List<Sound> getlistSound() {
        String packageName  = this.getPackageName();
        List<Sound> list = new ArrayList<>();
        MediaPlayer player = new MediaPlayer();
        Uri u = Uri.parse(String.format("android.resource://%s/%s/%s", packageName, "/raw/", "thich"));
        list.add(new Sound(R.drawable.rabbit, "digital", Uri.parse(String.format("android.resource://%s/%s/%s", packageName, "/raw/", "digital")), 1));
        list.add(new Sound(R.drawable.rabbit, "oversimplified", Uri.parse(String.format("android.resource://%s/%s/%s", packageName, "/raw/", "oversimplified")), 2));
        list.add(new Sound(R.drawable.rabbit, "ringtone", Uri.parse(String.format("android.resource://%s/%s/%s", packageName, "/raw/", "ringtone")), 3));
        list.add(new Sound(R.drawable.rabbit, "supermario", Uri.parse(String.format("android.resource://%s/%s/%s", packageName, "/raw/", "supermario")), 4));
        list.add(new Sound(R.drawable.rabbit, "theclockstrickes", Uri.parse(String.format("android.resource://%s/%s/%s", packageName, "/raw/", "theclockstrickes")), 5));
        list.add(new Sound(R.drawable.rabbit, "thichthich_phuongly", Uri.parse(String.format("android.resource://%s/%s/%s", packageName, "/raw/", "thichthich_phuongly")), 6));
        return list;
    }

    @Override
    public void getSound(@NonNull Sound sound) {
        Intent intent = new Intent();
        intent.putExtra("result", String.valueOf(sound.getSound_name()));
        setResult(RESULT_OK, intent);
        finish();
    }


}