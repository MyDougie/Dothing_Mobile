package dvorak.kosta.com.dothing_mobile.service;

/**
 * Created by YTK on 2017-07-16.
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * 푸쉬메세지 전송을 위한 FCM 토큰에 관한 클래스
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * 토큰이 갱신되었을 경우
     */
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        System.out.println();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);

        // 생성등록된 토큰을 개인 앱서버에 보내 저장해 두었다가 추가 뭔가를 하고 싶으면 할 수 있도록 한다.
        sendRegistrationToServer(token);
    }
    private void sendRegistrationToServer(String token) {
    }
}