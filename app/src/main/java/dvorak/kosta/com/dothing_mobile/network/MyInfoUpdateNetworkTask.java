package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by crw12 on 2017-07-26.
 */

public class MyInfoUpdateNetworkTask extends AsyncTask<Map<String, String>,Integer, String> {

    Activity activity;

    public MyInfoUpdateNetworkTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.i("제발","들어옴");
    }

    @Override
    protected String doInBackground(Map<String, String>... params) {
        //http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr+"androidMember/myinfoUpdate");
        http.addAllParameters(params[0]);
        //http 요청 전송
        HttpClient post = http.create();
        post.request();

        //응답 상태 코드 가져오기
        int statusCode = post.getHttpStatusCode();

        //응답 본문
        String body = post.getBody();

        return body;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Http_result",s);
    }
}
