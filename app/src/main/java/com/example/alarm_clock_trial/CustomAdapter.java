package com.example.alarm_clock_trial;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private List<Alarm> alarmList;
    private LayoutInflater layoutInflater;
    private DBHelper db;

    public CustomAdapter (Context context , List<Alarm> alarmList){
        this.context = context;
        this.alarmList = alarmList;
        layoutInflater = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return alarmList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.new_beati_row,null);
        final Alarm selectedAlarm = alarmList.get(position);
        final TextView nameTV = convertView.findViewById(R.id.nameTextView);
        final TextView alarmTV = convertView.findViewById(R.id.timeTextView);
        final ImageView delete_btn= convertView.findViewById(R.id.delete_btn);
        final AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        nameTV.setText(selectedAlarm.getName());
        alarmTV.setText(selectedAlarm.toString());
        final Intent serviceIntent = new Intent(context, AlarmReceiver.class);
        //   set sound cho each sound
        serviceIntent.putExtra("sound", selectedAlarm.getSound());
        serviceIntent.putExtra("cate", selectedAlarm.getCategory());
        serviceIntent.putExtra("note", selectedAlarm.getName());

        //click button delete
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DBHelper(context);
                alarmList.remove(selectedAlarm);
                db.deleteAlarm(selectedAlarm);
                notifyDataSetChanged();
            }
        });



        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, selectedAlarm.getHour());
        calendar.set(Calendar.MINUTE, selectedAlarm.getMinute());
        calendar.set(Calendar.SECOND, 0);
        if(calendar.getTimeInMillis() < System.currentTimeMillis()){
            calendar.add(Calendar.DATE, 1);

        }

        ToggleButton toggleButton = convertView.findViewById(R.id.toggle);
        toggleButton.setChecked(selectedAlarm.getStatus());
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedAlarm.setStatus(isChecked);
                DBHelper db = new DBHelper(context);
                db.updateAlarm(selectedAlarm);

                MainActivity.alarmList.clear();
                List <Alarm> list = db.getAllAlarms();
                MainActivity.alarmList.addAll(list);
                notifyDataSetChanged();

                if(!isChecked && selectedAlarm.toString().equals(MainActivity.activeAlarm)){
                    serviceIntent.putExtra("extra", "off");
                    PendingIntent pendingIntent= PendingIntent.getBroadcast(context,position,serviceIntent, PendingIntent.FLAG_MUTABLE);
                    alarmManager.cancel(pendingIntent);
                    context.sendBroadcast(serviceIntent);

                }
            }
        });

        if(selectedAlarm.getStatus()){
            serviceIntent.putExtra("extra", "on");
            serviceIntent.putExtra("active", selectedAlarm.toString());
            PendingIntent pendingIntent= PendingIntent.getBroadcast(context, position,serviceIntent, PendingIntent.FLAG_MUTABLE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        }
        return convertView;
    }
}
