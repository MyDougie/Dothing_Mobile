package dvorak.kosta.com.dothing_mobile.service;

/**
 * Created by YTK on 2017-07-16.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.activity.SplashActivity;

/**
 * FCM서버로부터 푸쉬메세지가 왔을 경우 다루는 서비스
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    /**
     * FCM서버로부터 메세지를 받았을 때
     * @param remoteMessage FCM서버로부터 온 메세지
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> map = remoteMessage.getData(); // 푸쉬메세지로부터 데이터를 얻어옴
        sendNotification(map.get("title"), map.get("body"), map.get("click"), map.get("errandsNum"), map.get("requestUserId")); // 알림메세지를 핸드폰에 보낸다
    }

    /**
     * 알림메세지를 핸드폰으로 보낸다
     * @param title 알림메세지의 제목
     * @param body 알림메세지의 내용
     * @param click 알림메세지 이벤트 종류(특정 액티비티로 이동)
     * @param errandsNum 특정 액티비티로 이동할 때 심부름 번호
     * @param requestUserId 특정 액티비티로 이동 할 때 요청자 id
     */
    private void sendNotification(String title, String body, String click, String errandsNum, String requestUserId) {
        Intent intent =new Intent(this, SplashActivity.class);

        // 기본액티비티가 아닌 특정 액티비티로 이동할 경우
        if (click != null) {
            intent.putExtra("requestUserId", requestUserId);
            intent.putExtra("errandsNum", errandsNum);
            intent.putExtra("click", click);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // 알림메세지 옵션 설정
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(body)
                .setVibrate(new long[]{1, 1000})
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
