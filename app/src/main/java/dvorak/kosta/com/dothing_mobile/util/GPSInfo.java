package dvorak.kosta.com.dothing_mobile.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.network.GPSNetworkTask;

import static android.content.Context.LOCATION_SERVICE;

/**
 * @brief : GPS에 관한 메소드들을 가지고 있는 메소드.
 * */
public class GPSInfo implements LocationListener {

    private final Context mContext;

    // 현재 GPS 사용유무
    boolean isGPSEnabled = false;

    // 네트워크 사용유무
    boolean isNetworkEnabled = false;

    // GPS 상태값
    boolean isGetLocation = false;

    Location location;
    double lat; // 위도
    double lon; // 경도

    // 최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    // 최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public GPSInfo(Context context) {
        this.mContext = context;
        getLocation();
    }

    /**
     * @brief : Location정보를 가져오는 메소드.
     * @return : Location - 위치정보를 가지고있는 location 객체 리턴.
     * */
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // GPS 정보 가져오기
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // 현재 네트워크 상태 값 알아오기
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            int gpsCheck = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION);

            if (gpsCheck == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(mContext, "위치권한을 허용하지 않으셨을 경우 앱사용에 제한이 있습니다.",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (!isGPSEnabled && !isNetworkEnabled) {
                    Toast.makeText(mContext, "네트워크에 혹은 GPS에 접속되지 않아 앱사용에 제한이 있습니다.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    this.isGetLocation = true;
                    // 네트워크 정보로 부터 위치값 가져오기
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                // 위도 경도 저장
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }

                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    lat = location.getLatitude();
                                    lon = location.getLongitude();
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    /**
     * @brief : GPS 종료하는 메소드
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSInfo.this);
        }
    }

    /**
     * @brief : 위도값을 리턴하는 메소드
     * @return : double - 위도값을 리턴.
     */
    public double getLatitude() {
        if (location != null) {
            lat = location.getLatitude();
        }
        return lat;
    }

    /**
     * @brief : 경도값을 리턴하는 메소드
     * @return : double - 경도값을 리턴.
     */
    public double getLongitude() {
        if (location != null) {
            lon = location.getLongitude();
        }
        return lon;
    }

    /**
     * @brief : GPS 나 wife 정보가 켜져있는지 확인합니다.
     * @return : true - 켜져있음, false - 꺼져있음
     */
    public boolean isGetLocation() {
        return this.isGetLocation;
    }


    /**
     * @brief : GPS가 변할때마다 실행되는 메소드
     * @param : Location location - 사용자의 위치정보를 가지고있는 객체
     * */
    public void onLocationChanged(Location location) {
        GPSNetworkTask gpsNetworkTask = new GPSNetworkTask("updateLocation");
        Map<String, String> params = new HashMap<>();
        params.put("id", MemberInfo.userId);
        params.put("latitude", location.getLatitude() +"");
        params.put("longitude", location.getLongitude() +"");
        gpsNetworkTask.execute(params);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }
}



