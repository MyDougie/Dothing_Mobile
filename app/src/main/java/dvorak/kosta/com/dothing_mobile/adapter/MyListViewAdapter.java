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
 * 뷰와 내 심부름리스트를 연결시켜주는 역할을 하는 클래스
 */
public class MyListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ErrandsItem> listViewItemList = new ArrayList<ErrandsItem>() ;

    // ListViewAdapter의 생성자
    public MyListViewAdapter() {

    }

    /**
     * Adapter에 사용되는 데이터의 개수를 리턴해주는 함수
     * @return 내 심부름들의 갯수
     */
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    /**
     * position에 위치한 나의 심부름을 화면에 출력하는데 사용될 View를 리턴.
     * @param position 행의 index를 의미
     * @param convertView 행 전체를 나타내는 뷰를 의미
     * @param parent 어댑터를 가지고 있는 부모뷰를 의미
     * @return 나의 심부름 목록을 화면에 출력하는데 사용될 View
     */
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
    /**
     * 선택된 행의 item의 id를 리턴하는 메소드
     * @param position 행의 index를 의미
     * @return 선택된 row의 id를 리턴
     */
    @Override
    public long getItemId(int position) {
        return position ;
    }

    /**
     * 선택된 행의 item을 리턴
     * @param position 행의 index를 의미
     * @return 선택된 row의 item을 리턴
     */
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    /**
     * list에 item을 추가하기 위한 메소드.
     * @param errandsItem 값이 set되어진 errandsItem
     */
    public void addItem(ErrandsItem errandsItem) {
        listViewItemList.add(errandsItem);
    }
    /**
     * list의 모든 아이템을 제거하는 메소드.
     */
    public void removeItem(){
        listViewItemList.clear();
    }

}
