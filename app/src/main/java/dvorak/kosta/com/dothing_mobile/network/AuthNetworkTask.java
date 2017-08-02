package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-22.
 * User의 Email 인증을 위한 NetWorkTask Class
 */

public class AuthNetworkTask  extends AsyncTask<Map<String,String>,Integer,String>{
    Activity activity;
    TextView authText;
    EditText et;
    String str;

    public AuthNetworkTask(Activity activity, TextView authText){
        this.activity = activity;
        this.authText = authText;
    }

    /**
     * 네트워크 기능을 background 스레드로 처리하는 메소드
     * @param maps 웹으로 보내는 params
     * @return String
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidMember/sendEmail");
        http.addAllParameters(maps[0]);

        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode();
        // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }


    /**
     * background을 실행하기 전 준비 단계 메소드
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * UI 스레드 상에서 실행되며, doInBackground() 종료 후 호출됨. \n
     * s로 인증번호를 받아서 입력한 인증번호와 비교하여 성공 여부를 보여준다.
     * @param s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        if(!s.trim().equals("")){
            str = s;
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

            dialog.setTitle("이메일 인증");
            dialog.setMessage("인증번호");

            et = new EditText(activity);
            dialog.setView(et);

            dialog.setPositiveButton("인증", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(et.getText().toString().equals(str)){
                        Toast.makeText(activity,"인증되었습니다.",Toast.LENGTH_SHORT).show();
                        authText.setText("인증 성공");
                    } else{
                        Toast.makeText(activity,"인증 실패했습니다.",Toast.LENGTH_SHORT).show();
                        authText.setText("인증 실패");
                    }
                    dialog.dismiss();
                }
            });
            dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
