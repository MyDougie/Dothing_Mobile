package dvorak.kosta.com.dothing_mobile.listener;

import android.graphics.Color;
import android.util.Log;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.ErrandActivity;
import dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.network.ErrandSearchNetworkTask;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-13.
 * 심부름을 검색 할때 심부름 위치 이벤트를 받는 Class
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


    /**
     * 맵이 드래그 됐을 때 심부름 위치를 받아 오는 메소드
     * @param mapView MapView 객체
     * @param mapPoint Map에서 좌표를 나태내는 변수
     * */
    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        mapView.removeAllCircles();
        ErrandSearchNetworkTask networkTask = new ErrandSearchNetworkTask(adapter, mapView, errandActivity);
        MapPoint.GeoCoordinate geo = mapPoint.getMapPointGeoCoord();
        Map<String, String> params = new HashMap<>();
        params.put("lat", geo.latitude + "");
        params.put("lng", geo.longitude + "");
        params.put("distance", ConstantUtil.SEARCH_DISTANCE);
        networkTask.execute(params);
        MapCircle circle1 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(geo.latitude, geo.longitude), // center
                Integer.parseInt(ConstantUtil.SEARCH_DISTANCE) * 1000, // radius
                Color.argb(128, 255, 0, 0), // strokeColor
                Color.argb(200, 212, 244, 250) // fillColor
        );
        mapView.addCircle(circle1);
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    /**
     * MapView가 사용 가능한 상태가 되었음을 알려준다.\n
     * onMapViewInitialized()가 호출된 이후에 MapView 객체가 제공하는 지도 조작 API들을 사용할 수 있다.
     * @param mapView MapView 객체
     * */
    @Override
    public void onMapViewInitialized(MapView mapView) {
        Log.e("맵", "초기화 완료");
        Map<String, String> params = new HashMap<>();
        ErrandSearchNetworkTask networkTask = new ErrandSearchNetworkTask(adapter, mapView, errandActivity);
        MapPoint.GeoCoordinate geo = mapView.getMapCenterPoint().getMapPointGeoCoord();
        // 현재의 위도와 경도로 심부름을 검색
        params.put("lat", geo.latitude+ "");
        params.put("lng", geo.longitude + "");
        params.put("distance", ConstantUtil.SEARCH_DISTANCE);
        networkTask.execute(params);
        MapCircle circle1 = new MapCircle(
                MapPoint.mapPointWithGeoCoord(geo.latitude, geo.longitude), // center
                Integer.parseInt(ConstantUtil.SEARCH_DISTANCE) * 1000, // radius
                Color.argb(128, 255, 0, 0), // strokeColor
                Color.argb(200, 212, 244, 250) // fillColor
        );
        mapView.addCircle(circle1);
    }
}
