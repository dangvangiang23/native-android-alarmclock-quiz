package com.example.alarm_clock_trial;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class Music extends Service {
    private MediaPlayer mediaPlayer;
    private boolean quizsuccess = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String song = intent.getStringExtra("sound");
        Uri urisong = Uri.parse(String.format("android.resource://%s/%s/%s", getPackageName(), "/raw/", song));
        mediaPlayer = MediaPlayer.create(this, urisong);
        mediaPlayer.start();

        mediaPlayer.setLooping(true);
        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.setLooping(false);
        mediaPlayer.stop();
    }
}
