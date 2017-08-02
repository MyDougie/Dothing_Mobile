package dvorak.kosta.com.dothing_mobile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import dvorak.kosta.com.dothing_mobile.R;

/**
 * 튜토리얼 보여주는 엑티비티
 */
public class TutorialActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tutorial);

    }
}
