package dvorak.kosta.com.dothing_mobile.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.network.ReplyInsertNetworkTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailThreeFragment extends Fragment {

    int curYear, curMonth, curDay, curHour, curMinute;
    TextView arrivalTime;
    EditText replyContent;
    String errandsNum;
    Activity activity;


    public DetailThreeFragment() {
    }


    public static DetailThreeFragment newInstance() {
        Bundle args = new Bundle();

        DetailThreeFragment fragment = new DetailThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_three, container, false);

        activity = getActivity();
        String errandNum = getActivity().getIntent().getStringExtra("errandNum");
        errandsNum = errandNum;


        arrivalTime = (TextView) v.findViewById(R.id.arrival_time);
        replyContent = (EditText) v.findViewById(R.id.reply_content);


        //현재 날짜와 시간 가져오기
        Calendar calender = new GregorianCalendar();
        curYear = calender.get(Calendar.YEAR);
        curMonth = calender.get(Calendar.MONTH);
        curDay = calender.get(Calendar.DAY_OF_MONTH);
        curHour = calender.get(Calendar.HOUR_OF_DAY);
        curMinute = calender.get(Calendar.MINUTE);

        //updateTime();

        Button dateChoiceBtn = (Button) v.findViewById(R.id.date_choice_btn);
        dateChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateSetListener, curYear, curMonth, curDay).show();
            }
        });
        Button timeChoiceBtn = (Button) v.findViewById(R.id.time_choice_btn);
        timeChoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), timeSetListener, curHour, curMinute, false).show();
            }
        });
        Button replyInsertBtn = (Button) v.findViewById(R.id.reply_insert_btn);
        replyInsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (arrivalTime.getText().equals("예상 도착시간")) {
                        Toast.makeText(getActivity(), "시간을 선택하세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Date upTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(arrivalTime.getText() + "");
                    Date errandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(DetailOneFragment.errandTime);

                    if (upTime.getTime() < errandTime.getTime()) {
                        Toast.makeText(getActivity(), "요청자의 요청 시간보다 시간이 느립니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (MemberInfo.userId.equals(DetailTwoFragment.requestId)) {
                        Toast.makeText(getActivity(), "작성자는 댓글을 작성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ReplyInsertNetworkTask replyInsertNetworkTask = new ReplyInsertNetworkTask(activity);

                Map<String, String> params = new HashMap<String, String>();
                params.put("memberId", MemberInfo.userId);
                params.put("errandNum", errandsNum);
                params.put("arrivalTime", arrivalTime.getText().toString());
                params.put("replyContent", replyContent.getText().toString());

                replyInsertNetworkTask.execute(params);
            }
        });


        return v;
    }


    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //사용자가 선택한 값 가져온 후
            curYear = year;
            curMonth = monthOfYear;
            curDay = dayOfMonth;

            //텍스트뷰 업데이트
            updateTime();
        }
    };

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //사용자가 선택한 값 가져온 후
            curHour = hourOfDay;
            curMinute = minute;

            //텍스트뷰 업데이트
            updateTime();
        }
    };


    void updateTime() {
        System.out.println("year : " + curYear);
        System.out.println("month : " + curMonth);
        System.out.println("day : " + curDay);
        System.out.println("hour : " + curHour);
        System.out.println("minute : " + curMinute);
        System.out.println("arrivalTime : " + arrivalTime);

        if (curMinute < 10) {
            arrivalTime.setText(String.format("%d-%02d-%02d %02d:%02d", curYear, curMonth + 1, curDay, curHour, curMinute));
        } else {
            arrivalTime.setText(String.format("%d-%02d-%02d %02d:%02d", curYear, curMonth + 1, curDay, curHour, curMinute));
        }
        System.out.println("arrivalTime.getText : " + arrivalTime.getText());
    }

}

