package dvorak.kosta.com.dothing_mobile.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.adapter.MyListViewAdapter;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by YTK on 2017-07-17.
 */

public class MyErrandNetworkTask  extends AsyncTask<Map<String, String>, Integer, String> {
    MyListViewAdapter adapter;

    public MyErrandNetworkTask(MyListViewAdapter adapter){
        this.adapter = adapter;
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
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/myRequest"); // HTTP 요청 전송

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
            adapter.removeItem();

            JSONArray jsonArray = new JSONArray(s);

            for(int i=0; i < jsonArray.length(); i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONObject posObj = obj.getJSONObject("errandsPos");
                int errandsNum = obj.getInt("errandsNum");
                String title = obj.getString("title");
                int errandPrice = obj.getInt("errandsPrice");
                int productPrice = obj.getInt("productPrice");
                String addr = posObj.getString("addr");
                String errandTime = obj.getString("endTime");
                int errandNum = obj.getInt("errandsNum");
                String replyNum = (obj.getJSONArray("errandsReply")).length() + "";
                String state="";
                String startTime = obj.getString("startTime");
                String finishTime = obj.getString("finishTime");
                String arrivalTime = obj.getString("arrivalTime");
                if(startTime.equals("null") ){
                    state = "심부름꾼 대기중";
                    Log.e("힝", "여기로와야대는데");
                } else{
                    if(finishTime.trim().equals("null")){
                        state="요청 완료";
                    }else{
                        if(arrivalTime.trim().equals("null")){
                            state = "심부름꾼 확인 대기중";
                        }else{
                            Log.e("힝", "왜일로감?");
                            state = "심부름 완료";
                        }
                    }
                }
                Log.e("정보!", errandNum + ":" + title + ":" + addr + ":" + errandTime +":" + replyNum + ":" + state);
                adapter.addItem(errandNum, title, errandPrice + productPrice + "" ,addr,errandTime, replyNum,state);
            }
            adapter.notifyDataSetChanged();
        }catch(Exception e){
            Log.e("E", e.getMessage());
        }

    }

}