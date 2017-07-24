package dvorak.kosta.com.dothing_mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.item.ErrandsItem;
import dvorak.kosta.com.dothing_mobile.listener.MapErrandsListener;

public class ErrandActivity extends AppCompatActivity{

    MapErrandsListener mapErrandsListener;
    MapView mapView;
    ListViewAdapter adapter = new ListViewAdapter();
    ViewGroup mapViewContainer;
    FloatingActionButton writeBtn;

    @Override
    protected void onResume() {
        super.onResume();

        mapErrandsListener = new MapErrandsListener(adapter, this);
        // 맵뷰 셋팅
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("6301c8d166630b078ad13401acc1267f");
        mapView.setMapViewEventListener(mapErrandsListener);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        writeBtn = (FloatingActionButton)findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErrandActivity.this, ErrandRegisterActivity.class);
                startActivity(intent);
            }
        });

        //리스트뷰 셋팅
        ListView listView = (ListView)findViewById(R.id.errandList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                view.setSelected(true);
                ErrandsItem item = (ErrandsItem)parent.getItemAtPosition(position);
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude())), true);
                int click = item.getClick();
                if(click == 0) {//선택안된 것을 처음 클릭했을 때
                    /**
                     * 다른 아이템들을 setClick(0)해주어야 함
                     * */

                    ArrayList<ErrandsItem> list =  adapter.getErrandList();
                    for(ErrandsItem it : list){
                        if(it.getErrandNum() != item.getErrandNum()){
                            it.setClick(0);
                        }
                    }
                    item.setClick(1);//한번선택됨

                    Toast.makeText(getBaseContext(), position + "번 선택됨!! ",
                            Toast.LENGTH_SHORT).show();
                }else if(click == 1){//선택된 셀을 다시 클릭
                    item.setClick(0);
                    Intent intent = new Intent(getApplicationContext(), DetailViewActivity.class);
                    intent.putExtra("errandNum",item.getErrandNum()+"");
                    startActivity(intent);
                }
            }
        });
    }


}
