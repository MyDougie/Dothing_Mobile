package dvorak.kosta.com.dothing_mobile.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.MyListViewAdapter;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.network.MyErrandNetworkTask;

public class MyResponseActivity extends AppCompatActivity {
    MyListViewAdapter myListViewAdapter = new MyListViewAdapter();
    ListView listView;
    MyErrandNetworkTask myErrandNetworkTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_response);
        listView = (ListView)findViewById(R.id.responseList);
        listView.setAdapter(myListViewAdapter);

        Map<String,String> params = new HashMap<>();
        params.put("userId", MemberInfo.userId);
        myErrandNetworkTask = new MyErrandNetworkTask(myListViewAdapter, "myResponse");
        myErrandNetworkTask.execute(params);
    }
}
