package dvorak.kosta.com.dothing_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.item.ChatListItem;

/**
 * @breif : 뷰와 채팅 리스트를 연결시켜주는 역할을 하는 클래스
 */

public class ChatListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ChatListItem> listViewItemList = new ArrayList<ChatListItem>() ;

    // ListViewAdapter의 생성자
    public ChatListViewAdapter() {

    }

    /**
     * @brief : Adapter에 사용되는 데이터의 개수를 리턴해주는 함수
     * @return int: 채팅목록의 갯수
     */
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    /**
     * @brief : position에 위치한 채팅목록을 화면에 출력하는데 사용될 View를 리턴.
     * @param : int position -  행의 index를 의미
     * @param : View convertView -  행 전체를 나타내는 뷰를 의미
     * @param : ViewGroup parent -  어댑터를 가지고 있는 부모뷰를 의미
     * @return View: 채팅목록을 화면에 출력하는데 사용될 View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_list_item, parent, false);
        }
        TextView chatListTitle = (TextView) convertView.findViewById(R.id.chatListTitle);
        TextView chatListName = (TextView) convertView.findViewById(R.id.chatListName);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
       ChatListItem chatItem = listViewItemList.get(position);

        DecimalFormat formatter = new DecimalFormat("#,###");
        // 아이템 내 각 위젯에 데이터 반영
        chatListTitle.setText(chatItem.getChatTitle());
        chatListName.setText(chatItem.getChatName() + "님과의 대화");

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
     * @brief : list에 item을 추가하기 위한 메소드.
     * @param : ChatListItem 속성들
     */
    public void addItem(String title, String name, String errandsNum, String userImgPath, String userImgPathTwo, String you, boolean isRequest) {
        ChatListItem chatListItem = new ChatListItem();
        chatListItem.setChatName(name);
        chatListItem.setChatTitle(title);
        chatListItem.setErradsNum(errandsNum);
        chatListItem.setUserImgPath(userImgPath);
        chatListItem.setUserImgPathTwo(userImgPathTwo);
        chatListItem.setYou(you);
        chatListItem.setRequest(isRequest);
        listViewItemList.add(chatListItem);
    }
    /**
     * @brief : list의 모든 아이템을 제거하는 메소드.
     */
    public void removeItem(){
        listViewItemList.clear();
    }

    /**
     * @brief : list의 모든 아이템을 제거하는 메소드.
     * @return ArrayList<ChatListItem> : ChatListItem 리스트를 리턴
     */
    public ArrayList<ChatListItem> getErrandList(){
        return listViewItemList;
    }

}
