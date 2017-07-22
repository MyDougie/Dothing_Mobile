package dvorak.kosta.com.dothing_mobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dvorak.kosta.com.dothing_mobile.dvorak.kosta.com.dothing_mobile.dto.LoginResultDTO;
import dvorak.kosta.com.dothing_mobile.network.AuthNetworkTask;

public class JoinActivity1 extends AppCompatActivity {

    EditText email,password,confirmPassword,et;
    Button authBtn;
    TextView authText;
    LoginResultDTO dto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join1);

        Button continueBtn1 = (Button) findViewById(R.id.continueBtn1);
        email = (EditText) findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.confirmPassword);
        authBtn = (Button)findViewById(R.id.authBtn);
        authText = (TextView)findViewById(R.id.idCheck);

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passwordStr = password.getText().toString();
                String confirmPasswordStr = confirmPassword.getText().toString();

                if(passwordStr.equals(confirmPasswordStr)){
                    password.setBackgroundColor(Color.parseColor("#7AFFCF"));
                    confirmPassword.setBackgroundColor(Color.parseColor("#7AFFCF"));
                } else {
                    password.setBackgroundColor(Color.parseColor("#FF9BD2"));
                    confirmPassword.setBackgroundColor(Color.parseColor("#FF9BD2"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

            authBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                        if (authText.getText().toString().equals("인증 성공")) {
                            Toast.makeText(getApplicationContext(), "인증 완료", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"Email로 인증번호를 전송했습니다.",Toast.LENGTH_SHORT).show();
                            AuthNetworkTask networkTask = new AuthNetworkTask(JoinActivity1.this, authText);
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("email", email.getText().toString());
                            networkTask.execute(map);
                        }
                    } else{
                        Toast.makeText(getApplicationContext(),"이메일 형식이 아닙니다",Toast.LENGTH_SHORT).show();
                    }
                }
            });


        continueBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String confirmPasswordStr = confirmPassword.getText().toString();

                if(validCheck(emailStr,passwordStr,confirmPasswordStr)){
                    if(authText.getText().toString().equals("인증 성공")){
                        Intent intent = new Intent(getApplicationContext(), JoinActivity3.class);
                        intent.putExtra("email", emailStr);
                        intent.putExtra("password", passwordStr);
                        startActivity(intent);
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(),"이메일 인증을 해주세요.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    //비밀번호 조합 검사
    public boolean passwordValidate(String password){
        String regex = "([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
    //유효성 검사
    public boolean validCheck(String emailStr, String passwordStr, String confirmPasswordStr){

        if(!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            Toast.makeText(getApplicationContext(),"email 형식이 아닙니다.",Toast.LENGTH_LONG).show();
            email.requestFocus();
            return false;
        } else if(!passwordValidate(passwordStr) || passwordStr.length() < 6) {
            Toast.makeText(getApplicationContext(),"영어,숫자,특수문자 조합으로 6자리 이상 사용해주세요.",Toast.LENGTH_LONG).show();
            password.requestFocus();
            return false;
        }
        else if(!passwordStr.equals(confirmPasswordStr)){
            Toast.makeText(getApplicationContext(),"비밀번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
            confirmPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }

}
