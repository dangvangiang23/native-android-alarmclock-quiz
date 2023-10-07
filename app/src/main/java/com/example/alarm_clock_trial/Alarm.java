package com.example.alarm_clock_trial;

import android.util.Log;

import java.io.Serializable;

public class Alarm implements Serializable {
    private int id;
    private int hour;
    private int minute;
    private boolean status;
    private String name;
    private String sound;
    private String category;

    public Alarm() {
    }



    public Alarm( int hour, int minute, boolean status, String name, String sound, String category) {
        this.hour = hour;
        this.minute = minute;
        this.status = status;
        this.name = name;
        this.sound = sound;
        this.category = category;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    @Override
    public String toString() {
        String hourString, minuteString, format;
        if (hour > 12) {
            hourString = (hour - 12) + "";
            format = " PM";
        }
        else if (hour == 0) {
            hourString = "12";
            format = " AM";
        } else if (hour == 12) {
            hourString = "12";
            format = " PM";
        }else{
            hourString = hour + "";
            format = " AM";
        }

        if (minute < 10){
            minuteString = "0" + minute;
        }else {
            minuteString = "" + minute;
        }

        return hourString + ":" + minuteString + format;
    }
}
