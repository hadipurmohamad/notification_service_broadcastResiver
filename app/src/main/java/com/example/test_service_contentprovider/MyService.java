package com.example.test_service_contentprovider;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
MediaPlayer mediaPlayer;

Context context;
    @Override
    public IBinder onBind(Intent intent) {

   return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


//      mediaPlayer.start();
////      startForegroundService(intent);
//        boolean pause = intent.getExtras().getBoolean("pause");
//        if (pause){
//        if (mediaPlayer.isPlaying()){
//            mediaPlayer.pause();
//        }else{
//            mediaPlayer.start();
//        }
//        }
    Toast.makeText(context, "انجام وظیفه...", Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        context=this;
        super.onCreate();
//        mediaPlayer=MediaPlayer.create(this,R.raw.r);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mediaPlayer.stop();
    }
}