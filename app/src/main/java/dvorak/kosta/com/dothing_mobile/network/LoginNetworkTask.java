package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.activity.ChatTestActivity;
import dvorak.kosta.com.dothing_mobile.activity.DetailViewActivity;
import dvorak.kosta.com.dothing_mobile.activity.FrameActivity;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-17.
 */

public class LoginNetworkTask extends AsyncTask<Map<String,String>,String,String>  {
    Activity activity;
    SharedPreferences auto;
    SharedPreferences.Editor autoLogin;
    String click, errandsNum;
    public LoginNetworkTask(Activity activity, String click, String errandsNum){
        this.activity = activity;
        this.errandsNum = errandsNum;
        this.click = click;
    }

    /** * doInBackground 실행되기 이전에 동작한다. */
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    /** * 본 작업을 쓰레드로 처리해준다. * @param params * @return */
    protected String doInBackground(Map<String,String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidMember/checkId");
        http.addAllParameters(maps[0]);
        String password = maps[0].get("password");
        auto = activity.getSharedPreferences("auto",Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        autoLogin.putString("LoginPassword",password);

        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode();
        // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }
    /** * doInBackground 종료되면 동작한다. * @param s : doInBackground가 리턴한 값이 들어온다. */
    protected void onPostExecute(String s) {

        if(s.trim().equals("")) {
            Toast.makeText(activity,"로그인 실패",Toast.LENGTH_SHORT).show();
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
                MemberInfo.ssnImg = jsonObject.getString("ssnImg");
                MemberInfo.joinDate = jsonObject.getString("joinDate");
                MemberInfo.introduce = jsonObject.getString("introduce");
                MemberInfo.latitude = jsonObject.getString("latitude");
                MemberInfo.longitude = jsonObject.getString("longitude");
                MemberInfo.currentPoint = jsonObject.getJSONObject("point").getString("currentPoint");
                MemberInfo.auth = jsonObject.getInt("auth");
                JSONArray jsonArray = jsonObject.getJSONArray("gpaList");
                int totalRes = 0, totalReq = 0;
                int totalResSum = 0, totalReqSum = 0;
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject gpa = jsonArray.getJSONObject(i);
                    int responseAccuracy = gpa.getInt("responseAccuracy");
                    int responseSpeed = gpa.getInt("responseSpeed");
                    int responseKindness = gpa.getInt("responseKindness");
                    int requestManners = gpa.getInt("requestManners");
                    if(responseAccuracy != 0){
                        totalResSum = (responseAccuracy + responseKindness + responseSpeed) / 3;
                        totalRes++;
                    }else {
                        totalReqSum = requestManners;
                        totalReq++;
                    }
                }
                int avg = 0;
                if(totalReq != 0) avg += totalReqSum / totalReq;
                if(totalRes != 0) avg += totalResSum / totalRes;
                if(totalRes == 0 || totalReq == 0) MemberInfo.averageGPA = avg;
                else if(totalRes != 0 && totalReq != 0) MemberInfo.averageGPA = avg/2;
                else if(totalRes == 0 && totalReq ==0) MemberInfo.averageGPA = 0;
                autoLogin.putString("LoginId",jsonObject.getString("userId"));

                autoLogin.commit();
                Intent intent = null;
                if(click == null) {
                    intent = new Intent(activity, FrameActivity.class);
                }else if(click.equals("DETAIL_ACTIVITY")){
                    intent = new Intent(activity, DetailViewActivity.class);
                    intent.putExtra("errandsNum", errandsNum);
                }else if(click.equals("CHAT_ACTIVITY")){
                    intent = new Intent(activity, ChatTestActivity.class);
                    intent.putExtra("errandsNum", errandsNum);
                }
                activity.startActivity(intent);
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
