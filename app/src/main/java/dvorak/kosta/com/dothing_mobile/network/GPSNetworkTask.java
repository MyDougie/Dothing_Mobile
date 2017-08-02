package dvorak.kosta.com.dothing_mobile.network;

import android.os.AsyncTask;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by YTK on 2017-07-23.
 * User의 GPS 정보를 Update 하는 NetWorkTask Class
 */

public class GPSNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
    String urlPath;

    public GPSNetworkTask(String urlPath){
        this.urlPath = urlPath;
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
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidMember/" + urlPath); // HTTP 요청 전송

        http.addAllParameters(maps[0]);
        HttpClient post = http.create();
        post.request(); // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode(); // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }
}
