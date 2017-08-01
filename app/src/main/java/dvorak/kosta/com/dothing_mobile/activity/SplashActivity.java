package dvorak.kosta.com.dothing_mobile.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.network.LoginNetworkTask;

public class SplashActivity extends AppCompatActivity {
    int checkstate = 0;
    String click, errandsNum, requestUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            click = bundle.getString("click");
            errandsNum = bundle.getString("errandsNum");
            requestUserId = bundle.getString("requestUserId");
        }
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

                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                String id = auto.getString("LoginId",null);
                String password = auto.getString("LoginPassword",null);

                if(id != null && password !=null){

                    Map<String,String> map = new HashMap<String, String>();
                    map.put("userId",id);
                    map.put("password",password);
                    map.put("token", FirebaseInstanceId.getInstance().getToken());
                    map.put("isApi","false");

                    LoginNetworkTask networkTask = new LoginNetworkTask(SplashActivity.this, click, errandsNum, requestUserId);
                    networkTask.execute(map);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        }, 2000);

    }
    @Override
    public void onBackPressed() {
    }
}
