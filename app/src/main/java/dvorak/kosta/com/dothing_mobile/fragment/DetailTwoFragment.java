package dvorak.kosta.com.dothing_mobile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.network.DetailRequesterNetworkTask;

/**
 * DetailViewActivity의 전체 UI중에 두번째 탭부분을 담당하는 클래스
 */
public class DetailTwoFragment extends Fragment {

    View v;
    public static String requestId;
    public DetailTwoFragment() {
        // Required empty public constructor
    }

    /**
     * Bundle객체를 통해 데이터들을 전송하는 메소드.
     * @return Fragment객체를 리턴.
     */
    public static DetailTwoFragment newInstance(){
        Bundle args = new Bundle();

        DetailTwoFragment fragment = new DetailTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * XML 레이아웃을 inflate하여 Fragment를 위한 View를 생성하고, Fragment 레이아웃의 root에 해당되는 View를 Activity에게 리턴하는 메소드.
     * @param inflater xml 레이아웃을 inflate할 객체
     * @param container inflate된 레이아웃의 상위가 될 ViewGroup
     * @param savedInstanceState 프래그먼트가 재개되는 중에 프래그먼트의 이전 인스턴스에 대한 데이터를 제공
     * @return Fragment 레이아웃의 root에 해당되는 View를 Activity에게 리턴.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_detail_two, container, false);

        String errandNum = getActivity().getIntent().getStringExtra("errandNum");

        Map<String, View> map = new HashMap<>();
        map.put("view",v);

        DetailRequesterNetworkTask networkTask = new DetailRequesterNetworkTask(errandNum, map, getActivity());
        Map<String, String> params = new HashMap<>();
        params.put("errandNum", errandNum);
        networkTask.execute(params);

        return v;
    }

}
