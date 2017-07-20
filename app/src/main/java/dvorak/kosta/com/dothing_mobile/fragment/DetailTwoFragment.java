package dvorak.kosta.com.dothing_mobile.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dvorak.kosta.com.dothing_mobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTwoFragment extends Fragment {

    public DetailTwoFragment() {
        // Required empty public constructor
    }

    public static DetailTwoFragment newInstance(){
        Bundle args = new Bundle();

        DetailTwoFragment fragment = new DetailTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_two, container, false);

   /*     //tab3
        ImageView requesterUserImg = (ImageView) v.findViewById(R.id.requester_user_img);
        EditText requesterName = (EditText) v.findViewById(R.id.name);
        EditText errandsRequestCount = (EditText) v.findViewById(R.id.errands_request_count);
        EditText grade = (EditText) v.findViewById(R.id.grade);
        EditText hashtag = (EditText) v.findViewById(R.id.hashtag);
        Map<String, View> map = new HashMap<>();
        map.put("requesterUserImg",requesterUserImg);
        map.put("requesterName",requesterName);
        map.put("errandsRequestCount",errandsRequestCount);
        map.put("grade",grade);
        map.put("hashtag",hashtag);

        String errandNum = getActivity().getIntent().getStringExtra("errandNum");
        System.out.println("detailTwoFragment's errandNum : " + errandNum);

        DetailRequesterNetworkTask networkTask = new DetailRequesterNetworkTask(errandNum, map);
        Map<String, String> params = new HashMap<>();
        params.put("errandNum", errandNum);
        networkTask.execute(params);*/

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_two, container, false);
    }

}
