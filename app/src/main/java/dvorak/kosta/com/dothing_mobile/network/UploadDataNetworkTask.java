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
 * 가입할때 User의 정보를 등록하기 위한 NetWorkTask Class
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
     * 네트워크 기능을 background 스레드로 처리하는 메소드
     * @param maps 웹으로 보내는 params
     * @return String
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
