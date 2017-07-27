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
         * 본 작업을 쓰레드로 처리해준다. * @param params * @return
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        /**
         * doInBackground 종료되면 동작한다. * @param s : doInBackground가 리턴한 값이 들어온다.
         */
        @Override
        protected void onPostExecute(String s) {
            try {


                JSONObject obj = new JSONObject(s);
                String result = obj.getString("result");
                Log.i("result : ", result+"");

                if(result.equals("포인트가 부족합니다! 충전해주세요.")){

                            Toast.makeText(activity, "포인트가 부족합니다! 충전해주세요.", Toast.LENGTH_SHORT).show();




                }else if(result.equals("성공적으로 매칭되었습니다!!")){

                            Toast.makeText(activity, "성공적으로 매칭되었습니다!", Toast.LENGTH_SHORT).show();

                }




            }catch(Exception e){
                e.printStackTrace();
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }