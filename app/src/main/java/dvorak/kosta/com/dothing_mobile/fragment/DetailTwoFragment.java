package dvorak.kosta.com.dothing_mobile.fragment;


import android.content.Context;
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
        return inflater.inflate(R.layout.fragment_detail_two, container, false);
    }

}
