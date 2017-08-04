package dvorak.kosta.com.dothing_mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.network.LoginNetworkTask;

/**
 * @brief : 페이스북 로그인시 추가정보 입력받는 엑티비티
 *           사용자 입력을 받아 서버로 전송, 로그인 시도
 */
public class LoginApiActivity extends AppCompatActivity {
    String email,id,name,gender,selfImg;
    String latitude, longitude;
    EditText apiAddr,apiDetailAddr;
    Button apiBtn;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginapi);

        Intent intent = getIntent();
        email = intent.getStringExtra("userId");
        id = intent.getStringExtra("id");
        gender = intent.getStringExtra("gender");
        selfImg = intent.getStringExtra("selfImg");
        name = intent.getStringExtra("name");

        apiAddr = (EditText)findViewById(R.id.apiAddr);
        apiDetailAddr = (EditText)findViewById(R.id.apiDetailAddr);
        apiBtn = (Button)findViewById(R.id.apiBtn);

        apiAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginApiActivity.this, RegisterWebviewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });
        apiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apiAddrStr = apiAddr.getText().toString();
                String apiDetailAddrStr = apiDetailAddr.getText().toString();
                if(validCheck(apiAddrStr,apiDetailAddrStr)){
                    Map<String,String> params = new HashMap<>();
                    params.put("userId",email);
                    params.put("password",id);
                    params.put("name",name);
                    params.put("sex",gender);
                    params.put("preAddr",apiAddrStr);
                    params.put("detailAddr",apiDetailAddrStr);
                    params.put("latitude",latitude);
                    params.put("longitude",longitude);
                    params.put("selfImg",selfImg);
                    params.put("token", FirebaseInstanceId.getInstance().getToken());
                    params.put("isApi","true");
                    params.put("facebook","ok");

                    LoginNetworkTask networkTask = new LoginNetworkTask(LoginApiActivity.this, null, null, null);
                    networkTask.execute(params);
                }
            }
        });

    }

    /**
     * @brief : 입력값 유효성 검사
     * @param : apiAddrStr - 주소, apiDetailAddrStr - 상세주소
     * @return : 입력값이 있으면 true, 없으면 false
     */
    public boolean validCheck(String apiAddrStr,String apiDetailAddrStr){

        if(apiAddrStr.length() < 1){
            Toast.makeText(getApplicationContext(),"주소를 입력해주세요",Toast.LENGTH_SHORT).show();
            apiAddr.requestFocus();
            return false;
        } else if(apiDetailAddrStr.length() < 1){
            Toast.makeText(getApplicationContext(),"상세주소를 입력해주세요",Toast.LENGTH_SHORT).show();
            apiDetailAddr.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * @brief : callback 메소드
     * @param : requestCode - 요청코드, resultCode - 결과코드, data - 전송한 데이터
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == SEARCH_ADDRESS_ACTIVITY) {
            String data = intent.getExtras().getString("data");
            if (data != null) {
                // data = 주소:위도:경도
                apiAddr.setText(data.split(":")[0]);
                latitude = data.split(":")[1];
                longitude = data.split(":")[2];
            }
        }
    }
}
