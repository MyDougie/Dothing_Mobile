package dvorak.kosta.com.dothing_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.item.ErrandsItem;

/**
 * Created by YTK on 2017-07-12.
 */

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ErrandsItem> listViewItemList = new ArrayList<ErrandsItem>() ;
    // ListViewAdapter의 생성자
    public ListViewAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_errands, parent, false);
        }

        TextView errandTitle = (TextView) convertView.findViewById(R.id.errandTitle);
        TextView errandPrice = (TextView) convertView.findViewById(R.id.errandPrice);
        TextView errandAddr = (TextView) convertView.findViewById(R.id.errandAddr);
        TextView errandTime = (TextView)convertView.findViewById(R.id.errandTime);
        TextView replyNumText = (TextView)convertView.findViewById(R.id.replyNumText);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ErrandsItem errandsItem = listViewItemList.get(position);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date currentDate = new Date();
        Date errandDate = null;
        try {
            errandDate = dateFormatter.parse(errandsItem.getErrandTime());
        }catch(Exception e){
            e.printStackTrace();
        }
        long hour = 60 * 60 * 1000;
        long diff = errandDate.getTime() - currentDate.getTime();
        int diffDay = (int)(diff / (24 * 60 * 60 * 1000));
        long diffSubHour = diff - (diff - (int)(diff / 60 * 60 * 1000));
        int diffHour = (int)((diff - (diffDay * 24 * hour)) / hour);
        int diffMin =  (int)(diff - ((diffDay * 24 * hour) + (diffHour * hour)))/ (60*1000);
        DecimalFormat formatter = new DecimalFormat("#,###");
        // 아이템 내 각 위젯에 데이터 반영
        String errandFormatPrice = formatter.format(Double.parseDouble(errandsItem.getErrandPrice()));
        errandTitle.setText(errandsItem.getErrandTitle());
        errandPrice.setText("보상 " +errandFormatPrice +"원");
        errandAddr.setText(errandsItem.getAddr());
        errandTime.setText(diffDay + "일 " +  diffHour +"시 " + diffMin +"분 남음");
        replyNumText.setText(errandsItem.getReplyNum() +"명 지원중");
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
        return listViewItemList.get(position) ;
    }


    public void addItem(String requesteUserId, int errandNum, String title, String errandPrice, String addr, String latitude, String longitude, String time, int replyNum) {

        ErrandsItem item = new ErrandsItem();
        item.setRequesterId(requesteUserId);
        item.setErrandNum(errandNum);
        item.setErrandTitle(title);
        item.setErrandPrice(errandPrice);
        item.setAddr(addr);
        item.setLatitude(latitude);
        item.setLongitude(longitude);
        item.setErrandTime(time);
        item.setClick(0);
        item.setReplyNum(replyNum + "");
        listViewItemList.add(item);
    }
    //리스트의 모든 아이템 제거
    public void removeItem(){
        listViewItemList.clear();
    }

    //아이템 리스트를 리턴
    public ArrayList<ErrandsItem> getErrandList(){
        return listViewItemList;
    }

}
