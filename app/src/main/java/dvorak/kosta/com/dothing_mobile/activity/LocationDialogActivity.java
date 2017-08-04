package dvorak.kosta.com.dothing_mobile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import dvorak.kosta.com.dothing_mobile.R;

/**
 * 상대방 위치를 보여주는 엑티비티
 */
public class LocationDialogActivity extends Activity implements MapView.MapViewEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener{
    TextView chatMapText;
    MapView mapView;
    ViewGroup mapViewContainer;
    String latitude, longitude, time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chat_map);
        latitude = getIntent().getExtras().getString("latitude");
        longitude = getIntent().getExtras().getString("longitude");
        time = getIntent().getExtras().getString("time");
        chatMapText = (TextView)findViewById(R.id.chat_map_text);

    }

    /**
     * 엑티비티 재시작시 맵뷰 생성
     */
    @Override
    protected void onResume() {
        super.onResume();
        // 맵뷰 셋팅
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("6301c8d166630b078ad13401acc1267f");
        mapView.setMapViewEventListener(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.chat_map);
        mapViewContainer.addView(mapView);
    }

    /**
     * 엑티비티 중지시 맵뷰 제거
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
    }

    /**
     * 맵뷰 시작시 상대방 위치 표시
     * @param : mapView
     */
    @Override
    public void onMapViewInitialized(MapView mapView) {
        if(latitude.equals("0")) {
            chatMapText.setText("상대방의 위치를 찾을 수 없습니다.(등록되지 않음)");
        }else {

            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("상대방의 현재 위치");
            marker.setTag(0);
            Double latitudeDouble = Double.parseDouble(latitude);
            Double longitudeDouble = Double.parseDouble(longitude);
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitudeDouble, longitudeDouble);
            marker.setMapPoint(mapPoint);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            mapView.addPOIItem(marker);
            mapView.setMapCenterPoint(mapPoint, true);
            MapReverseGeoCoder reverseGeoCoder = new MapReverseGeoCoder("6301c8d166630b078ad13401acc1267f", mapPoint, this, this);
            reverseGeoCoder.startFindingAddress();
        }
    }

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
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        chatMapText.setText(time + "경 상대방의 위치: \n" + s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        chatMapText.setText(time + "경 상대방의 위치");
    }
}
