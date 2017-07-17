package dvorak.kosta.com.dothing_mobile.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by YTK on 2017-07-15.
 */

public class MyPageActivity extends AppCompatActivity{
    TextView myName, myEmail, myPoint;
    ImageView myImage;
    Handler handler;
    Bitmap bmImg;
//    back task;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        myName = (TextView)findViewById(R.id.userName);
        myEmail = (TextView)findViewById(R.id.userEmail);
        myPoint = (TextView)findViewById(R.id.userPoint);
        myImage = (ImageView)findViewById(R.id.userImage);
        Log.e("정보들", MemberInfo.name + " " + MemberInfo.userId + " " + MemberInfo.selfImgUrlPath);
        myName.setText(MemberInfo.name);
        myEmail.setText(MemberInfo.userId);
        DecimalFormat formatter = new DecimalFormat("#,###");
        String currentPoint = formatter.format(Double.parseDouble(MemberInfo.currentPoint));
        myPoint.setText(currentPoint + "원");

        Glide.with(this).load(MemberInfo.selfImgUrlPath).bitmapTransform(new CropCircleTransformation(this)).into(myImage);
//        task = new back();
//        task.execute(MemberInfo.selfImgUrlPath);

    }
 /*   private class back extends AsyncTask<String, Integer,Bitmap> {



        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                bmImg = BitmapFactory.decodeStream(is);


            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }

        protected void onPostExecute(Bitmap img){
            myImage.setImageBitmap(bmImg);

        }

    }*/

}
