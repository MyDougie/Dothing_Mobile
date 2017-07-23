package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.MainActivity;
import dvorak.kosta.com.dothing_mobile.dvorak.kosta.com.dothing_mobile.dto.LoginResultDTO;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-17.
 */

public class SignUpNetworkTask extends AsyncTask<Map<String, String>, Integer, String>  {

    Activity activity;

    public SignUpNetworkTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

        // Http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "android/signIn");

        // Parameter 를 전송한다.
        http.addAllParameters(maps[0]);

        //Http 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode();

        // 응답 본문 가져오기
        String body = post.getBody();

        return body;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("RESULT : ",s);

        Gson gson = new Gson();
        LoginResultDTO dto = gson.fromJson(s,LoginResultDTO.class);

        if(dto.getResult().equals("1")){
            Toast toast = Toast.makeText(activity,"회원가입이 되었습니다!",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(activity,MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        } else{
            Toast toast = Toast.makeText(activity,"회원가입 실패",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
