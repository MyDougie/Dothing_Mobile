package dvorak.kosta.com.dothing_mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import net.daum.mf.map.api.MapView;

import dvorak.kosta.com.dothing_mobile.R;

public class ErrandRegisterAddrActivity extends AppCompatActivity {
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    MapView mapView;
    ViewGroup mapViewContainer;
    EditText registerAddr;
    EditText registerDetailAddr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errand_register_addr);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("6301c8d166630b078ad13401acc1267f");
        mapViewContainer = (ViewGroup) findViewById(R.id.registerMap);
        mapViewContainer.addView(mapView);
        registerAddr = (EditText)findViewById(R.id.registerAddr);
        registerDetailAddr = (EditText)findViewById((R.id.registerDetailAddr));

        registerAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ErrandRegisterAddrActivity.this, AddrWebviewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });
    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null)
                        registerAddr.setText(data);
                }
                break;

        }

    }
}
