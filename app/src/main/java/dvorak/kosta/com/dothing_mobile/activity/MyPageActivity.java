package dvorak.kosta.com.dothing_mobile.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import dvorak.kosta.com.dothing_mobile.MainActivity;
import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by YTK on 2017-07-15.
 */

public class MyPageActivity extends AppCompatActivity{
    LinearLayout myRequestLayout;
    TextView myName, myEmail, myPoint;
    ImageView myImage, myLogout;
    Handler handler;
    Bitmap bmImg;
    MyPageListener myPageListener = new MyPageListener();
//    back task;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        myName = (TextView)findViewById(R.id.userName);
        myEmail = (TextView)findViewById(R.id.userEmail);
        myPoint = (TextView)findViewById(R.id.userPoint);
        myImage = (ImageView)findViewById(R.id.userImage);
        myRequestLayout = (LinearLayout)findViewById(R.id.requestBtn);

        myRequestLayout.setOnClickListener(myPageListener);
        myLogout = (ImageView) findViewById(R.id.logout);
        Log.e("정보들", MemberInfo.name + " " + MemberInfo.userId + " " + MemberInfo.selfImgUrlPath);
        myName.setText(MemberInfo.name);
        myEmail.setText(MemberInfo.userId);
        DecimalFormat formatter = new DecimalFormat("#,###");
        String currentPoint = formatter.format(Double.parseDouble(MemberInfo.currentPoint));
        myPoint.setText(currentPoint + "원");

        Glide.with(this).load(MemberInfo.selfImgUrlPath).bitmapTransform(new CropCircleTransformation(this)).into(myImage);

        myLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                editor.remove("LoginId");
                editor.remove("LoginPassword");
                editor.commit();

                Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Glide.with(this).load(MemberInfo.selfImgUrlPath).bitmapTransform(new CropCircleTransformation(this)).into(myImage);
//        task = new back();
//        task.execute(MemberInfo.selfImgUrlPath);

    }

    public class MyPageListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.requestBtn:
                    Intent intent = new Intent(MyPageActivity.this, MyRequestActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

}
