package com.example.alarm_clock_trial;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "my_channel_id";
    private static final int NOTIFICATION_ID = 1;
    static String note;


    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isRunning = false;
        String string = intent.getExtras().getString("extra");
        String cate = intent.getExtras().getString("cate");
        note = intent.getExtras().getString("note");
        QuizActivity.category = cate;


        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (Music.class.getName().equals((service.service.getClassName()))) {
                isRunning = true;
            }
        }
        Intent musicIntent = new Intent(context, Music.class);
        musicIntent.putExtra("sound", intent.getStringExtra("sound"));
        // Neu alarm on thi show noti
        if (string.equals("on") && !isRunning) {
            context.startService(musicIntent);
            MainActivity.activeAlarm = intent.getExtras().getString("active");

            //Alarm alarm = new Alarm();
       //     alarm = (Alarm) intent.getExtras().get("alarm");
         //   Log.e("alarmcheck2", alarm.getName());
            createNotificationChannel(context);
            showNotification(context, cate);
        } else if (string.equals("off")) {
            context.stopService(musicIntent);
            MainActivity.activeAlarm = "";
        }


    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(Context context, String cate) {
        Intent intent = new Intent(context, QuizActivity.class);
        intent.setAction(cate);
        Log.e("cate2", cate);
      //  String packagee = intent.getExtras().getString("package");
        //intent.putExtra("package", packagee);
      //  intent.putExtra("package", alarm.getCategory() );
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_active_24)
                .setContentTitle("Thông báo")
                .setContentText(" "+ note)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
