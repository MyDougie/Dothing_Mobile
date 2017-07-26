package dvorak.kosta.com.dothing_mobile.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.ReplyListViewAdapter;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.item.ReplyItem;
import dvorak.kosta.com.dothing_mobile.network.DetailErrandNetworkTask;
import dvorak.kosta.com.dothing_mobile.network.StartErrandNetworkTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailOneFragment extends Fragment{

    public static String errandTime;
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

        final String errandNum = getActivity().getIntent().getStringExtra("errandNum");
       /* String requestUserId = getActivity().getIntent().getStringExtra("requestUserId");
        Log.i("requestUserId!!!!", requestUserId);*/

        final ListView listView = (ListView)v.findViewById(R.id.replyList);

        listView.setAdapter(adapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setSelected(true);
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

        //심부름을 등록한 유저 ID 와 접속한 유저 ID 같아야함.
    //if(requestUserId.equals(MemberInfo.userId)) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
                view.setSelected(true);
                ReplyItem item = (ReplyItem) parent.getItemAtPosition(position);
                int click = item.getClick();
                if (click == 0) {//선택안된 것을 처음 클릭했을 때
                    /**
                     * 다른 아이템들을 setClick(0)해주어야 함
                     * */
                    ArrayList<ReplyItem> list = adapter.getReplyList();
                    Log.i("replyNum : ", item.getReplyNum() + "");
                    for (ReplyItem it : list) {
                        if (it.getReplyNum() != item.getReplyNum()) {
                            it.setClick(0);
                        }
                    }
                    item.setClick(1);//한번선택됨


                } else if (click == 1) {//선택된 셀을 다시 클릭
                    item.setClick(0);
                       /* Intent intent = new Intent(getApplicationContext(), DetailViewActivity.class);
                        intent.putExtra("errandNum",item.getErrandNum()+"");
                        startActivity(intent);*/
                    //모달 띄우고 확인 누르면 매칭

                    final String responseUserId = item.getUser().getId();

                    new AlertDialog.Builder(getContext())
                            .setTitle("심부름꾼 선택")
                            .setMessage("'" + item.getUser().getName() + "' 심부름꾼을 선택하시겠습니까?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Map<String, View> map = new HashMap<>();
                                    map.put("view", v);

                                    StartErrandNetworkTask networkTask = new StartErrandNetworkTask(getActivity());
                                    Map<String, String> params = new HashMap<>();
                                    params.put("requestUserId", MemberInfo.userId);
                                    params.put("strErrandNum", errandNum);
                                    params.put("responseUserId", responseUserId);
                                    networkTask.execute(params);

                                    //   Toast.makeText(getActivity(), "매칭되었습니다!!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(getActivity(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }).show();

                }
            }
        });
   // }

        Map<String, View> map = new HashMap<>();
        map.put("view", v);

        DetailErrandNetworkTask networkTask = new DetailErrandNetworkTask(errandNum, map, adapter);
        Map<String, String> params = new HashMap<>();
        params.put("errandNum", errandNum);
        networkTask.execute(params);


        return v;
    }

}

