package dvorak.kosta.com.dothing_mobile.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import dvorak.kosta.com.dothing_mobile.MainActivity;
import dvorak.kosta.com.dothing_mobile.R;

public class SplashActivity extends AppCompatActivity {
    int checkstate = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PermissionListener permissionListener = new PermissionListener(){
            @Override
            public void onPermissionGranted() {
                checkstate++;
                if(checkstate == 2){
                    goPage();
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                checkstate++;
                if(checkstate == 2){
                    goPage();
                }
            }
        };
        new TedPermission(this).setPermissionListener(permissionListener)
                .setDeniedMessage("권한 거부시 이용이 원할하지 않습니다 [설정]->[권한]에서 허용해주세요")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check();
        new TedPermission(this).setPermissionListener(permissionListener)
                .setDeniedMessage("권한 거부시 이용이 원할하지 않습니다 [설정]->[권한]에서 허용해주세요")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();




    }

    public void goPage(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, 2000);

    }
    @Override
    public void onBackPressed() {
    }
}
