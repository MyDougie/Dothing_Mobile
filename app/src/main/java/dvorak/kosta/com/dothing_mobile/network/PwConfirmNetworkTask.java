package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Map;

//import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.activity.MyInfoUpdateActivity;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by crw12 on 2017-07-26.
 * User의 개인정보를 수정하기전 비밀번호 정보 비교를 위한 NetworkTask Class
 */

public class PwConfirmNetworkTask extends AsyncTask<Map<String, String>,Integer, String> {

    Activity activity;

    public PwConfirmNetworkTask(Activity activity){
        this.activity = activity;
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
     * @param params 웹으로 보내는 params
     * @return String
     */
    @Override
    protected String doInBackground(Map<String, String>... params) {
        //http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr+"androidMember/pwConfirm");
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

    /**
     * UI 스레드 상에서 실행되며, doInBackground() 종료 후 호출됨. \n
     * controller에서 비교 후 s의 값이 있는 것과 없는 것으로 개인정보를 수정 할 수 있게 한다.
     * @param s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s.trim().equals("")) {
            Toast.makeText(activity,"비밀번호가틀렸습니다",Toast.LENGTH_SHORT).show();
        }else {
            try {

                JSONObject jsonObject = new JSONObject(s);
                MemberInfo.userId = jsonObject.getString("userId");
                MemberInfo.name = jsonObject.getString("name");
                MemberInfo.password = jsonObject.getString("password");
                MemberInfo.selfImgUrlPath = ConstantUtil.ipAddr + "users/" + MemberInfo.userId + "/" + jsonObject.getString("selfImg");
                Log.e("pathURLIMG", MemberInfo.selfImgUrlPath);
                MemberInfo.preAddr = jsonObject.getString("preAddr");
                MemberInfo.detailAddr = jsonObject.getString("detailAddr");
                MemberInfo.sex = jsonObject.getString("sex");
                MemberInfo.joinDate = jsonObject.getString("joinDate");
                MemberInfo.introduce = jsonObject.getString("introduce");
                MemberInfo.latitude = jsonObject.getString("latitude");
                MemberInfo.longitude = jsonObject.getString("longitude");
                MemberInfo.currentPoint = jsonObject.getJSONObject("point").getString("currentPoint");


                Intent intent = new Intent(activity, MyInfoUpdateActivity.class);
                activity.startActivity(intent);
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
