package dvorak.kosta.com.dothing_mobile.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.RatingActivity;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by YTK on 2017-07-23.
 * 심부름을 한 사용자의 정보를 가져오는 NetworkTask Class
 */
public class EvalNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
    Context context;
    boolean isRequest;
    String imgPath, name, errandsNum, userId;
    public EvalNetworkTask(Context context, boolean isRequest, String imgPath, String name, String errandsNum, String userId){
        this.context = context;
        this.isRequest = isRequest;
        this.imgPath=imgPath;
        this.name=name;
        this.errandsNum = errandsNum;
        this.userId=userId;
    }

    /**
     * background을 실행하기 전 준비 단계 메소드
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 네트워크 기능을 background 스레드로 처리하는 메소드
     * @param maps 웹으로 보내는 params
     * @return String
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidMember/isEvalFinish"); // HTTP 요청 전송

        http.addAllParameters(maps[0]);
        HttpClient post = http.create();
        post.request(); // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode(); // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }

    /**
     * UI 스레드 상에서 실행되며, doInBackground() 종료 후 호출됨. \n
     * s로 심부름을 한 대상자의 정보를 가져온다
     * @param s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        try {
            if(s.equals("true")){
                Intent intent = new Intent(context, RatingActivity.class);
                intent.putExtra("isRequest", isRequest);
                intent.putExtra("name", name);
                intent.putExtra("imgPath", imgPath);
                intent.putExtra("errandsNum", errandsNum);
                intent.putExtra("userId", userId);
                context.startActivity(intent);
            }else{
                Toast.makeText(context, "이미 심부름 완료를 요청했습니다.", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Log.e("E", e.getMessage());
        }

    }
}
