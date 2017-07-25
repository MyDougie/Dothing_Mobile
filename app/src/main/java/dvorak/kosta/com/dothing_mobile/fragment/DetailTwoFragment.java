package dvorak.kosta.com.dothing_mobile.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;
import dvorak.kosta.com.dothing_mobile.network.DetailRequesterNetworkTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTwoFragment extends Fragment {

    View v;

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

        v = inflater.inflate(R.layout.fragment_detail_two, container, false);

        String errandNum = getActivity().getIntent().getStringExtra("errandNum");

      //tab3
        /*
        ImageView requesterUserImg = (ImageView) v.findViewById(R.id.requesterImg);
        TextView requestId = (TextView) v.findViewById(R.id.requester_id);
        TextView errandsRequestCount = (TextView) v.findViewById(R.id.errands_request_count);
        RatingBar grade = (RatingBar) v.findViewById(R.id.mannerGrade);
        */
        Map<String, View> map = new HashMap<>();
        map.put("view",v);

        DetailRequesterNetworkTask networkTask = new DetailRequesterNetworkTask(errandNum, map, getActivity());
        Map<String, String> params = new HashMap<>();
        params.put("errandNum", errandNum);
        networkTask.execute(params);

        // Inflate the layout for this fragment

        return v;
    }

    //////////

}
