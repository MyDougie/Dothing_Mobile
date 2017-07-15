package dvorak.kosta.com.dothing_mobile.activity;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TabHost;

import dvorak.kosta.com.dothing_mobile.R;

public class FrameActivity extends ActivityGroup {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup(getLocalActivityManager());

        TabHost.TabSpec ts = tabHost.newTabSpec("심부름 검색");
        ts.setIndicator("", getResources().getDrawable(R.drawable.magnifier));
        ts.setContent(new Intent(this, ErrandActivity.class));
        tabHost.addTab(ts);

        TabHost.TabSpec ts2 = tabHost.newTabSpec("심부름 등록");
        ts2.setIndicator("", getResources().getDrawable(R.drawable.edit));
        ts2.setContent(new Intent(this, ErrandRegisterActivity.class));
        tabHost.addTab(ts2);

        tabHost.setCurrentTab(0);
        toolbar.setTitle("심부름 검색");
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                toolbar.setTitle(tabId);
            }
        });
    }
}
