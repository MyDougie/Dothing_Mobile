package dvorak.kosta.com.dothing_mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.MyListViewAdapter;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.item.ErrandsItem;
import dvorak.kosta.com.dothing_mobile.network.MyErrandNetworkTask;

/**
 * 심부름 요청 엑티비티, 심부름 클릭시 심부름 번호, 사용자 ID 서버로 전송
 */
public class MyRequestActivity extends AppCompatActivity {
    MyListViewAdapter myListViewAdapter = new MyListViewAdapter();
    ListView listView;
    MyErrandNetworkTask myErrandNetworkTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_request);
        listView = (ListView)findViewById(R.id.requestList);
        listView.setAdapter(myListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ErrandsItem item = (ErrandsItem)parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DetailViewActivity.class);
                intent.putExtra("errandNum",item.getErrandNum()+"");
                intent.putExtra("requestUserId", item.getRequesterId());
                startActivity(intent);
            }
        });
        Map<String,String> params = new HashMap<>();
        params.put("userId", MemberInfo.userId);
        myErrandNetworkTask = new MyErrandNetworkTask(myListViewAdapter, "myRequest");
        myErrandNetworkTask.execute(params);
    }
}
