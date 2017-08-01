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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

//import com.bumptech.glide.Glide;
//import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by YTK on 2017-07-15.
 */

public class MyPageActivity extends AppCompatActivity{

    LinearLayout myRequestLayout, myResponseLayout, myLogout, mySafety, myInfoPW;
    RatingBar ratingbar;

    TextView myName, myEmail, myPoint;
    ImageView myImage;
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
        myResponseLayout = (LinearLayout)findViewById(R.id.responseBtn);
        myLogout = (LinearLayout) findViewById(R.id.logoutBtn);

        myInfoPW = (LinearLayout) findViewById(R.id.myinfoupdate);

        myRequestLayout.setOnClickListener(myPageListener);
        myResponseLayout.setOnClickListener(myPageListener);
        myLogout.setOnClickListener(myPageListener);
        myInfoPW.setOnClickListener(myPageListener);

        Log.e("정보들", MemberInfo.name + " " + MemberInfo.userId + " " + MemberInfo.selfImgUrlPath);

        mySafety = (LinearLayout)findViewById(R.id.safetyBtn);
        ratingbar = (RatingBar)findViewById(R.id.myRating);
        myRequestLayout.setOnClickListener(myPageListener);
        myResponseLayout.setOnClickListener(myPageListener);
        myLogout.setOnClickListener(myPageListener);
        mySafety.setOnClickListener(myPageListener);
        Log.e("정보들", MemberInfo.name + " " + MemberInfo.userId + " " + MemberInfo.selfImgUrlPath + " " + MemberInfo.averageGPA);

        myName.setText(MemberInfo.name);
        myEmail.setText(MemberInfo.userId);
        ratingbar.setRating(MemberInfo.averageGPA);
        DecimalFormat formatter = new DecimalFormat("#,###");
        String currentPoint = formatter.format(Double.parseDouble(MemberInfo.currentPoint));
        myPoint.setText(currentPoint + "원");

        Glide.with(this).load(MemberInfo.selfImgUrlPath).bitmapTransform(new CropCircleTransformation(this)).into(myImage);



        //Glide.with(this).load(MemberInfo.selfImgUrlPath).bitmapTransform(new CropCircleTransformation(this)).into(myImage);
//        task = new back();
//        task.execute(MemberInfo.selfImgUrlPath);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setSupportActionBar(null);
    }

    public class MyPageListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.requestBtn:
                    Intent intent = new Intent(MyPageActivity.this, MyRequestActivity.class);
                    startActivity(intent);
                    break;
                case R.id.responseBtn:
                    Intent resIntent = new Intent(MyPageActivity.this, MyResponseActivity.class);
                    startActivity(resIntent);
                    break;
                case R.id.logoutBtn:
                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = auto.edit();
                    editor.remove("LoginId");
                    editor.remove("LoginPassword");
                    editor.commit();

                    Intent logoutIntent = new Intent(MyPageActivity.this, LoginActivity.class);
                    startActivity(logoutIntent);
                    finish();
                    break;
                case R.id.myinfoupdate:
                    Intent pwIntent = new Intent(MyPageActivity.this, PwConfirmActivity.class);
                    startActivity(pwIntent);
                    break;

                case R.id.safetyBtn:
                    if(MemberInfo.auth == 2){
                        Toast.makeText(MyPageActivity.this, "이미 안전심부름꾼입니다 :)",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Log.i("SSNIMG", MemberInfo.ssnImg);
                    if(!MemberInfo.ssnImg.equals("null")){
                        Toast.makeText(MyPageActivity.this, "안전심부름꾼 심사 대기중입니다 :)",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    Intent safetyIntent = new Intent(MyPageActivity.this, SafetyActivity.class);
                    startActivity(safetyIntent);
                    break;
            }
        }
    }

}
