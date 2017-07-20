package dvorak.kosta.com.dothing_mobile.network;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-17.
 */

public class DetailRequesterNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {

    private String errandNum;
    private Map<String , View> map;

    public DetailRequesterNetworkTask() {
        super();
    }

    public DetailRequesterNetworkTask(String errandNum, Map<String, View> map) {
        super();
        this.errandNum = errandNum;
        this.map = map;
    }

    /**
     * 본 작업을 쓰레드로 처리해준다. * @param params * @return
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/requesterDetail"); // HTTP 요청 전송

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
        try {
            JSONObject obj = new JSONObject(s);
            String requestId = obj.getString("requesterId");
            int requestCount = obj.getInt("requestCount");
            int grade = obj.getInt("grade");
            JSONArray hashtagList = obj.getJSONArray("hashtagList");
            String hash = "";
            for(int i=0; i<hashtagList.length(); i++){
                hash += hashtagList.get(i) + " ";
            }

          //  Log.i("requestId : ", requestId );
          //  Log.i("requestCount : ", requestCount+"" );
            Log.i("grade :", grade+"" );
           // Log.i("hash :", hash);
            View v = map.get("view");
            // ((ImageView)map.get("requesterUserImg")).setImage
            ((EditText)v.findViewById(R.id.requester_id)).setText(requestId);
            ((EditText)v.findViewById(R.id.errands_request_count)).setText(requestCount+"");
            ((EditText)v.findViewById(R.id.grade)).setText(grade+"");
            //((EditText)v.findViewById(R.id.hashtag)).setText(grade+"");






        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
