package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-17.
 */

public class ReplyInsertNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {

    private Map<String , String> map;
    Activity activity;
    public ReplyInsertNetworkTask() {
        super();
    }

    public ReplyInsertNetworkTask(Activity activity) {
        super();
        this.activity = activity;
    }

    /**
     * 본 작업을 쓰레드로 처리해준다. * @param params * @return
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/replyInsert"); // HTTP 요청 전송

        http.addAllParameters(maps[0]);
        HttpClient post = http.create();
        post.request(); // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode(); // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * doInBackground 종료되면 동작한다. * @param s : doInBackground가 리턴한 값이 들어온다.
     */
    @Override
    protected void onPostExecute(String s) {
        if(s.trim().equals("0")) {
            Toast.makeText(activity, "댓글 등록 실패", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(activity, "댓글 등록 성공", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
