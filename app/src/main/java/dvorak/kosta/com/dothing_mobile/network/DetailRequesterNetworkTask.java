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
import dvorak.kosta.com.dothing_mobile.fragment.DetailTwoFragment;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017-07-17.
 */

public class DetailRequesterNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {

    private String errandNum;
    private Map<String , View> map;
    private ImageView requesterImg;
    private TextView requesterId;
    private TextView requestCount;
    private RatingBar mannerGrade;
    private LinearLayout hashtagLayout;
    private TextView introduce;
    private View view;
    private Activity activity;



    public DetailRequesterNetworkTask() {
        super();
    }

    public DetailRequesterNetworkTask(String errandNum, Map<String, View> map, Activity activity) {
        super();
        this.errandNum = errandNum;
        this.map = map;
        this.activity = activity;

    }

    /**
     * 본 작업을 쓰레드로 처리해준다. * @param params * @return
     */
    @Override
    protected String doInBackground(Map<String, String>... maps) {
        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/requesterDetail"); // HTTP 요청 전송

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
        this.requesterImg = (ImageView) view.findViewById(R.id.requesterImg);
        this.requesterId = (TextView) view.findViewById(R.id.requester_id);
        this.requestCount = (TextView) view.findViewById(R.id.errands_request_count);
        this.mannerGrade = (RatingBar) view.findViewById(R.id.mannerGrade);
        this.introduce = (TextView) view.findViewById(R.id.introduce);
        this.hashtagLayout = (LinearLayout) view.findViewById(R.id.hashtagLayout);
    }

    /**
     * doInBackground 종료되면 동작한다. * @param s : doInBackground가 리턴한 값이 들어온다.
     */
    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject obj = new JSONObject(s);
            String requestId = obj.getString("requesterId");
            DetailTwoFragment.requestId = requestId;
            int requestCount = obj.getInt("requestCount");
            int grade = obj.getInt("grade");
            String introduce = obj.getString("introduce");
            if(introduce.equals("null")){
                introduce = "   인사말이 없습니다.";
            }
            String requesterImg = obj.getString("requesterImg");


            //hashtag 동적으로 추가 시작
            JSONArray hashtagList = obj.getJSONArray("hashtagList");
            int len = (hashtagList.length() > 5) ? 5 : hashtagList.length();
            for(int i=0; i<len; i++) {
                Log.i("hashxxx : " , hashtagList.get(i).toString());
                TextView hashtag = new TextView(activity);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.topMargin = 20;
                lp.leftMargin = 40;
                hashtag.setLayoutParams(lp);

                hashtag.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                hashtag.setPadding(10, 10, 10, 10);
                hashtag.setPaintFlags(Paint.FAKE_BOLD_TEXT_FLAG);
                hashtag.setTextColor(Color.WHITE);
                hashtag.setTextSize(14);
                hashtag.setText(hashtagList.get(i).toString());

                hashtagLayout.addView(hashtag);

            }
            //hashtag 동적으로 추가 끝

            this.requesterId.setText(requestId);
            this.requestCount.setText(requestCount+"");
            this.mannerGrade.setRating(Math.round(grade));
            this.introduce.setText(introduce);
            Glide.with(view.getContext()).load(ConstantUtil.ipAddr + "users/" + requestId + "/" + requesterImg).bitmapTransform(new CropCircleTransformation(view.getContext())).into(this.requesterImg);
            this.requesterImg.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지를 뷰 크기에 맞게 조절

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
