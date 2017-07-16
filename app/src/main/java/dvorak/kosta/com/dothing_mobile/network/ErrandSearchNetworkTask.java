package dvorak.kosta.com.dothing_mobile.network;

import android.os.AsyncTask;
import android.util.Log;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.activity.ErrandActivity;
import dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-13.
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
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "errandSearch"); // HTTP 요청 전송

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
            mapView.removeAllPOIItems();
            JSONArray jsonArray = new JSONArray(s);
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
//                    Log.d(i+"번 심부름", obj.toString());
                JSONObject posObj = obj.getJSONObject("errandsPos");
//                    Log.d(i+"번 심부름의 lat", posObj.getDouble("latitude") + "");
//                    Log.d(i+"번 심부름의 lng", posObj.getDouble("longitude") + "");
                double latitude = posObj.getDouble("latitude");
                double longitude = posObj.getDouble("longitude");
                String title = obj.getString("title");
                String errandPrice = obj.getString("errandsPrice");
                String addr = posObj.getString("addr");
                String lat = posObj.getString("latitude");
                String lng = posObj.getString("longitude");
                String errandTime = obj.getString("endTime");
                int errandNum = obj.getInt("errandsNum");
                MapPOIItem marker = new MapPOIItem();
                marker.setItemName(title);
                marker.setTag(errandNum);
                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
                marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                mapView.addPOIItem(marker);
                adapter.addItem(title, errandPrice,addr, lat,lng,errandTime) ;
            }
            adapter.notifyDataSetChanged();
        }catch(Exception e){
            Log.e("E", e.getMessage());
        }

    }

}