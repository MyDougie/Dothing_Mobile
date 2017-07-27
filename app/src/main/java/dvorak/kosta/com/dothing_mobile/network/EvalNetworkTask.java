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
     * doInBackground 실행되기 이전에 동작한다.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 본 작업을 쓰레드로 처리해준다. * @param params * @return
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
     * doInBackground 종료되면 동작한다. * @param s : doInBackground가 리턴한 값이 들어온다.
     */
    @Override
    protected void onPostExecute(String s) {
        Log.d("HTTP_RESULT", s);
        try {
            Log.e("EVAL", "결과: " + s);
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
