package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-17.
 * 심부를 상세정보에서 댓글을 등록하기 위한 NetWorkTask Class
 */

public class ReplyInsertNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {

    private Map<String , String> map;
    Activity activity;
    public ReplyInsertNetworkTask() {
        super();
    }

    public ReplyInsertNetworkTask(Activity activity) {
        super();
        this.activity = activity;
    }

    /**
     * 네트워크 기능을 background 스레드로 처리하는 메소드
     * @param maps 웹으로 보내는 params
     * @return String
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/insertReply"); // HTTP 요청 전송

        http.addAllParameters(maps[0]);
        HttpClient post = http.create();
        post.request(); // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode(); // 응답 본문 가져오기
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
     * s값을 비교해서 댓글 등록의 성공 여부 판단을 한다.
     * @param s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        if(s.trim().equals("0")) {
            Toast.makeText(activity, "댓글 등록 실패", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(activity, "댓글 등록 성공", Toast.LENGTH_SHORT).show();
        }
    }

}
