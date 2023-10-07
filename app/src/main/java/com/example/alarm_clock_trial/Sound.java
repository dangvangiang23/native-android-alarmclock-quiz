package com.example.alarm_clock_trial;

import android.net.Uri;

public class Sound {
    private int sound_img;
    private String sound_name;
    private Uri sound_path;
    private int soundID;

    public Sound() {
    }

    public Sound(int sound_img, String sound_name, Uri sound_path, int soundID) {
        this.sound_img = sound_img;
        this.sound_name = sound_name;
        this.sound_path = sound_path;
        this.soundID = soundID;
    }

    public int getSound_img() {
        return sound_img;
    }

    public void setSound_img(int sound_img) {
        this.sound_img = sound_img;
    }

    public String getSound_name() {
        return sound_name;
    }

    public void setSound_name(String sound_name) {
        this.sound_name = sound_name;
    }

    public Uri getSound_path() {
        return sound_path;
    }

    public void setSound_path(Uri sound_path) {
        this.sound_path = sound_path;
    }

    public int getSoundID() {
        return soundID;
    }

    public void setSoundID(int soundID) {
        this.soundID = soundID;
    }
}
