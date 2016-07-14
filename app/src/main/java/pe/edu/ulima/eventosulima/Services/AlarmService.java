package pe.edu.ulima.eventosulima.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import pe.edu.ulima.eventosulima.DetailActivity;
import pe.edu.ulima.eventosulima.Navigation;
import pe.edu.ulima.eventosulima.R;

/**
 * Created by kyosh on 14/07/2016.
 */
public class AlarmService extends IntentService {

    private static final int NOTIF_ID = 1;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        mHandler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    public AlarmService(){
        super("AlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String evento = intent.getStringExtra("evento");
            final int key = intent.getIntExtra("key", 0);
            synchronized (this) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //createNotification();
                        createNotification(evento, key);
                        onDestroy();
                    }
                });
            }
        }
    }

    public void createNotification(String contenido, int key) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("firebaseposition", ""+key);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        //taskStackBuilder.addParentStack(DetailActivity.class);
        taskStackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(contenido)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIF_ID,notification);
    }
}
