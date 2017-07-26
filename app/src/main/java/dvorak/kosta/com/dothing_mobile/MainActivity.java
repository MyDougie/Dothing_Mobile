package dvorak.kosta.com.dothing_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.network.LoginNetworkTask;

public class MainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    private Button facebookBtn;
    String email,name,gender,id,selfImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        facebookBtn = (Button)findViewById(R.id.facebookBtn);
        final Button loginBtn = (Button) findViewById(R.id.loginBtn);

        Button joinBtn = (Button)findViewById(R.id.joinBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        joinBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),JoinActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("email","user_friends","public_profile","user_birthday"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        Log.e("facebookSuccess","onSuccess");

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("result : ",object.toString());
                                Profile profile = Profile.getCurrentProfile();
                                selfImg = profile.getProfilePictureUri(100,100).toString();
                                Log.e("bbbbb",selfImg);

                               try{
                                   email = object.getString("email");
                                   name = object.getString("name");
                                   gender = object.getString("gender");
                                   id = object.getString("id");

                                   Log.e("TAG","페이스북 이메일 => "+email);
                                   Log.e("TAG","페이스북 이메일 => "+name);
                                   Log.e("TAG","페이스북 이메일 => "+gender);
                                   Log.e("TAG","페이스북 이메일 => "+id);

                                   Map<String,String> map = new HashMap<>();
                                   map.put("userId",email);
                                   map.put("gender",gender);
                                   map.put("password",id);
                                   map.put("name",name);
                                   map.put("selfImg",selfImg);
                                   map.put("isApi","true");
                                   map.put("token", FirebaseInstanceId.getInstance().getToken());

                                   LoginNetworkTask networkTask = new LoginNetworkTask(MainActivity.this);
                                   networkTask.execute(map);

                               }catch (Exception e){
                                    e.printStackTrace();
                               }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields","id,name,email,gender");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("onCancel","onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("onError","onError"+error.getLocalizedMessage());
                    }
                });

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
