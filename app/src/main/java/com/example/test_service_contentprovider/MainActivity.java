package com.example.test_service_contentprovider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    String offerChannelId = "offers";
    //    String offerChannelId2 = "offers2";
    NotificationManager notifManager;
    NotificationCompat.Builder builder;
    NotificationChannel notificationChannel;
    RemoteViews remoteViews;
    Handler handler;
    Runnable runnable;
    Thread thread;
    public static int notifyCode = 313;
    Context context;
    boolean pause;
    Button btn,buttonstart,buttonstop;
    @SuppressLint("RemoteViewLayout")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //برای ساخت نوتیفیکیشن به سه چیز نیاز مندیم
        //notificationmanager
        //notificationchannel برای اپی ای بالای 26 لازم است چون اندروید نوتیفیکیشن ها را بر اساس کانال تقسیم بندی کرده تا بتوان یک برنامه چندین نوع اعلان بفرستد
        //notificationbuilder
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.notify_btn);
        buttonstart = findViewById(R.id.btn_start);
        buttonstop = findViewById(R.id.btn_stop);
        context = MainActivity.this;
        notifManager = getSystemService(NotificationManager.class);//ساخت notificationmanager

        notificationChannel = new NotificationChannel(offerChannelId, offerChannelId,
                //ساخت  notificationchanel
                NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("برای تست");
        builder = new NotificationCompat.Builder(context, offerChannelId);
        //ساخت builderr
        remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        //برای ارسان اعلان سفارشی باید طرح ان را ایجاد کنیم و بعد مثل کد بالا ریموت بسازیم و از این طریق به اعلان اضافه کنیم
        Intent intent = new Intent(this, Myrecive.class);
        intent.putExtra("Intentid", notifyCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 123, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.notify_btn_close, pendingIntent);//
        //// برای اینکه وقتی روعی علان کلیک شد و یا یکی از اجزای اعلان باید از اینتنت استقاده کنیم وbroad cast resiver
        //وقتی با اینتنت به broad cast resiver  رفتیم مثلا با ایدی مشخص میتوانیم اعلان را خودمان ببندیم
        builder.setSmallIcon(R.drawable.ic_baseline_arrow_circle_down_24)//آیکن اعلان که الزامیست
                .setOngoing(true)//برای اینکه اعلان ثابت بماند و حذف نشود
                .setContent(remoteViews);//ست کردن طرح سفارشی برای اعلان

        notifManager.createNotificationChannel(notificationChannel);
        btn.setOnClickListener(v -> {
//            notifManager.notify(notifyCode, builder.build());
            pause=true;
            Intent intent1=new Intent(this,MyService.class);//اجرای forgrand service  با استفاده از اینتنت و اعلان
            // کلاس سرویس ناقص است باید آموزش مربوط را مشاهده کرد
            intent1.putExtra("pause",pause);
            startService(intent1);

        });
        buttonstart.setOnClickListener(view->{
            pause=false;
            Intent intent1=new Intent(this,MyService.class);
            intent1.putExtra("pause",pause);
            handler=new Handler();


thread = new Thread(() -> {
    startService(intent1);
    handler.postDelayed(thread,10000);
});
handler.post(thread);

        });
        buttonstop.setOnClickListener(view->{

            Intent intent1=new Intent(this,MyService.class);
            stopService(intent1);
            handler.removeCallbacks(thread);
        });
    }

}