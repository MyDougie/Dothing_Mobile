package dvorak.kosta.com.dothing_mobile.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.fragment.DetailOneFragment;
import dvorak.kosta.com.dothing_mobile.fragment.DetailThreeFragment;
import dvorak.kosta.com.dothing_mobile.fragment.DetailTwoFragment;

public class DetailViewActivity extends AppCompatActivity {
    Context mContext;
    ViewPager vp;
    LinearLayout ll;
    private static int TAB_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);


        ////////탭 시작////////
        vp = (ViewPager)findViewById(R.id.vp);
        ll = (LinearLayout)findViewById(R.id.ll);

        TextView tab_first = (TextView)findViewById(R.id.tab_first);
        TextView tab_second = (TextView)findViewById(R.id.tab_second);
        TextView tab_third = (TextView)findViewById(R.id.tab_third);

        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);

        tab_first.setOnClickListener(movePageListener);
        tab_first.setTag(0);
        tab_second.setOnClickListener(movePageListener);
        tab_second.setTag(1);
        tab_third.setOnClickListener(movePageListener);
        tab_third.setTag(2);

        tab_first.setSelected(true);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                for(int i=0; i<TAB_COUNT; i++){
                    if(position==i) {
                        ll.findViewWithTag(i).setSelected(true);
                    }
                    else {
                        ll.findViewWithTag(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });


    }


    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();
            for(int i=0; i<TAB_COUNT; i++){
                if(tag==i) {
                    ll.findViewWithTag(i).setSelected(true);
                }
                else {
                    ll.findViewWithTag(i).setSelected(false);
                }
            }
            vp.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new DetailOneFragment();
                case 1:
                    return new DetailTwoFragment();
                case 2:
                    return new DetailThreeFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
    ////////탭 끝////////

}
