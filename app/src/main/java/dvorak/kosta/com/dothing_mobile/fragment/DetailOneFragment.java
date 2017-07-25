package dvorak.kosta.com.dothing_mobile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.ReplyListViewAdapter;
import dvorak.kosta.com.dothing_mobile.network.DetailErrandNetworkTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailOneFragment extends Fragment {
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

        final ListView listView = (ListView)v.findViewById(R.id.replyList);
        listView.setAdapter(adapter);

        String errandNum = getActivity().getIntent().getStringExtra("errandNum");
        System.out.println("detailOneFragment's errandNum : " + errandNum);

        Map<String, View> map = new HashMap<>();
        map.put("view",v);

        DetailErrandNetworkTask networkTask = new DetailErrandNetworkTask(errandNum, map, adapter);
        Map<String, String> params = new HashMap<>();
        params.put("errandNum", errandNum);
        networkTask.execute(params);
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
        return v;
    }

}

