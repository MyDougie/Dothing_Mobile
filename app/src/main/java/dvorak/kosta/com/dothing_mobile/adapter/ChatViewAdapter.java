package dvorak.kosta.com.dothing_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.item.ChatItem;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @breif : 뷰와 채팅아이템을 연결시켜주는 역할을 하는 클래스
 */
public class ChatViewAdapter extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_MAX = 2 ;
    private static final int LEFT_ITEM = 0 ;
    private static final int RIGHT_ITEM = 1 ;


    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ChatItem> listViewItemList = new ArrayList<ChatItem>() ;
    Context context;
    // ListViewAdapter의 생성자
    public ChatViewAdapter(Context context) {
this.context = context;
    }

    /**
     * @brief : Adapter에 사용되는 데이터의 개수를 리턴해주는 함수
     * @return int: 채팅메세지들의 갯수
     */
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override public int getViewTypeCount() { return ITEM_VIEW_TYPE_MAX ; } // position 위치의 아이템 타입 리턴.
    @Override public int getItemViewType(int position) { return listViewItemList.get(position).getType() ; }


    /**
     * @brief : position에 위치한 채팅메세지를 화면에 출력하는데 사용될 View를 리턴.
     * @param : int position -  행의 index를 의미
     * @param : View convertView -  행 전체를 나타내는 뷰를 의미
     * @param : ViewGroup parent -  어댑터를 가지고 있는 부모뷰를 의미
     * @return View: 채팅메세지목록을 화면에 출력하는데 사용될 View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        int viewType = getItemViewType(position);
        // "listview_item" Layout을 inflate하여 convertView 참조 획득.

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ChatItem chatItem = listViewItemList.get(position);

            switch (viewType) {
                case LEFT_ITEM:
                    convertView = inflater.inflate(R.layout.left_chat_item, parent, false);
                    TextView chatContent = (TextView) convertView.findViewById(R.id.chatContent);
                    TextView chatDate = (TextView) convertView.findViewById(R.id.chatDate);
                    ImageView chatImg = (ImageView) convertView.findViewById(R.id.chatImage);

                    chatContent.setText(chatItem.getChat());
                    chatDate.setText(chatItem.getDate());
                    Glide.with(context).load(chatItem.getUserImgPath()).bitmapTransform(new CropCircleTransformation(context)).into(chatImg);
                    break;
                case RIGHT_ITEM:
                    convertView = inflater.inflate(R.layout.right_chat_item, parent, false);
                    TextView chatContent2 = (TextView) convertView.findViewById(R.id.chatContent2);
                    TextView chatDate2 = (TextView) convertView.findViewById(R.id.chatDate2);
                    ImageView chatImg2 = (ImageView) convertView.findViewById(R.id.chatImage2);
;
                    chatContent2.setText(chatItem.getChat());
                    chatDate2.setText(chatItem.getDate());
                    Glide.with(context).load(chatItem.getUserImgPath()).bitmapTransform(new CropCircleTransformation(context)).into(chatImg2);
                    break;
            }

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
        return listViewItemList.get(position) ;
    }

    /**
     * @brief : list에 item을 뷰의 왼쪽에 추가하기 위한 메소드.
     * @param : ChatItem 속성들
     */
    public void addItemLeft(String id, String chat, String urlPath, String date) {
        ChatItem chatItem = new ChatItem();
        chatItem.setType(LEFT_ITEM);
        chatItem.setUserImgPath(urlPath);
        chatItem.setUserId(id);
        chatItem.setDate(date);
        chatItem.setChat(chat);
        listViewItemList.add(chatItem);
    }
    /**
     * @brief : list에 item을 뷰의 오른쪽에 추가하기 위한 메소드.
     * @param : ChatItem 속성들
     */
    public void addItemRight(String id, String chat, String urlPath, String date) {
        ChatItem chatItem = new ChatItem();
        chatItem.setType(RIGHT_ITEM);
        chatItem.setUserImgPath(urlPath);
        chatItem.setUserId(id);
        chatItem.setDate(date);
        chatItem.setChat(chat);
        listViewItemList.add(chatItem);
    }
    /**
     * @brief : list의 모든 아이템을 제거하는 메소드.
     */
    public void removeItem(){
        listViewItemList.clear();
    }

    /**
     * @brief : list의 모든 아이템을 제거하는 메소드.
     * @return ArrayList<ChatItem> : ChatItem 리스트를 리턴
     */
    public ArrayList<ChatItem> getErrandList(){
        return listViewItemList;
    }

}
