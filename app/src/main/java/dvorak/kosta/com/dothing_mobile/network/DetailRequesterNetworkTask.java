package dvorak.kosta.com.dothing_mobile.network;

import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
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
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/detailRequester"); // HTTP 요청 전송

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
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                //                    Log.d(i+"번 심부름", obj.toString());
                JSONObject posObj = obj.getJSONObject("errandsPos");
                //                    Log.d(i+"번 심부름의 lat", posObj.getDouble("latitude") + "");
                //                    Log.d(i+"번 심부름의 lng", posObj.getDouble("longitude") + "");
                double latitude = posObj.getDouble("latitude");
                double longitude = posObj.getDouble("longitude");
                String requesterName = obj.getString("requesterName");
                String errandsCount = obj.getString("errandsCount");
                String starGrade = obj.getString("starGrade");
                JSONArray hashTags = obj.getJSONArray("hashTags");

                int errandNum = obj.getInt("errandsNum");

               // ((ImageView)map.get("requesterUserImg")).setImage
                ((EditText)map.get("requesterName")).setText(requesterName);
                ((EditText)map.get("errandsRequestCount")).setText(errandsCount);
                ((EditText)map.get("grade")).setText(starGrade);
                String hash = "";
                for(int j=0; j<hashTags.length(); j++){
                   hash += hashTags.getJSONObject(j);
                }
                ((EditText)map.get("hashtag")).setText(hash);

            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
