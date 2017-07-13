package dvorak.kosta.com.dothing_mobile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import dvorak.kosta.com.dothing_mobile.R;

public class FrameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec ts = tabHost.newTabSpec("심부름 검색");
        ts.setContent(R.id.tab1);
    }
}
