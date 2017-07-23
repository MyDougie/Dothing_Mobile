package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
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

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by YTK on 2017-07-15.
 */

public class UploadDataNetworkTask extends AsyncTask<Map<String, Object>, Integer, String> {
    String urlPath;
    Activity activity;

    //전달 인자값 -> urlPath: 서버 주소 / activity -> 실행되고있는 액티비티
    public  UploadDataNetworkTask(String urlPath, Activity activity){
        this.urlPath = urlPath;
        this.activity = activity;
    }

    /**
     * maps의 key값 -> 스프링 서버에서의 변수 이름
     * maps의 밸류값 -> 스프링 서버에서의 변수 내용(ex key -> test value -> "안녕"이면 컨트롤러의 파라미터에서 String test 로 받고 sysout찍으면 안녕이 찍힘)
     */
    @Override
    protected String doInBackground(Map<String, Object>... maps) { // 내가 전송하고 싶은 파라미터
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlPath);
        try {
        MultipartEntity reqEntity = new MultipartEntity();
        Iterator<String> iter = maps[0].keySet().iterator(); // 맵에 여러개의 값들이 있겠죠?

        while(iter.hasNext()) { // 맵에 값이 여러개니깐 MultipartEntity라는 박스에 담아서 전송해야됨
            String key = iter.next();
            Object value = maps[0].get(key);
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
        return "OK";
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(activity, "전송 완료하였습니다.", Toast.LENGTH_SHORT).show();
        activity.finish();

    }

}
