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
 * 채팅 실시간 상대방 위치 검색 NetWorkTask Class
 */

public class ChatMapNetworkTask extends AsyncTask<Map<String, String>, Integer, String>{
    Context context;


    public ChatMapNetworkTask(Context context){
        this.context = context;
    }

    /**
     * background을 실행하기 전 준비 단계 메소드
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 네트워크 기능을 background 스레드로 처리하는 메소드
     * @param maps 웹으로 보내는 params
     * @return String
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
     * UI 스레드 상에서 실행되며, doInBackground() 종료 후 호출됨. \n
     * s로 시간과 위도, 경도를 받아서 위치를 보여준다.
     * @param s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject obj = new JSONObject(s);
            String time = obj.getString("time");
            String latitude = obj.getString("latitude");
            String longitude = obj.getString("longitude");
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
