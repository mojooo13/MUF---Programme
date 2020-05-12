import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.muf_services.R;

public class PrimeService extends Service {
    private static final String CHANNEL_ID = "PrimeService.CHANNEL_ID";
    private String actualChannelId;
    private static final int NOTIFICATION_ID = 12344532;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        actualChannelId = createChannel();
    }
    public String createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel c = new NotificationChannel(CHANNEL_ID, "Prime Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if(manager == null) throw new RuntimeException("Notification Mangaer not available.");
            manager.createNotificationChannel(c);
            return CHANNEL_ID;
        }else {
            return "";
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification = new NotificationCompat
                .Builder(this, actualChannelId)
                .setContentTitle(getString(R.string.content_title))
                .setContentText(getString(R.string.conent_text))
                .setContentIntent(pendingIntent)
                .build();
        startForeground(NOTIFICATION_ID,notification);

        return START_NOT_STICKY;
    }
}
