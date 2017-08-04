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
 * 뷰와 심부름아이템을 연결시켜주는 역할을 하는 클래스
 */
public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ErrandsItem> listViewItemList = new ArrayList<ErrandsItem>() ;
    // ListViewAdapter의 생성자
    public ListViewAdapter() {
    }

    /**
     * Adapter에 사용되는 데이터의 개수를 리턴해주는 함수
     * @return 심부름의 갯수
     */
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    /**
     * position에 위치한 심부름을 화면에 출력하는데 사용될 View를 리턴.
     * @param position 행의 index를 의미
     * @param convertView 행 전체를 나타내는 뷰를 의미
     * @param parent 어댑터를 가지고 있는 부모뷰를 의미
     * @return 심부름목록을 화면에 출력하는데 사용될 View
     */
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

    /**
     * list의 모든 아이템을 제거하는 메소드.
     * @return ErrandsItem 리스트를 리턴
     */
    public ArrayList<ErrandsItem> getErrandList(){
        return listViewItemList;
    }

}
