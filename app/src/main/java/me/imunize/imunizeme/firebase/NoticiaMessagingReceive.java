package me.imunize.imunizeme.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.imunize.imunizeme.CarteirinhaActivity;
import me.imunize.imunizeme.R;
import me.imunize.imunizeme.WebViewActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by Sr. Décio Montanhani on 26/08/2017.
 */

public class NoticiaMessagingReceive extends FirebaseMessagingService {

    private static final int ID_NOTIFICACAO = 1000;
    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public void onMessageReceived(RemoteMessage remoteMessage) {



        if (remoteMessage.getData().size() > 0) {
            Map<String, String> mensagem = remoteMessage.getData();
            Log.i("mensagem recebida", String.valueOf(mensagem));

            sendNotification(mensagem.get("text"));
        }
        /*// TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
           */

        // Check if message contains a notification payload.
        /*if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }*/

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    private void sendNotification(String messageBody) {


        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification n = new Notification(R.mipmap.ic_imunizeme_launcher, "Você Recebeu uma mensagem", System.currentTimeMillis());

        String site = pegaLink(messageBody);

        if(site != null){

            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("site", site);

            PendingIntent p = PendingIntent.getActivity(this, ID_NOTIFICACAO, intent, 0);

            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_imunizeme_launcher)
                    .setContentTitle("Notícias Imunize.me")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(p);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(ID_NOTIFICACAO, notificationBuilder.build());
        }
    }

    private String pegaLink(String messageBody) {

        Matcher matcher = urlPattern.matcher(messageBody);
        while (matcher.find()) {
            int matchStart = matcher.start(1);
            int matchEnd = matcher.end();

            String site = messageBody.substring(matchStart, matchEnd);
            Log.i("site", site);

            return site;
            // now you have the offsets of a URL match
        }


        return null;
    }

}
