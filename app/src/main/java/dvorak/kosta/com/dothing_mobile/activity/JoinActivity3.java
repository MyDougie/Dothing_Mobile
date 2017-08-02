package dvorak.kosta.com.dothing_mobile.activity;

import android.app.Dialog;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.network.UploadDataNetworkTask;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * @brief : 회원가입 엑티비티
 */
public class JoinActivity3 extends AppCompatActivity {

    private WebView webView;
    private EditText addr,name;//addr
    private EditText detailAddr;
    private Handler handler;

    private Dialog dialogWeb;

    private String email;
    private String password;
    ImageView registerImage;
    String imgPath="";
    String latitude, longitude;
    MapView mapView;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join3);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");


        //findViewById
        addr = (EditText) findViewById(R.id.addr);
        detailAddr = (EditText) findViewById(R.id.detailAddr);
        name = (EditText) findViewById(R.id.name);
        Button myImage = (Button)findViewById(R.id.myImage);
        registerImage = (ImageView)findViewById(R.id.myProfile);


        Button joinBtn = (Button)findViewById(R.id.continueBtn3);
        /**
         * @brief : 회원가입 버튼 클릭시 동작, 입력값을 받아 서버로 전송해서 가입시도
         */
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
                //getCheckedRadioButtonId() 의 리턴값은 선택된 RadioButton 의 id 값.
                RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
                String gender = rb.getText().toString();
                String addrStr = addr.getText().toString();
                String detailAddrStr = detailAddr.getText().toString();
                String nameStr = name.getText().toString();

                if(validCheck(addrStr,detailAddrStr,nameStr,imgPath)){


                    Map<String, Object> params = new HashMap<>();
                    params.put("userId",email);
                    params.put("password",password);
                    params.put("name",nameStr);
                    params.put("sex",gender);
                    params.put("preAddr",addrStr);
                    params.put("detailAddr",detailAddrStr);
                    params.put("latitude",latitude);
                    params.put("longitude",longitude);
                    params.put("selfImgFile",new File(imgPath));

                    new UploadDataNetworkTask(ConstantUtil.ipAddr + "androidMember/signIn", JoinActivity3.this).execute(params);

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        /**
         * @brief : 프로필사진 업로드 버튼 클릭시 동작
         */
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakeAlbumAction();
            }
        });
        /**
         * @brief : 주소 검색 클릭시 동작, registerWebview엑티비티 실행
         */
        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JoinActivity3.this, RegisterWebviewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });


    }

    /**
     * @brief : 엘범에서 이미지를 가져오는 메소드
     */
    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    /**
     * @brief : 이미지 실제 경로 구하는 메소드
     */
    public String getRealImagePath(Uri uriPath) {
        String[] proj = { MediaStore.Images.Media.DATA };

        CursorLoader cursorLoader = new CursorLoader(this, uriPath, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * @brief : callback 메소드, 서버에서 보내온 데이터를 split해서 위도,경도 저장
     */
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

    /**
     * @brief : 주소,상세주소 이름, 이미지 경로 유효성 검사
     * @param : addr, detailAddr, name, imgPath
     * @return : 유효성 검사 통과하면 true, 아니면 false
     */
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
}

