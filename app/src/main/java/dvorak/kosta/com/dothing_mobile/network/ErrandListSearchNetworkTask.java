package dvorak.kosta.com.dothing_mobile.network;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.ErrandsListActivity;
import dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-13.
 * @brief : List에 심부름 목록을 가져오는 NetWorkTask Class
 */
public class ErrandListSearchNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
    ListViewAdapter adapter;
    public ErrandListSearchNetworkTask(ListViewAdapter adapter){
        this.adapter = adapter;
    }

    /**
     * @brief : background을 실행하기 전 준비 단계 메소드
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * @brief : 네트워크 기능을 background 스레드로 처리하는 메소드
     * @param : Map<String,String> maps 웹으로 보내는 params
     * @return : String
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/errandListSearch"); // HTTP 요청 전송

        http.addAllParameters(maps[0]);
        HttpClient post = http.create();
        post.request(); // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode(); // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }

    /**
     * @brief : UI 스레드 상에서 실행되며, doInBackground() 종료 후 호출됨. \n
     * s로 심부름 목록에 대한 정보들을 받아와서 보여준다
     * @param : String s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        try {

            adapter.removeItem();
            JSONArray jsonArray = new JSONArray(s);
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject posObj = obj.getJSONObject("errandsPos");
                JSONArray replyArray = obj.getJSONArray("errandsReply");
                double latitude = posObj.getDouble("latitude");
                double longitude = posObj.getDouble("longitude");
                String title = obj.getString("title");
                String errandPrice = obj.getString("errandsPrice");
                String addr = posObj.getString("addr");
                String lat = posObj.getString("latitude");
                String lng = posObj.getString("longitude");
                String errandTime = obj.getString("endTime");
                int errandNum = obj.getInt("errandsNum");

                JSONObject requestUser = obj.getJSONObject("requestUser");
                String requesteUserId = requestUser.getString("userId");

                adapter.addItem(requesteUserId, errandNum, title, errandPrice,addr, lat,lng,errandTime, replyArray.length()) ;

            }
            ErrandsListActivity.progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }catch(Exception e){
            Log.e("E", e.getMessage());
        }

    }

}