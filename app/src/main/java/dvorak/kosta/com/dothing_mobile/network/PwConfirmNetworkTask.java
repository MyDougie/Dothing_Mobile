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
 */

public class PwConfirmNetworkTask extends AsyncTask<Map<String, String>,Integer, String> {

    Activity activity;

    public PwConfirmNetworkTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

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
