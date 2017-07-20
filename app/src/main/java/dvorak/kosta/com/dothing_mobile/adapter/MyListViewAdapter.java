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
import dvorak.kosta.com.dothing_mobile.item.ErrandsItem;

/**
 * Created by YTK on 2017-07-12.
 */

public class MyListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ErrandsItem> listViewItemList = new ArrayList<ErrandsItem>() ;

    // ListViewAdapter의 생성자
    public MyListViewAdapter() {

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
            convertView = inflater.inflate(R.layout.activity_myerrands, parent, false);
        }
        TextView errandTitle = (TextView) convertView.findViewById(R.id.errandMyTitle);
        TextView errandPrice = (TextView) convertView.findViewById(R.id.errandMyTotalPrice);
        TextView errandAddr = (TextView) convertView.findViewById(R.id.errandMyaddr);
        TextView errandTime = (TextView)convertView.findViewById(R.id.errandMyDate);
        TextView errandReplyNum = (TextView) convertView.findViewById(R.id.errandReplyNumber);
        TextView errandState = (TextView) convertView.findViewById(R.id.errandMyState);
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ErrandsItem errandsItem = listViewItemList.get(position);

        DecimalFormat formatter = new DecimalFormat("#,###");
        // 아이템 내 각 위젯에 데이터 반영
        String errandFormatPrice = formatter.format(Double.parseDouble(errandsItem.getErrandPrice()));
        errandTitle.setText(errandsItem.getErrandTitle());
        errandPrice.setText(errandFormatPrice +"원");
        errandAddr.setText(errandsItem.getAddr());
        errandTime.setText("~" + errandsItem.getErrandTime());
        errandReplyNum.setText(errandsItem.getReplyNum()+"개") ;
        errandState.setText(errandsItem.getState());
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

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(int errandsNum, String title, String errandPrice, String addr, String time, String reply, String state) {
        ErrandsItem item = new ErrandsItem();
        item.setErrandNum(errandsNum);
        item.setErrandTitle(title);
        item.setErrandPrice(errandPrice);
        item.setAddr(addr);
        item.setErrandTime(time);
        item.setClick(0);
        item.setReplyNum(reply);
        item.setState(state);
        listViewItemList.add(item);
    }
    //리스트의 모든 아이템 제거
    public void removeItem(){
        listViewItemList.clear();
    }

}
