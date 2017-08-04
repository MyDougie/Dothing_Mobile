package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-26.
 * User가 심부름 대상을 선택하고 매칭을 위한 NetWorkTask Class
 */


public class StartErrandNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {

        private Activity activity;

        public StartErrandNetworkTask() {
            super();
        }

        public StartErrandNetworkTask(Activity activity) {
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
            HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/startErrand"); // HTTP 요청 전송

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
         * s의 결과로 비교하여, 매칭 성공 여부를 보여준다.
         * @param s doInBackground()에서 return한 parameter
         * */
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject obj = new JSONObject(s);
                String result = obj.getString("result");

                if(result.equals("포인트가 부족합니다! 충전해주세요.")){

                            Toast.makeText(activity, "포인트가 부족합니다! 충전해주세요.", Toast.LENGTH_SHORT).show();

                }else if(result.equals("성공적으로 매칭되었습니다!!")){

                            Toast.makeText(activity, "성공적으로 매칭되었습니다!", Toast.LENGTH_SHORT).show();
                }

            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }