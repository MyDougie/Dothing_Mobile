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
 * @breif : 뷰와 댓글 리스트를 연결시켜주는 역할을 하는 클래스
 */
public class ReplyListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ReplyItem> ReplyListViewItemList = new ArrayList<ReplyItem>() ;

    // ListViewAdapter의 생성자
    public ReplyListViewAdapter() {

    }

    /**
     * @brief : Adapter에 사용되는 데이터의 개수를 리턴해주는 함수
     * @return int: 댓글들의 갯수
     */
    @Override
    public int getCount() {
        return ReplyListViewItemList.size() ;
    }

    /**
     * @brief : position에 위치한 댓글을 화면에 출력하는데 사용될 View를 리턴.
     * @param : int position -  행의 index를 의미
     * @param : View convertView -  행 전체를 나타내는 뷰를 의미
     * @param : ViewGroup parent -  어댑터를 가지고 있는 부모뷰를 의미
     * @return View: 댓글목록을 화면에 출력하는데 사용될 View
     */
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


    /**
     * @brief : 선택된 행의 item의 id를 리턴하는 메소드
     * @param : int position -  행의 index를 의미
     * @return long: 선택된 row의 id를 리턴
     */
    @Override
    public long getItemId(int position) {
        return position ;
    }

    /**
     * @brief : 선택된 행의 item을 리턴
     * @param : int position -  행의 index를 의미
     * @return Object: 선택된 row의 item을 리턴
     */
    @Override
    public Object getItem(int position) {
        return ReplyListViewItemList.get(position) ;
    }


    /**
     * @brief : list에 item을 추가하기 위한 메소드.
     * @param : ReplyItem 속성들
     */
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
    /**
     * @brief : list의 모든 아이템을 제거하는 메소드.
     */
    public void removeItem(){
        ReplyListViewItemList.clear();
    }

    /**
     * @brief : list의 모든 아이템을 제거하는 메소드.
     * @return ArrayList<ReplyItem> : ReplyItem 리스트를 리턴
     */
    public ArrayList<ReplyItem> getReplyList(){
        return ReplyListViewItemList;
    }

}
