package dvorak.kosta.com.dothing_mobile.activity;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.network.GPSNetworkTask;
import dvorak.kosta.com.dothing_mobile.util.GPSInfo;

public class FrameActivity extends ActivityGroup {
    public static TabHost tabHost;
    public static Toolbar toolbar;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        //푸시메세지 토큰 등록
        context = this;
        FirebaseInstanceId.getInstance().getToken();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup(getLocalActivityManager());


        toolbar.setTitle("심부름 검색");
        TabHost.TabSpec ts = tabHost.newTabSpec("심부름 검색");
        ts.setIndicator("", getResources().getDrawable(R.drawable.magnifier_small));
        ts.setContent(new Intent(this, ErrandActivity.class));
        tabHost.addTab(ts);

        TabHost.TabSpec chatTs = tabHost.newTabSpec("채팅 목록");
        chatTs.setIndicator("", getResources().getDrawable(R.drawable.chat_small));
        chatTs.setContent(new Intent(this, ChatListActivity.class));
        tabHost.addTab(chatTs);

        TabHost.TabSpec myTs = tabHost.newTabSpec("마이페이지");
        myTs.setIndicator("", getResources().getDrawable(R.drawable.user_small));
        myTs.setContent(new Intent(this, MyPageActivity.class));
        tabHost.addTab(myTs);
        
        for (int tab = 0; tab < tabHost.getTabWidget().getChildCount(); ++tab) {
            tabHost.getTabWidget().getChildAt(tab).getLayoutParams().height = 150;
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                toolbar.setTitle(tabId);
                if(!tabId.equals("심부름 검색")) {
                    toolbar.getMenu().clear();
                }

            }
        });


        GPSInfo gpsInfo = new GPSInfo(this);
        GPSNetworkTask gpsNetworkTask = new GPSNetworkTask("updateLocation");
        Map<String, String> params = new HashMap<>();
        params.put("id", MemberInfo.userId);
        params.put("latitude", gpsInfo.getLatitude() +"");
        params.put("longitude", gpsInfo.getLongitude() +"");
    }
}
