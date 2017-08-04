package dvorak.kosta.com.dothing_mobile.activity;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.network.LoginNetworkTask;


/**
 * @brief : 로그인 화면 activity
 */
public class LoginActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private Map<String,String> params;
    public static ProgressBar progressBar;

    private CallbackManager callbackManager;
    private Button facebookBtn;
    String email,name,gender,id,selfImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        facebookBtn = (Button)findViewById(R.id.facebookBtn);
        progressBar = (ProgressBar)findViewById(R.id.loginProgress);
        params = new HashMap<>();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        /**
         * @brief : 페이스북 로그인 버튼 클릭시 동작
         */
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email","user_friends","public_profile","user_birthday"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    /**
                     * @brief : 로그인 성공시 동작 메소드, 로그인 정보 서버로 전송
                     * @param : 로그인한 사용자 정보
                     */
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        Log.e("facebookSuccess","onSuccess");

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("result : ",object.toString());
                                Profile profile = Profile.getCurrentProfile();
                                selfImg = profile.getProfilePictureUri(128,128).toString();
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

                                    LoginNetworkTask networkTask = new LoginNetworkTask(LoginActivity.this, null, null, null);
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

                    /**
                     * @brief : 로그인 취소시 동작
                     */
                    @Override
                    public void onCancel() {
                        Log.e("onCancel","onCancel");
                    }

                    /**
                     * @brief : 로그인 실패시 동작
                     */
                    @Override
                    public void onError(FacebookException error) {
                        Log.e("onError","onError"+error.getLocalizedMessage());
                    }
                });

            }
        }); //facebookBtn 끝

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }//oncreate 끝

    /**
     * @brief : callback 메소드
     * @param : requestCode - 요청코드, resultCode - 결과코드, data - 전송한 데이터
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    /**
     * @brief : 사용자가 입력한 email, password를 받아 서버로 전송, 성공시 로그인
     */
    private void attemptLogin() {
        /*if (mAuthTask != null) {
            return;
        }*/

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            params.put("userId",email);
            params.put("password",password);
            params.put("token", FirebaseInstanceId.getInstance().getToken());
            params.put("isApi","false");
Log.i("로그인" , "파람: " + params);
            LoginNetworkTask networkTask = new LoginNetworkTask(LoginActivity.this, null, null, null);
            networkTask.execute(params);

        }
    }

    /**
     * @brief : email 형식 여부 체크
     * @param : email
     * @return : 맞으면 true, 틀리면 false
     */
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    /**
     * @brief : password 유효성 검사
     * @param : password
     * @return : 5자리 이상이면 true, 아니면 false
     */
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * @brief : joinActivity1 엑티비티 시작 메소드
     */
    public void attemptSign(View v){
        Intent intent = new Intent(v.getContext(), JoinActivity1.class);
        startActivity(intent);
    }
}

