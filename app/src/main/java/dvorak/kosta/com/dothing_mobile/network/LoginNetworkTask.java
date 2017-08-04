package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.LoginActivity;
import dvorak.kosta.com.dothing_mobile.activity.ChatTestActivity;
import dvorak.kosta.com.dothing_mobile.activity.DetailViewActivity;
import dvorak.kosta.com.dothing_mobile.activity.LoginApiActivity;
import dvorak.kosta.com.dothing_mobile.activity.FrameActivity;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-17.
 * Login NetworkTask Class
 */

public class LoginNetworkTask extends AsyncTask<Map<String,String>,String,String>  {
    Activity activity;
    SharedPreferences auto;
    SharedPreferences.Editor autoLogin;
    String click, errandsNum, requestUserId;
    String name, email, gender, selfImg, isApi, password;
    public LoginNetworkTask(Activity activity, String click, String errandsNum, String requestUserId){

        this.activity = activity;
        this.errandsNum = errandsNum;
        this.click = click;
        this.requestUserId =requestUserId;
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
    protected String doInBackground(Map<String,String>... maps) {
        password = maps[0].get("password");
        email = maps[0].get("userId");
        name = maps[0].get("name");
        gender = maps[0].get("gender");
        selfImg = maps[0].get("selfImg");
        isApi = maps[0].get("isApi");

        String url="";

        if("true".equals(isApi)){
            url="androidMember/apiCheckId";
        } else{
            url= "androidMember/checkId";
        }

        // HTTP 요청 준비 작업

        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidMember/checkId");

        //HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + url);

        http.addAllParameters(maps[0]);

        // HTTP 요청 전송
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
     * isApi로 비교하여 로그인을 판단하고 User의 정보를 MemberInfo에 저장해준다.
     * @param s doInBackground()에서 return한 parameter
     * */
    protected void onPostExecute(String s) {

        if(s.trim().equals("")) {
            if(!"true".equals(isApi))
                Toast.makeText(activity,"로그인 실패",Toast.LENGTH_SHORT).show();
            if("true".equals(isApi)) {
                Toast.makeText(activity,"추가 정보를 입력해주세요!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, LoginApiActivity.class);
                intent.putExtra("userId", email);
                intent.putExtra("gender", gender);
                intent.putExtra("id", password);
                intent.putExtra("name", name);
                intent.putExtra("selfImg", selfImg);
                activity.startActivity(intent);
                activity.finish();
            }
        }else {

            try {
                if(LoginActivity.progressBar != null)
                    LoginActivity.progressBar.setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject(s);
                MemberInfo.userId = jsonObject.getString("userId");
                MemberInfo.name = jsonObject.getString("name");
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


                auto = activity.getSharedPreferences("auto", Activity.MODE_PRIVATE);
                autoLogin = auto.edit();
                autoLogin.putString("LoginPassword", password);
                autoLogin.putString("LoginId",jsonObject.getString("userId"));

                autoLogin.commit();
                Intent intent = null;
                Log.i("인자들", click + " : " + errandsNum + " : " + requestUserId);
                if(click == null) {
                    intent = new Intent(activity, FrameActivity.class);
                }else if(click.equals("DETAIL_ACTIVITY")){
                    intent = new Intent(activity, DetailViewActivity.class);
                    intent.putExtra("errandNum", errandsNum);
                    intent.putExtra("requestUserId", requestUserId);
                }else if(click.equals("CHAT_ACTIVITY")){
                    intent = new Intent(activity, ChatTestActivity.class);
                    intent.putExtra("errandsNum", errandsNum);
                }
                if(LoginActivity.progressBar != null)
                LoginActivity.progressBar.setVisibility(View.GONE);
                activity.startActivity(intent);
                activity.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
