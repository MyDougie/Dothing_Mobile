package dvorak.kosta.com.dothing_mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;

public class MainViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainview);

        MapView mapView = new MapView(this);
        mapView.setDaumMapApiKey("b80840fa249b76f3ea0e08b7f18d6751");

        ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);

        mapViewContainer.addView(mapView);

    }

}
