package dvorak.kosta.com.dothing_mobile.network;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.activity.ErrandsListActivity;
import dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-13.
 */

public class ErrandListSearchNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
    ListViewAdapter adapter;
    public ErrandListSearchNetworkTask(ListViewAdapter adapter){
        this.adapter = adapter;
    }
    /**
     * doInBackground 실행되기 이전에 동작한다.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 본 작업을 쓰레드로 처리해준다. * @param params * @return
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
     * doInBackground 종료되면 동작한다. * @param s : doInBackground가 리턴한 값이 들어온다.
     */
    @Override
    protected void onPostExecute(String s) {
        Log.d("HTTP_RESULT", s);
        try {

            adapter.removeItem();
            JSONArray jsonArray = new JSONArray(s);
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
//                    Log.d(i+"번 심부름", obj.toString());
                JSONObject posObj = obj.getJSONObject("errandsPos");
//                    Log.d(i+"번 심부름의 lat", posObj.getDouble("latitude") + "");
//                    Log.d(i+"번 심부름의 lng", posObj.getDouble("longitude") + "");
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
/*

                JSONObject requestUser = obj.getJSONObject("requestUser");
                String requesteUserId = requestUser.getString("requesteUserId");
                Log.i("requestUserIdxxxxx", requesteUserId);
*/

               // adapter.addItem(title, errandPrice,addr, lat,lng,errandTime) ;
                adapter.addItem(errandNum, title, errandPrice,addr, lat,lng,errandTime, replyArray.length()) ;
            }
            ErrandsListActivity.progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }catch(Exception e){
            Log.e("E", e.getMessage());
        }

    }

}