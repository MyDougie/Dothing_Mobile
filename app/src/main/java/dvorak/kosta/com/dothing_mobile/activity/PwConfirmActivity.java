package dvorak.kosta.com.dothing_mobile.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.network.MyInfoUpdateNetworkTask;
import dvorak.kosta.com.dothing_mobile.network.PwConfirmNetworkTask;

/**
 * Created by crw12 on 2017-07-24.
 */

public class PwConfirmActivity extends AppCompatActivity {

    EditText password;
    Map<String, String> params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordconfirm);

        params = new HashMap<>();

        Button mConfirmPwBtn = (Button) findViewById(R.id.confirmPwBtn);
        mConfirmPwBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                comparePassword();
            }
        });

    }

    private void comparePassword(){

        password = (EditText) findViewById(R.id.passwordConfirm);

        params.put("id", MemberInfo.userId);
        params.put("pw", password.getText().toString());

        PwConfirmNetworkTask networkTask = new PwConfirmNetworkTask(PwConfirmActivity.this);
        networkTask.execute(params);

    }


}
