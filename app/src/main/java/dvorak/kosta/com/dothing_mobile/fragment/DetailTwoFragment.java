package dvorak.kosta.com.dothing_mobile.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTwoFragment extends Fragment {

    View v;

    public DetailTwoFragment() {
        // Required empty public constructor
    }

    public static DetailTwoFragment newInstance(){
        Bundle args = new Bundle();

        DetailTwoFragment fragment = new DetailTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment


       //tab3
       /* ImageView requesterUserImg = (ImageView) v.findViewById(R.id.requester_user_img);
        EditText requestId = (EditText) v.findViewById(R.id.requester_id);
        EditText errandsRequestCount = (EditText) v.findViewById(R.id.errands_request_count);
        EditText grade = (EditText) v.findViewById(R.id.grade);
        EditText hashtag = (EditText) v.findViewById(R.id.hashtag);*/

       // Map<String, View> map = new HashMap<>();
       // map.put("view",v);

        String errandNum = getActivity().getIntent().getStringExtra("errandNum");

        DetailRequesterNetworkTask networkTask = new DetailRequesterNetworkTask(errandNum);
        Map<String, String> params = new HashMap<>();
        params.put("errandNum", errandNum);
        networkTask.execute(params);

        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_detail_two, container, false);
        return v;
    }

    ///////////
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



        public DetailRequesterNetworkTask() {
            super();
        }

        public DetailRequesterNetworkTask(String errandNum) {
            super();
            this.errandNum = errandNum;

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
          //  view = map.get("view");
           // this.requesterImg = (ImageView) view.findViewById(R.id.requesterImg);
           // this.requesterId = (TextView) view.findViewById(R.id.requester_id);
           // this.requestCount = (TextView) view.findViewById(R.id.errands_request_count);
           // this.mannerGrade = (RatingBar) view.findViewById(R.id.mannerGrade);
           // this.introduce = (TextView) view.findViewById(R.id.introduce);
           // this.hashtagLayout = (LinearLayout) view.findViewById(R.id.hashtagLayout);
        }

        /**
         * doInBackground 종료되면 동작한다. * @param s : doInBackground가 리턴한 값이 들어온다.
         */
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject obj = new JSONObject(s);
                String requesterId = obj.getString("requesterId");
                int requestCount = obj.getInt("requestCount");
                int grade = obj.getInt("grade");
                JSONArray hashtagList = obj.getJSONArray("hashtagList");
                String hash = "";
                int len = (hashtagList.length() > 5) ? 5 : hashtagList.length();
                for(int i=0; i<len; i++) {
                    hash += hashtagList.get(i) + " ";
                }
                String introduce = obj.getString("introduce");
                String requesterImg = obj.getString("requesterImg");

                Log.i("requestId : ", this.requesterId.getText().toString() );
                Log.i("requestCount : ", this.requestCount.getText().toString()+"" );
                Log.i("grade :", grade+"" );
                Log.i("hash :", hash);
                Log.i("introduce :", introduce);
                Log.i("requesterImg :", requesterImg);

                ((TextView)v.findViewById(R.id.requester_id)).setText(requesterId);
                ((TextView)v.findViewById(R.id.errands_request_count)).setText(requesterId);
                ((RatingBar)v.findViewById(R.id.mannerGrade)).setRating(grade);
                ((TextView)v.findViewById(R.id.introduce)).setText(requesterId);


/*
                this.requesterId.setText(requesterId);
                this.requestCount.setText(requestCount+"");
                this.mannerGrade.setRating(grade);
                this.introduce.setText(introduce);
                URL url = new URL(ConstantUtil.ipAddr + "users/" + requestId + "/" + requesterImg);
                Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                this.requesterImg.setImageBitmap(bitmap);
                */

                // ((ImageView)map.get("requesterUserImg")).setImage
                // ((EditText)v.findViewById(R.id.requester_id)).setText(requestId);
                // ((EditText)v.findViewById(R.id.errands_request_count)).setText(requestCount+"");
                // ((EditText)v.findViewById(R.id.grade)).setText(grade+"");
                //((EditText)v.findViewById(R.id.hashtag)).setText(grade+"");



            }catch(Exception e){
                e.printStackTrace();
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }


    //////////



}
