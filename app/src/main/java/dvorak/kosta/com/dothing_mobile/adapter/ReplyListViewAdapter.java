package dvorak.kosta.com.dothing_mobile.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.item.Member;
import dvorak.kosta.com.dothing_mobile.item.ReplyItem;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017-07-24.
 */

public class ReplyListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ReplyItem> ReplyListViewItemList = new ArrayList<ReplyItem>() ;

    // ListViewAdapter의 생성자
    public ReplyListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return ReplyListViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        //if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reply_item, parent, false);
       // }
        ImageView responserImg = (ImageView) convertView.findViewById(R.id.responserImg);
        TextView replyUserName = (TextView) convertView.findViewById(R.id.replyUserName);
        TextView replyContent = (TextView) convertView.findViewById(R.id.replyContent);
        TextView arrivalTime = (TextView)convertView.findViewById(R.id.arrivalTime);
        RatingBar replyRatingBar = (RatingBar) convertView.findViewById(R.id.replyRatingBar);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ReplyItem replyItem = ReplyListViewItemList.get(position);


        String userId = replyItem.getUser().getId();
        String imgPath = replyItem.getUser().getUserImgPath();
        // 아이템 내 각 위젯에 데이터 반영
        Glide.with(convertView.getContext()).load(ConstantUtil.ipAddr + "users/" + userId + "/" + imgPath).bitmapTransform(new CropCircleTransformation(convertView.getContext())).into(responserImg);
        Log.e("리플정보", userId + " : " + imgPath);
        responserImg.setScaleType(ImageView.ScaleType.FIT_XY); // 이미지를 뷰 크기에 맞게 조절
        replyUserName.setText(replyItem.getUser().getName());
        replyContent.setText(replyItem.getReplyContent());
        arrivalTime.setText("예상도착시간 : " + replyItem.getArrivalTime());
        replyRatingBar.setRating(replyItem.getResponserAvgRating());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return ReplyListViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(int replyNum, String userId, String name, String content, String arrivalTime, String replyDate, String imgPath, int avgGpa) {
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

        ReplyListViewItemList.add(item);
    }
    //리스트의 모든 아이템 제거
    public void removeItem(){
        ReplyListViewItemList.clear();
    }

    //아이템 리스트를 리턴
    public ArrayList<ReplyItem> getReplyList(){
        return ReplyListViewItemList;
    }

}
