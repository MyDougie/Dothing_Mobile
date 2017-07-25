package dvorak.kosta.com.dothing_mobile.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.daum.mf.map.api.MapPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.activity.DetailViewActivity;
import dvorak.kosta.com.dothing_mobile.adapter.ReplyListViewAdapter;
import dvorak.kosta.com.dothing_mobile.item.ErrandsItem;
import dvorak.kosta.com.dothing_mobile.item.ReplyItem;
import dvorak.kosta.com.dothing_mobile.network.DetailErrandNetworkTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailOneFragment extends Fragment {
    View v;
    ReplyListViewAdapter adapter = new ReplyListViewAdapter();

    public DetailOneFragment() {
        // Required empty public constructor
    }

    public static DetailOneFragment newInstance(){
        Bundle args = new Bundle();

        DetailOneFragment fragment = new DetailOneFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_detail_one, container, false);

        final ListView listView = (ListView)v.findViewById(R.id.replyList);
        listView.setAdapter(adapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        listView.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        listView.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                listView.onTouchEvent(event);
                return true;

                // Inflate the layout for this fragment
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                view.setSelected(true);
                ReplyItem item = (ReplyItem) parent.getItemAtPosition(position);
                int click = item.getClick();
                if(click == 0) {//선택안된 것을 처음 클릭했을 때
                    /**
                     * 다른 아이템들을 setClick(0)해주어야 함
                     * */
                    ArrayList<ReplyItem> list =  adapter.getReplyList();
                    Log.i("replyNum : ", item.getReplyNum()+"");
                    for(ReplyItem it : list){
                        if(it.getReplyNum() != item.getReplyNum()){
                            it.setClick(0);
                        }
                    }
                    item.setClick(1);//한번선택됨


                }else if(click == 1){//선택된 셀을 다시 클릭
                    item.setClick(0);
                   /* Intent intent = new Intent(getApplicationContext(), DetailViewActivity.class);
                    intent.putExtra("errandNum",item.getErrandNum()+"");
                    startActivity(intent);*/
                   //모달 띄우고 확인 누르면 매칭

                }
            }
        });



        String errandNum = getActivity().getIntent().getStringExtra("errandNum");
        System.out.println("detailOneFragment's errandNum : " + errandNum);

        Map<String, View> map = new HashMap<>();
        map.put("view",v);

        DetailErrandNetworkTask networkTask = new DetailErrandNetworkTask(errandNum, map, adapter);
        Map<String, String> params = new HashMap<>();
        params.put("errandNum", errandNum);
        networkTask.execute(params);




        return v;
    }

}

