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
public class DetailOneFragment extends Fragment {


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
        String errandNum = getActivity().getIntent().getStringExtra("errandNum");
        System.out.println("detailOneFragment's errandNum : " + errandNum);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_one, container, false);
    }

}

