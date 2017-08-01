package dvorak.kosta.com.dothing_mobile.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.LocationDialogActivity;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by YTK on 2017-07-23.
 */

public class ChatMapNetworkTask extends AsyncTask<Map<String, String>, Integer, String>{
    Context context;


    public ChatMapNetworkTask(Context context){
        this.context = context;
    }
    /**
     * doInBackground 실행되기 이전에 동작한다.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 본 작업을 쓰레드로 처리해준다. * @param params * @return
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidMember/requestLocation"); // HTTP 요청 전송

        http.addAllParameters(maps[0]);
        HttpClient post = http.create();
        post.request(); // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode(); // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }

    /**
     * doInBackground 종료되면 동작한다. * @param s : doInBackground가 리턴한 값이 들어온다.
     */
    @Override
    protected void onPostExecute(String s) {
        Log.d("HTTP_RESULT", s);
        try {
            JSONObject obj = new JSONObject(s);
            String time = obj.getString("time");
            String latitude = obj.getString("latitude");
            String longitude = obj.getString("longitude");
            Log.e("채팅지도검색",  "결과: " + time + ":" + latitude + ":" + longitude);
            Intent intent = new Intent(context, LocationDialogActivity.class);
            intent.putExtra("time", time);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            context.startActivity(intent);
        }catch(Exception e){
            Log.e("E", e.getMessage());
        }

    }
}
