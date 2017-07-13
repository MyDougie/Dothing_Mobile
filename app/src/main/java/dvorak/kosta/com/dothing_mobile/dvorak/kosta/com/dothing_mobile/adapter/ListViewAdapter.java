package dvorak.kosta.com.dothing_mobile.dvorak.kosta.com.dothing_mobile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.dvorak.kosta.com.dothing_mobile.item.ErrandsItem;

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
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView errandImg = (ImageView) convertView.findViewById(R.id.errandImg);
        TextView errandTitle = (TextView) convertView.findViewById(R.id.errandTitle);
        TextView errandContent = (TextView) convertView.findViewById(R.id.errandContent);
        TextView errandPrice = (TextView) convertView.findViewById(R.id.errandPrice);
        TextView productPrice = (TextView) convertView.findViewById(R.id.productPrice);
        TextView errandAddr = (TextView) convertView.findViewById(R.id.errandAddr);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ErrandsItem errandsItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        errandImg.setImageDrawable(errandsItem.getErrandImg());
        errandTitle.setText(errandsItem.getErrandTitle());
        errandContent.setText(errandsItem.getErrandContent());
        errandPrice.setText(errandsItem.getErrandPrice());
        productPrice.setText(errandsItem.getProductPrice());
        errandAddr.setText(errandsItem.getAddr());

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
    public void addItem(Drawable icon, String title, String content, String productPrice, String errandPrice, String addr) {
        ErrandsItem item = new ErrandsItem();

        item.setErrandImg(icon);
        item.setErrandTitle(title);
        item.setErrandContent(content);
        item.setErrandPrice(errandPrice);
        item.setProductPrice(productPrice);
        item.setAddr(addr);
        item.setClick(0);
        listViewItemList.add(item);
    }


}
