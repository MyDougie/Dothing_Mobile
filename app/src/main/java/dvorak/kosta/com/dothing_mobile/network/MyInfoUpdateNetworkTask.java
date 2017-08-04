package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.MyInfoUpdateActivity;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by crw12 on 2017-07-26.
 * User의 개인 정보를 수정하기 위해서 원래 User의 정보를 가져오는 NetworkTask Class
 */

public class MyInfoUpdateNetworkTask extends AsyncTask<Map<String, Object>,Integer, String> {
    String urlPath;
    Activity activity;

    public MyInfoUpdateNetworkTask(Activity activity, String urlPath){
        this.activity = activity;
        this.urlPath = urlPath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground (Map<String, Object>... params) {



        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlPath);
        try {
            MultipartEntity reqEntity = new MultipartEntity();
            Iterator<String> iter = params[0].keySet().iterator(); // 맵에 여러개의 값들이 있겠죠?

            while(iter.hasNext()) { // 맵에 값이 여러개니깐 MultipartEntity라는 박스에 담아서 전송해야됨
                String key = iter.next();
                Object value = params[0].get(key);
                if(value instanceof String) { // 밸류가 문자일경우
                    reqEntity.addPart(key, new StringBody((String)value, Charset.forName("UTF-8")));
                    Log.e("추가됨", key + " " + value.toString());
                }else if(value instanceof File) { // 밸류가 이미지(파일)일 경우
                    reqEntity.addPart(key, new FileBody((File)value));
                    Log.e("추가됨","파일추가됨");
                }
            }
            httpPost.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(httpPost);

        }catch(Exception e){
            e.printStackTrace();
        }

        /*//http 요청 준비 작업
        dvorak.kosta.com.dothing_mobile.HttpClient.Builder http = new dvorak.kosta.com.dothing_mobile.HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidMember/myinfoUpdate");
        dvorak.kosta.com.dothing_mobile.HttpClient post = http.create();
        *//*http.addAllParameters(params[0]);
        http.addOrReplace();*//*
        post.request();

        //응답 상태 코드 가져오기
        int statusCode = post.getHttpStatusCode();

        //응답 본문
        String body = post.getBody();*/
        return "";
        //Log.d("이건가 -->", body);

    }
        /* //http 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr+"androidMember/myinfoUpdate");
        //http.addAllParameters();
        //http 요청 전송
        HttpClient post = http.create();
        post.request();

        //응답 상태 코드 가져오기
        int statusCode = post.getHttpStatusCode();

        //응답 본문
        String body = post.getBody();

        return body;*/


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("뭐지 --> ",s);

       /* if(s.trim().equals("")) {
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

        }*/

    }
}
