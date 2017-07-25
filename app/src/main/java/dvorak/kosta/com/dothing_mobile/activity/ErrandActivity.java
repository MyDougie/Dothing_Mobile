package dvorak.kosta.com.dothing_mobile.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

public class ErrandActivity extends AppCompatActivity{

    MapErrandsListener mapErrandsListener;
    MapView mapView;
    ListViewAdapter adapter = new ListViewAdapter();

    ViewGroup mapViewContainer;
    FloatingActionButton writeBtn;
    int selection = 0;
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

        setSupportActionBar(FrameActivity.toolbar);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_errand, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_distance:
                final String items[] = { "3km", "5km", "10km", "30km" };
                AlertDialog.Builder ab = new AlertDialog.Builder(ErrandActivity.this);
                ab.setTitle("검색 반경 설정");
               final int preSelection = selection;
                final String preDistance = ConstantUtil.SEARCH_DISTANCE;
                switch(ConstantUtil.SEARCH_DISTANCE){
                    case "3" : selection=0; break;
                    case "5" : selection=1; break;
                    case "10" : selection=2; break;
                    case "30" : selection=3; break;
                }
                ab.setSingleChoiceItems(items, selection,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                switch(whichButton){
                                    case 0: ConstantUtil.SEARCH_DISTANCE = "3"; break;
                                    case 1: ConstantUtil.SEARCH_DISTANCE = "5";break;
                                    case 2: ConstantUtil.SEARCH_DISTANCE = "10";break;
                                    case 3: ConstantUtil.SEARCH_DISTANCE = "30";break;
                                }
                                selection = whichButton;
                            }
                        }).setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                selection = preSelection;
                                ConstantUtil.SEARCH_DISTANCE = preDistance;
                                dialog.dismiss();
                            }
                        });
                ab.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
