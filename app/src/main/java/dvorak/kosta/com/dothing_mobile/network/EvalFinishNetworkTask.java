package dvorak.kosta.com.dothing_mobile.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.activity.RatingActivity;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by YTK on 2017-07-23.
 * @brief : 심부름 완료가 되었는지 판단 여부 NetworkTask Class
 */
public class EvalFinishNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
    Context context;
    public EvalFinishNetworkTask(Context context){
        this.context = context;
    }

    /**
     * @brief : background을 실행하기 전 준비 단계 메소드
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * @brief : 네트워크 기능을 background 스레드로 처리하는 메소드
     * @param : Map<String,String> maps 웹으로 보내는 params
     * @return : String
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/errandsFinish"); // HTTP 요청 전송

        http.addAllParameters(maps[0]);
        HttpClient post = http.create();
        post.request(); // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode(); // 응답 본문 가져오기
        String body = post.getBody();
        return body;
    }

    /**
     * @brief : UI 스레드 상에서 실행되며, doInBackground() 종료 후 호출됨. \n
     * s로 심부름이 완료가 제대로 되었는지 판단 여부 확인 한다.
     * @param : String s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        try {
            if(s.equals("true")){
                ((RatingActivity)context).finish();
            }else{
                Toast.makeText(context, "심부름 완료에 문제가 생겼습니다", Toast.LENGTH_SHORT);
            }
        }catch(Exception e){
            Log.e("E", e.getMessage());
        }

    }
}
