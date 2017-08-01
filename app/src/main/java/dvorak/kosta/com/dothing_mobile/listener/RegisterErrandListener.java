package dvorak.kosta.com.dothing_mobile.listener;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import dvorak.kosta.com.dothing_mobile.activity.ErrandRegisterActivity;

/**
 * Created by YTK on 2017-07-15.
 * @brief : 심부름 등록할 때 심부름 위치 이벤트를 받는 Class
 */
public class RegisterErrandListener implements MapView.MapViewEventListener {
    Context context;
    EditText registerAddr;
    ErrandRegisterActivity errandRegisterActivity;
    String latitude,longitude;
    public RegisterErrandListener(EditText registerAddr, ErrandRegisterActivity errandRegisterActivity, String latitude, String longitude){
        this.registerAddr = registerAddr;
        this.errandRegisterActivity = errandRegisterActivity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @brief : MapView가 사용 가능한 상태가 되었음을 알려준다.\n
     * onMapViewInitialized()가 호출된 이후에 MapView 객체가 제공하는 지도 조작 API들을 사용할 수 있다.
     * @param : MapView mapView MapView 객체
     * */
    @Override
    public void onMapViewInitialized(MapView mapView) {

        if(latitude != null) {
            // 마커 생성
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("심부름 받을 위치");
            marker.setTag(0);
            Double latitudeDouble = Double.parseDouble(latitude);
            Double longitudeDouble = Double.parseDouble(longitude);
            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitudeDouble, longitudeDouble);
            marker.setMapPoint(mapPoint);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            mapView.addPOIItem(marker);
            mapView.setMapCenterPoint(mapPoint, true);
        }
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    /**
     * @brief : 사용자가 지도 위를 터치한 경우 호출된다.
     * @param : MapView mapView MapView 객체
     * @param : MapPoint mapPoint Map에서 좌표를 나태내는 변수
     * */
    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        Log.e("errandListen", "clickled");
        mapView.removeAllPOIItems();
        mapView.setMapCenterPoint(mapPoint,true);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("심부름 받을 위치");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(marker);
        MapPoint.GeoCoordinate geo = mapPoint.getMapPointGeoCoord();
        errandRegisterActivity.latitude = geo.latitude + "";
        errandRegisterActivity.longitude = geo.longitude + "";
        MapReverseGeoCoder reverseGeoCoder = new MapReverseGeoCoder("6301c8d166630b078ad13401acc1267f", mapPoint, new MapReverseGeoCoder.ReverseGeoCodingResultListener() {
            @Override
            public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
                registerAddr.setText(s);
            }

            @Override
            public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

            }
        }, errandRegisterActivity);
        reverseGeoCoder.startFindingAddress();
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
}
