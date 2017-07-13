package dvorak.kosta.com.dothing_mobile;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.dvorak.kosta.com.dothing_mobile.item.ErrandsItem;

public class TestActivity extends AppCompatActivity  implements LocationListener, MapView.MapViewEventListener{

    MapView mapView;
    LocationManager locationManager;
    String provider;
    ListViewAdapter adapter;
    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        Toast.makeText(getBaseContext(), "세팅완료! ",
                Toast.LENGTH_SHORT).show();
        /*int gpsCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(gpsCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }else{
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location == null){
                Log.d("NULL!", "널야");
            }else {
                Log.d("NULL!NOT", "널아니야" + location.getLatitude() + " : " + location.getLongitude());
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.26160889491439, 127.01722998172045), true);
            }
        }*/
        Map<String, String> params = new HashMap<>();
        NetworkTask networkTask = new NetworkTask();
        networkTask.execute(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("b80840fa249b76f3ea0e08b7f18d6751");
        mapView.setMapViewEventListener(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        adapter = new ListViewAdapter();
        ListView listView = (ListView)findViewById(R.id.errandList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ErrandsItem item = (ErrandsItem)parent.getItemAtPosition(position);
                int click = item.getClick();
                if(click == 0) {
                    item.setClick(1);
                    Toast.makeText(getBaseContext(), position + "번 선택됨!! ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class NetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
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
            HttpClient.Builder http = new HttpClient.Builder("POST", "http://172.30.1.48:8000/controller/android/errand"); // HTTP 요청 전송

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
                JSONArray jsonArray = new JSONArray(s);
                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Log.d(i+"번 심부름", obj.toString());
                    JSONObject posObj = obj.getJSONObject("errandsPos");
                    Log.d(i+"번 심부름의 lat", posObj.getDouble("latitude") + "");
                    Log.d(i+"번 심부름의 lng", posObj.getDouble("longitude") + "");
                    double latitude = posObj.getDouble("latitude");
                    double longitude = posObj.getDouble("longitude");
                    String title = obj.getString("title");
                    String content = obj.getString("content");
                    String productPrice = obj.getString("productPrice");
                    String errandPrice = obj.getString("errandsPrice");
                    String addr = posObj.getString("addr");
                    int errandNum = obj.getInt("errandsNum");
                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName(title);
                    marker.setTag(errandNum);
                    marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                    mapView.addPOIItem(marker);
                    adapter.addItem(ContextCompat.getDrawable(TestActivity.this, R.drawable.dothing_mark), title,
                            content,productPrice,errandPrice,addr);
                }
                adapter.notifyDataSetChanged();
            }catch(Exception e){
                Log.e("E", e.getMessage());
            }

        }

    }
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getBaseContext(), "위치감지! ",
                Toast.LENGTH_SHORT).show();
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude()), true);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }

}
