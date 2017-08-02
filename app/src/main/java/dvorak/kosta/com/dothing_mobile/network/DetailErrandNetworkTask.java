package dvorak.kosta.com.dothing_mobile.network;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.ReplyListViewAdapter;
import dvorak.kosta.com.dothing_mobile.fragment.DetailOneFragment;
import dvorak.kosta.com.dothing_mobile.item.Member;
import dvorak.kosta.com.dothing_mobile.item.ReplyItem;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * Created by Administrator on 2017-07-24.
 * 선택된 심부름의 상세정보 가져오는 NetWorkTask Class
 */
public class DetailErrandNetworkTask extends AsyncTask<Map<String, String>, Integer, String> {

    private ReplyListViewAdapter adapter;
    private String errandNum;
    private Map<String , View> map;
    private View view;
    private ImageView errandImg;
    private TextView errandsPrice;
    private TextView productPrice;
    private TextView address;
    private TextView errandContent;
    private TextView errandTime;
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
     * 네트워크 기능을 background 스레드로 처리하는 메소드
     * @param maps 웹으로 보내는 params
     * @return String
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

    /**
     * background을 실행하기 전 준비 단계 메소드
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        view = map.get("view");
        this.errandImg = (ImageView) view.findViewById(R.id.detailErrandImage);
        this.errandsPrice = (TextView) view.findViewById(R.id.detailErrandPrice);
        this.productPrice = (TextView) view.findViewById(R.id.detailProductPrice);
        this.address = (TextView) view.findViewById(R.id.detailErrandAddr);
        this.errandTime = (TextView)view.findViewById(R.id.detailErrandTime);
        this.errandContent = (TextView) view.findViewById(R.id.detailErrandContent);
    }

    /**
     * UI 스레드 상에서 실행되며, doInBackground() 종료 후 호출됨. \n
     * s로 필요한 데이터 정보들을 받아와서 심부름의 상세정보를 보여준다.
     * @param s doInBackground()에서 return한 parameter
     * */
    @Override
    protected void onPostExecute(String s) {
        try {
            adapter.removeItem();

            JSONObject obj = new JSONObject(s);
            int productPrice = obj.getInt("productPrice");
            int errandsPrice = obj.getInt("errandsPrice");
            String address = obj.getString("address");
            String errandContent = obj.getString("errandContent");
            String errandImg = obj.getString("errandImg");
            String errandTime = obj.getString("errandTime");
            errandContent = errandContent.replaceAll("<p>","");
            errandContent = errandContent.replaceAll("</p>","\n");


            this.errandsPrice.setText(errandsPrice+"");
            this.productPrice.setText(productPrice+"");
            this.address.setText(address);
            this.errandContent.setText(errandContent);
            this.errandTime.setText(errandTime +"까지");
            DetailOneFragment.errandTime=errandTime;
            Glide.with(view.getContext()).load(ConstantUtil.ipAddr + "errands/" + errandNum + "/" + errandImg).into(this.errandImg);

            JSONArray replyList = obj.getJSONArray("replyList");
            JSONArray avgGpaList = obj.getJSONArray("avgGpaList");
            for(int i=0; i<replyList.length(); i++){
                JSONObject replyObj=  replyList.getJSONObject(i);
                int replyNum = replyObj.getInt("replyNum");
                String content = replyObj.getString("replyContent");
                String arrivalTime = replyObj.getString("arrivalTime");
                String replyDate = replyObj.getString("replyDate");

                JSONObject replyUser = replyObj.getJSONObject("user");
                String imgPath = replyUser.getString("selfImg");
                String name = replyUser.getString("name");
                String userId = replyUser.getString("userId");

                int avgGpa = (int)avgGpaList.get(i);

                content = content.replaceAll("<p>","");
                content = content.replaceAll("</p>","\n");


                ReplyItem item = new ReplyItem();

                Member member = new Member();
                member.setId(userId);
                member.setName(name);
                member.setUserImgPath(imgPath);
                item.setUser(member);

                item.setReplyNum(replyNum);
                item.setReplyContent(content);
                item.setArrivalTime(arrivalTime);
                item.setReplyDate(replyDate);
                item.setResponserAvgRating(avgGpa);

                adapter.addItem(item);
            }
            adapter.notifyDataSetChanged();


        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
