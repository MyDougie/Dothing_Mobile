package dvorak.kosta.com.dothing_mobile.network;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.ErrandActivity;
import dvorak.kosta.com.dothing_mobile.activity.TutorialActivity;
import dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

import static android.content.Context.MODE_PRIVATE;
import static dvorak.kosta.com.dothing_mobile.activity.ErrandActivity.tutorial;

/**
 * Created by Administrator on 2017-07-13.
 * Map에 심부름 목록을 가져오는 NetWorkTask Class
 */
public class ErrandSearchNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
    ListViewAdapter adapter;
    MapView mapView;
    ErrandActivity errandActivity;

    public ErrandSearchNetworkTask(ListViewAdapter adapter, MapView mapView, ErrandActivity errandActivity){
        this.adapter = adapter;
        this.mapView = mapView;
        this.errandActivity = errandActivity;
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
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/errandSearch"); // HTTP 요청 전송

        http.addAllParameters(maps[0]);
        HttpClient post = http.create();
        post.request(); // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode(); // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }

    /**
     * UI 스레드 상에서 실행되며, doInBackground() 종료 후 호출됨. \n
     * s로 심부름 목록에 대한 정보들을 받아와서 Map에 보여준다
     * @param s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        try {
            adapter.removeItem();
            mapView.removeAllPOIItems();
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

                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(title);
                marker.setTag(errandNum);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                mapView.addPOIItem(marker);
                adapter.addItem(requesteUserId, errandNum, title, errandPrice,addr, lat,lng,errandTime, replyArray.length()) ;

            }
            adapter.notifyDataSetChanged();

            if(tutorial == 1) {
                errandActivity.startActivity(new Intent(errandActivity, TutorialActivity.class));
                SharedPreferences pref = errandActivity.getSharedPreferences("tutorial", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("tutorial", 0);
                editor.commit();

                tutorial--;
            }
        }catch(Exception e){
            Log.e("E", e.getMessage());
        }

    }

}