package dvorak.kosta.com.dothing_mobile.listener;

import android.util.Log;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.ErrandActivity;
import dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.network.ErrandSearchNetworkTask;

/**
 * Created by Administrator on 2017-07-13.
 */

public class MapErrandsListener implements MapView.MapViewEventListener {
    ListViewAdapter adapter;
    ErrandActivity errandActivity;

    public MapErrandsListener(ListViewAdapter adapter, ErrandActivity errandActivity){
        this.adapter = adapter;
        this.errandActivity = errandActivity;
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

    //맵이 드래그됬을 때 심부름을 받아옴
    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        Log.e("맵", "드래그 인식");
        ErrandSearchNetworkTask networkTask = new ErrandSearchNetworkTask(adapter, mapView, errandActivity);
        MapPoint.GeoCoordinate geo = mapPoint.getMapPointGeoCoord();
        Map<String, String> params = new HashMap<>();
        params.put("lat", geo.latitude + "");
        params.put("lng", geo.longitude + "");
        networkTask.execute(params);
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {
        Log.e("맵", "초기화 완료");
        Map<String, String> params = new HashMap<>();
        ErrandSearchNetworkTask networkTask = new ErrandSearchNetworkTask(adapter, mapView, errandActivity);
        MapPoint.GeoCoordinate geo = mapView.getMapCenterPoint().getMapPointGeoCoord();
        // 현재의 위도와 경도로 심부름을 검색
        params.put("lat", geo.latitude+ "");
        params.put("lng", geo.longitude + "");
        networkTask.execute(params);
    }
}
