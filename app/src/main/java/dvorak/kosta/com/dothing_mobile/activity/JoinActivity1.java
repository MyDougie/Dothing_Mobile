package dvorak.kosta.com.dothing_mobile.activity;

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

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.network.AuthNetworkTask;

/**
 * @brief : 회원가입 엑티비티, email,password 입력받고 다음 회원가입 엑티비티로 데이터 전달
 */
public class JoinActivity1 extends AppCompatActivity {

    EditText email,password,confirmPassword,et;
    Button authBtn;
    TextView authText;

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

        /**
         * @brief : 비밀번호 확인 editText에 text입력이 감지되면 동작
         *           입력한 비밀번호와 일치,불일치시 색상변화
         */
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
        /**
         * @brief : 이메일 인증 버튼 클릭시 인증번호 전송
         */
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

    /**
     * @brief : 비밀번호 유효성 검사, 숫자,영문,특수문자 조합 8자리
     * @param : password
     * @return : 유효성 검사 통과시 true, 아니면 false
     */
    public boolean passwordValidate(String password){
        String regex = "([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    /**
     * @brief : email,password, confirmPassword 유효성 검사,
     *           email 형식 확인, password 조합확인, 비밀번호 일치 확인
     * @param : email, password, 비밀번호 확인
     * @return : 모든 조건 통과시 true, 아니면 false
     */
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
