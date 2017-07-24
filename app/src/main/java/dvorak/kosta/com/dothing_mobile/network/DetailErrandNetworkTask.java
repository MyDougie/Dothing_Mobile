package dvorak.kosta.com.dothing_mobile.network;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.ReplyListViewAdapter;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017-07-24.
 */


public class DetailErrandNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {

    private ReplyListViewAdapter adapter;
    private String errandNum;
    private Map<String , View> map;
    private View view;
    private ImageView errandImg;


    public DetailErrandNetworkTask() {
        super();
    }

    public DetailErrandNetworkTask(String errandNum, Map<String, View> map, ReplyListViewAdapter adapter) {
        super();
        this.errandNum = errandNum;
        this.map = map;
        this.adapter = adapter;
    }

    /**
     * 본 작업을 쓰레드로 처리해준다. * @param params * @return
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/errandsDetail"); // HTTP 요청 전송

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
        view = map.get("view");
        this.errandImg = (ImageView) view.findViewById(R.id.detailErrandImage);
    }

    /**
     * doInBackground 종료되면 동작한다. * @param s : doInBackground가 리턴한 값이 들어온다.
     */
    @Override
    protected void onPostExecute(String s) {
        try {

            JSONObject obj = new JSONObject(s);
            int productPrice = obj.getInt("productPrice");
            int errandsPrice = obj.getInt("errandsPrice");
            String address = obj.getString("address");
            String errandContent = obj.getString("errandContent");
            String errandImg = obj.getString("errandImg");

            Log.i("productPrice : ", productPrice+"");
            Log.i("errandsPrice : ", errandsPrice+"");
            Log.i("address : ", address);
            Log.i("errandContent : ", errandContent+"");
            Log.i("errandImg : ", errandImg+"");

         /*   //reply
            JSONArray replyList = obj.getJSONArray("replyList");
            for(int i=0; i<replyList.length(); i++) {
                Log.i("replyxxx : " , replyList.getJSONObject(i).toString());
                TextView reply = new TextView(activity);

                reply.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                reply.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                reply.setPadding(5, 5, 5, 5);
                reply.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
                reply.setTextColor(Color.WHITE);
                reply.setTextSize(14);
                reply.setText(hashtagList.get(i).toString());

                hashtagLayout.addView(hashtag);

            }
            //hashtag 동적으로 추가 끝
*/

            ((TextView)view.findViewById(R.id.detailErrandPrice)).setText(errandsPrice);
            ((TextView)view.findViewById(R.id.detailProductPrice)).setText(productPrice);
            ((TextView)view.findViewById(R.id.detailErrandAddr)).setText(address);
            ((TextView)view.findViewById(R.id.detailErrandContent)).setText(errandContent);
            Glide.with(view.getContext()).load(ConstantUtil.ipAddr + "errands/" + errandNum + "/" + errandImg).bitmapTransform(new CropCircleTransformation(view.getContext())).into(this.errandImg);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
