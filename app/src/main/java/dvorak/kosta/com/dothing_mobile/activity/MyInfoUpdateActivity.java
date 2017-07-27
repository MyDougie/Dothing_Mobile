package dvorak.kosta.com.dothing_mobile.activity;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.daum.mf.map.api.MapView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.network.MyInfoUpdateNetworkTask;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;


/**
 * Created by crw12 on 2017-07-24.
 */

public class MyInfoUpdateActivity extends AppCompatActivity {

    private EditText addr,name;//addr
    private EditText detailAddr;
    private Map<String, Object> params;
    private EditText password;
    ImageView registerImage;
    String imgPath="";
    String latitude, longitude;
    MapView mapView;
    private View view;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfoupdate);

        addr = (EditText) findViewById(R.id.addr);
        detailAddr = (EditText) findViewById(R.id.detailAddr);
        name = (EditText) findViewById(R.id.name);
        Button myImage = (Button)findViewById(R.id.myImage);
        registerImage = (ImageView)findViewById(R.id.myProfile);
        password = (EditText) findViewById(R.id.password);

        name.setText(MemberInfo.name);
        addr.setText(MemberInfo.preAddr);
        detailAddr.setText(MemberInfo.detailAddr);
        //registerImage.setImageURI(Uri.parse(new File(MemberInfo.selfImgUrlPath).toString()));
        //Glide.with(view.getContext()).load(ConstantUtil.ipAddr + "users/" + MemberInfo.userId + "/" +MemberInfo.selfImgUrlPath).into(this.registerImage);
        password.setText(MemberInfo.password);
        latitude = MemberInfo.latitude;
        longitude = MemberInfo.longitude;


        Button updateBtn = (Button) findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String addrStr = addr.getText().toString();
                String detailAddrStr = detailAddr.getText().toString();
                String nameStr = name.getText().toString();
                String passwordStr = password.getText().toString();

                params = new HashMap<>();

                if(passwordStr.equals("******")){
                    if(validCheck(addrStr,detailAddrStr,nameStr,imgPath)){

                        params.put("id", MemberInfo.userId);
                        params.put("name",nameStr);
                        params.put("preAddr",addrStr);
                        params.put("detailAddr",detailAddrStr);
                        params.put("latitude",latitude);
                        params.put("longitude",longitude);
                        params.put("selfImgFile",new File(imgPath));

                        new MyInfoUpdateNetworkTask(MyInfoUpdateActivity.this, ConstantUtil.ipAddr+"androidMember/myinfoUpdate").execute(params);

                    }
                }else{
                    if(validCheck(addrStr,detailAddrStr,nameStr,imgPath)&&validCheck2(passwordStr)){
                        params.put("id", MemberInfo.userId);
                        params.put("name",nameStr);
                        params.put("preAddr",addrStr);
                        params.put("detailAddr",detailAddrStr);
                        params.put("latitude",latitude);
                        params.put("longitude",longitude);
                        params.put("password", passwordStr);
                        params.put("selfImgFile",new File(imgPath));
                        Log.i("여기로 찍히지?","제발");

                        new MyInfoUpdateNetworkTask(MyInfoUpdateActivity.this, ConstantUtil.ipAddr+"androidMember/myinfoUpdate").execute(params);

                    }
                }

            }
        });

        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakeAlbumAction();
            }
        });

        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyInfoUpdateActivity.this, RegisterWebviewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });


      /*  params = new HashMap<>();

        Button mConfirmPwBtn = (Button) findViewById(R.id.confirmPwBtn);
        mConfirmPwBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                comparePassword();
            }
        });*/

    }

    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    public String getRealImagePath(Uri uriPath) {
        String[] proj = { MediaStore.Images.Media.DATA };

        CursorLoader cursorLoader = new CursorLoader(this, uriPath, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 1) {
            registerImage.setImageURI((intent.getData()));
            imgPath = getRealImagePath(intent.getData());
        } else if (requestCode == SEARCH_ADDRESS_ACTIVITY) {
            String data = intent.getExtras().getString("data");
            if (data != null) {
                // data = 주소:위도:경도
                addr.setText(data.split(":")[0]);
                latitude = data.split(":")[1];
                longitude = data.split(":")[2];
            }
        }
    }

    //비밀번호 조합 검사
    public boolean passwordValidate(String password){
        String regex = "([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public boolean validCheck(String addrStr, String detailAddrStr, String nameStr, String imgPath){
        if(nameStr.length() < 1){
            Toast.makeText(getApplicationContext(),"이름을 입력해주세요",Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return false;
        } else if(addrStr.length() < 1){
            Toast.makeText(getApplicationContext(),"주소를 입력해주세요",Toast.LENGTH_SHORT).show();
            addr.requestFocus();
            return false;
        } else if(detailAddrStr.length() < 1){
            Toast.makeText(getApplicationContext(),"상세주소를 입력해주세요",Toast.LENGTH_SHORT).show();
            detailAddr.requestFocus();
            return false;
        } else if(imgPath.length() < 1){
            Toast.makeText(getApplicationContext(),"프로필 사진을 등록해주세요",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validCheck2(String passwordStr){
        if(!passwordValidate(passwordStr) || passwordStr.length() < 6) {
            Toast.makeText(getApplicationContext(),"영어,숫자,특수문자 조합으로 6자리 이상 사용해주세요.",Toast.LENGTH_LONG).show();
            password.requestFocus();
            return false;
        }
        return true;
    }


    /*private void comparePassword(){

        password = (EditText) findViewById(R.id.passwordConfirm);

        params.put("id", MemberInfo.userId);
        params.put("pw", password.getText().toString());
        params.put("test", "test");

        MyInfoUpdateNetworkTask networkTask = new MyInfoUpdateNetworkTask(MyInfoUpdateActivity.this);
        networkTask.execute(params);


        if(password.getText().equals(MemberInfo.password)){
            Toast.makeText(getApplicationContext(),"비밀번호가맞아", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),MemberInfo.userId, Toast.LENGTH_LONG).show();
        }


    }*/


}
