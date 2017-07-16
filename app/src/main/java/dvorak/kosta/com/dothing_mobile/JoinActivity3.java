package dvorak.kosta.com.dothing_mobile;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.dvorak.kosta.com.dothing_mobile.dto.LoginResultDTO;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

public class JoinActivity3 extends AppCompatActivity {

    private WebView webView;
    private EditText addr;//addr
    private EditText detailAddr;
    private Handler handler;

    private Dialog dialogWeb;

    private String email;
    private String password;
    private String name;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join3);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");

        addr = (EditText) findViewById(R.id.addr);
        detailAddr = (EditText) findViewById(R.id.detailAddr);

        // WebView 초기화
        //init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        //handler = new Handler();



        Button joinBtn = (Button)findViewById(R.id.continueBtn3);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), JoinActivity2.class);

                RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
                //getCheckedRadioButtonId() 의 리턴값은 선택된 RadioButton 의 id 값.
                RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());
                String gender = rb.getText().toString();

                NetworkTask2 networkTask = new NetworkTask2();

                Map<String, String> params = new HashMap<>();
                params.put("userId",email);
                params.put("password",password);
                params.put("name",name);
                params.put("phone",phone);
                params.put("sex",gender);
                params.put("preAddr",addr.getText().toString());
                params.put("detailAddr",addr.getText().toString());

                networkTask.execute(params);


            }
        });

        dialogWeb = new Dialog(this);

        Button searchAddressBtn = (Button)findViewById(R.id.searchAddress);
        searchAddressBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("searchAddressBtn clicked!!");
            }
        });


    }

    public class NetworkTask2 extends AsyncTask<Map<String, String>, Integer, String> {
          @Override
          protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

            // Http 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "signIn");

            // Parameter 를 전송한다.
            http.addAllParameters(maps[0]);

            //Http 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("RESULT : ",s);

            Gson gson = new Gson();
            LoginResultDTO dto = gson.fromJson(s,LoginResultDTO.class);

            if(dto.getResult().equals("1")){
                Toast toast = Toast.makeText(getApplicationContext(),"회원가입이 되었습니다!",Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            } else{
                Toast toast = Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }



    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    addr.setText(String.format("(%s) %s %s", arg1, arg2, arg3));
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }

    public void init_webView() {
        dialogWeb.setContentView(R.layout.activity_addr_webview);

        WebView wb = (WebView) dialogWeb.findViewById(R.id.addrWebView);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb.setWebChromeClient(new WebChromeClient());
        wb.loadUrl("doothing.com/signIn.jsp");
        wb.addJavascriptInterface(new AndroidBridge(), "TestApp");
        dialogWeb.setCancelable(true);
        dialogWeb.setTitle("주소검색");

    }



}

