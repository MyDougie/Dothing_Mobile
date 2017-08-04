package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.LoginActivity;
import dvorak.kosta.com.dothing_mobile.dto.LoginResultDTO;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-17.
 * User의 회원가입 후 등록을 위한 NetWorkTask Class
 */

public class SignUpNetworkTask extends AsyncTask<Map<String, String>, Integer, String>  {

    Activity activity;

    public SignUpNetworkTask(Activity activity){
        this.activity = activity;
    }

    /**
     * 네트워크 기능을 background 스레드로 처리하는 메소드
     * @param maps 웹으로 보내는 params
     * @return String
     */
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

    /**
     * UI 스레드 상에서 실행되며, doInBackground() 종료 후 호출됨. \n
     * s에 성공여부 값이 넘어오며 LoginResultDTO의 값과 비교하여 가입 여부를 보여준다.
     * @param s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        Log.d("RESULT : ",s);

        Gson gson = new Gson();
        LoginResultDTO dto = gson.fromJson(s,LoginResultDTO.class);

        if(dto.getResult().equals("1")){
            Toast toast = Toast.makeText(activity,"회원가입이 되었습니다!",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            activity.finish();
        } else{
            Toast toast = Toast.makeText(activity,"회원가입 실패",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
