package com.example.anirudh.hawkeye;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.InterpolatorRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.List;

public class MyService extends Service {

    String topic1 = "test";
    String topicW = "/pandey/W";
    String topicE = "/pandey/E";
    String topicI = "/pandey/I";
    String file_name = "my_log";
    FileOutputStream fo;
    MqttAndroidClient client;
    String clientId;
    MqttConnectOptions options;
    IMqttToken token;
    int start;
    int end;
    int cur;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {


        try {
            clientId = MqttClient.generateClientId();
            client = new MqttAndroidClient(this.getApplicationContext(), "tcp://192.168.2.5:1883", clientId);
            options = new MqttConnectOptions();

            token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d("status: ", "connected to the broker");
                    Toast.makeText(getApplicationContext(), "connected to broker", Toast.LENGTH_SHORT).show();
                    setSubscription();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d("status: ", "onFailure");
                    Toast.makeText(getApplicationContext(), "failed to connected to broker", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }


        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                //tv.setText(new String(message.getPayload()));
                String data = new String(message.getPayload());

                if (topic.equals(topicW)) {
                    if (isAppIsInBackground(getApplicationContext())) {
                        SharedPreferences sp=getSharedPreferences("time", Context.MODE_PRIVATE);
                        cur=sp.getInt("currenthour",0);
                        start=sp.getInt("starthour",0);
                        end=sp.getInt("endhour",0);
                        Log.d("start hour in service",Integer.toString(start));
                        Log.d("end hour in service",Integer.toString(end));
                        Log.d("cuurent hour in service",Integer.toString(cur));
                        if(cur>=start && cur <=end)
                        shownotification();
                        //writeToFile(data);
                    } else {
                        //fo.close();
                    }
                }
                Intent intent = new Intent("data");
                //intent.setAction(MY_ACTION);

                intent.putExtra("DATAPASSED", data);
                //LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(intent);
                Log.d("status: ", data);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


        return START_STICKY;
    }

    public void writeToFile(String abc) {
        try {

            fo = openFileOutput(file_name, MODE_PRIVATE);
            abc = abc + '\n';
            fo.write(abc.getBytes());
            fo.close();

        } catch (FileNotFoundException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "File not found: " + e.toString());
        }

    }

    public int time()
    {
        int start = 9; // let's take your failing example: 21-07
        int end = 17;
        int hours = (end - start) % 24; // here hours will be 14

        Calendar cal = Calendar.getInstance();
// set calendar to TODAY 21:00:00.000
        cal.set(Calendar.HOUR_OF_DAY, start);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        long startHourMilli = cal.getTimeInMillis();

// add 14 hours = TOMORROW 07:00:00.000
        cal.add(Calendar.HOUR_OF_DAY, hours);
        long endHourMilli = cal.getTimeInMillis();

        long current = System.currentTimeMillis();

        if(current > startHourMilli && current > endHourMilli)
            return 1;
        else
            return 0;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setSubscription()
    {
        try {

            client.subscribe(topic1,1);
            client.subscribe(topicI,1);
            client.subscribe(topicE,1);
            client.subscribe(topicW,1);


        }
        catch (MqttException e)
        {
            e.printStackTrace();
        }
    }



    public void shownotification()
    {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(MyService.this);
        builder.setContentTitle("CAUTION!");
        builder.setContentText("Intruder Detected");
        builder.setSmallIcon(R.drawable.not);
        builder.setTicker("A new notification from HAWKEYE");
        //builder.setDefaults(Notification.DEFAULT_SOUND Notification.DEFAULT_LIGHTS);
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        Uri not= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        builder.setSound(not);

        builder.setAutoCancel(true);

        SharedPreferences s1=getApplicationContext().getSharedPreferences("checkNoti",MODE_PRIVATE);
        SharedPreferences.Editor editor=s1.edit();
        editor.putBoolean("ttyl",true);
        editor.commit();

        Intent i=new Intent(this,tab_activity.class);
        //i.putExtra("check",true);
        i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pi=PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);

        Notification notification=builder.build();
        NotificationManager manager= (NotificationManager) getApplication().getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1234,notification);
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Log.d("status", "TASK REMOVED");

        PendingIntent service = PendingIntent.getService(
                getApplicationContext(),
                1001,
                new Intent(getApplicationContext(), MyService.class),
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 100, service);
    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        }
        else
        {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }



}

