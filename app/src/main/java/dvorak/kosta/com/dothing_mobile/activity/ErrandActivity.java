package dvorak.kosta.com.dothing_mobile.activity;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.item.ErrandsItem;
import dvorak.kosta.com.dothing_mobile.listener.MapErrandsListener;

public class ErrandActivity extends AppCompatActivity  implements LocationListener{

    MapErrandsListener mapErrandsListener;
    MapView mapView;
    ListViewAdapter adapter = new ListViewAdapter();
//    LocationManager locationManager;
//    String provider;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mapErrandsListener = new MapErrandsListener(adapter, this);
        // 맵뷰 셋팅
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("6301c8d166630b078ad13401acc1267f");
        mapView.setMapViewEventListener(mapErrandsListener);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        //리스트뷰 셋팅
        ListView listView = (ListView)findViewById(R.id.errandList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ErrandsItem item = (ErrandsItem)parent.getItemAtPosition(position);
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude())), true);
                int click = item.getClick();
                if(click == 0) {
                    item.setClick(1);
                    Toast.makeText(getBaseContext(), position + "번 선택됨!! ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


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
