package ru.realityfamily.lct_mobile.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

import ru.realityfamily.lct_mobile.R;

public class AccelerationService extends Service {

    final static int ALARM_VALUE = 25;

    SensorManager sensorManager;
    Sensor sensorAccel;

    Timer timer;
    double acceleration;
    Context context;

    public AccelerationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        context = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (Math.abs(acceleration) > ALARM_VALUE) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "")
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle(getString(R.string.alert_title))
                            .setContentText("Если вы в порядке, просто " +
                                    "нажмите на уведомление")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000});
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(0, builder.build());
                }
            }
        };
        timer.schedule(task, 0, 400);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(listener);
    }

    SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                double tempAcceleration = Math.sqrt(Math.pow(event.values[0], 2) +
                        Math.pow(event.values[1], 2) + Math.pow(event.values[2], 2));

                acceleration = Math.round(tempAcceleration * 100.0) / 100.0;
            }
        }
    };
}