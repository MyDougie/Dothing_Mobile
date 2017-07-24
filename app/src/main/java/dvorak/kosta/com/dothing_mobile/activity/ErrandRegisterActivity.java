package dvorak.kosta.com.dothing_mobile.activity;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.listener.RegisterErrandListener;
import dvorak.kosta.com.dothing_mobile.network.UploadDataNetworkTask;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

public class ErrandRegisterActivity extends ActivityGroup {
    String fileName, imgPath;
    ImageView registerImage;
    EditText registerDate;
    EditText registerTime;
    EditText registerTitle;
    EditText registerContent;
    EditText registerErrandPrice;
    EditText registerProductPrice;
    EditText registerAddr;
    EditText registerDetailAddr;
    String registerTitleString;
    String registerContentString;
    String registerErrandPriceString;
    String registerProductPriceString;
    String errandTimeString;
    String registerAddrString, registerDetailAddrString;
    public String latitude, longitude;
    FloatingActionButton okBtn;
    MapView mapView;
    ViewGroup mapViewContainer;
    RegisterErrandListener registerErrandListener;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;


    /**
     * 날짜 선택후 텍스트창에 날짜 넣기
     */
    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            registerDate.setText(year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth));
        }
    };

    /**
     * 시간 선택후 텍스트창에 시간 넣기
     */
    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            registerTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        mapViewContainer.removeView(mapView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerErrandListener = new RegisterErrandListener(registerAddr, this, latitude, longitude);
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("6301c8d166630b078ad13401acc1267f");
        mapView.setMapViewEventListener(registerErrandListener);
        mapViewContainer = (ViewGroup) findViewById(R.id.registerMap);
        mapViewContainer.addView(mapView);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mapView.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        mapView.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                mapView.onTouchEvent(event);
                return true;
            }
        });



    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage("작성을 취소하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         ErrandRegisterActivity.this.finish();
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });

        AlertDialog alert = alert_confirm.create();
        alert.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errand_register);
        okBtn = (FloatingActionButton)findViewById(R.id.errandRegisterBtn);
        registerImage = (ImageView) findViewById(R.id.registerImage);
        registerTitle = (EditText) findViewById(R.id.registerTitle);
        registerContent = (EditText) findViewById(R.id.registerContent);
        registerErrandPrice = (EditText) findViewById(R.id.registerErrandPrice);
        registerProductPrice = (EditText) findViewById(R.id.registerProductPrice);
        registerDate = (EditText) findViewById(R.id.registerDate);
        registerTime = (EditText) findViewById(R.id.registerTime);
        registerAddr = (EditText) findViewById(R.id.registerAddr);
        registerDetailAddr = (EditText) findViewById((R.id.registerDetailAddr));

        Button uploadBtn = (Button) findViewById(R.id.selectImage);


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> params = new HashMap<>();
                if(checkValid()){
                    if(imgPath != null){
                        params.put("errandsPhotoFile",new File(imgPath));
                    }
                    params.put("requestUser.userId", MemberInfo.userId);
                    params.put("endTime", errandTimeString);
                    params.put("productPrice", registerProductPriceString);
                    params.put("errandsPrice", registerErrandPriceString);
                    params.put("title", registerTitleString);
                    params.put("content", registerContentString);
                    params.put("errandsPos.addr", registerAddrString + " " + registerDetailAddrString);
                    params.put("errandsPos.latitude", latitude);
                    params.put("errandsPos.longitude", longitude);
                    Log.e("여기서 왜?", latitude +":"+longitude);
                    new UploadDataNetworkTask(ConstantUtil.ipAddr + "androidErrand/insertErrand", ErrandRegisterActivity.this).execute(params);

                }
            }
        });
        registerAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ErrandRegisterActivity.this, RegisterWebviewActivity.class);
                startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
            }
        });


        uploadBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakeAlbumAction();
            }
        });


        registerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar();
                DatePickerDialog dialog = new DatePickerDialog(ErrandRegisterActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        registerTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = new GregorianCalendar();
                TimePickerDialog dialog = new TimePickerDialog(ErrandRegisterActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                dialog.show();
            }
        });
    }

    /**
     * 유효성 검사
     */
    public boolean checkValid() {
        registerTitleString = registerTitle.getText().toString().trim();
        registerContentString = registerContent.getText().toString().trim();
        registerErrandPriceString = registerErrandPrice.getText().toString().trim();
        registerProductPriceString = registerProductPrice.getText().toString().trim();
        registerAddrString = registerAddr.getText().toString().trim();
        registerDetailAddrString = registerDetailAddr.getText().toString().trim();
        if (registerTitleString.equals("")) {
            Toast.makeText(ErrandRegisterActivity.this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
            registerTitle.requestFocus();
            return false;
        } else if (registerContentString.equals("")) {
            Toast.makeText(ErrandRegisterActivity.this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
            registerContent.requestFocus();
            return false;
        } else if (registerErrandPriceString.equals("")) {
            Toast.makeText(ErrandRegisterActivity.this, "심부름가격을 입력하세요", Toast.LENGTH_SHORT).show();
            registerErrandPrice.requestFocus();
            return false;
        } else if (registerProductPriceString.equals("")) {
            Toast.makeText(ErrandRegisterActivity.this, "물건가격 입력하세요", Toast.LENGTH_SHORT).show();
            registerProductPrice.requestFocus();
            return false;
        } else if (registerTime.getText().toString().trim().equals("")) {
            Toast.makeText(ErrandRegisterActivity.this, "원하는 심부름 시간을 입력하세요", Toast.LENGTH_SHORT).show();
            registerTime.requestFocus();
            return false;
        } else if (registerDate.getText().toString().trim().equals("")) {
            Toast.makeText(ErrandRegisterActivity.this, "원하는 심부름 날짜를 입력하세요", Toast.LENGTH_SHORT).show();
            registerDate.requestFocus();
            return false;
        }else if(registerAddrString.equals("")){
            Toast.makeText(ErrandRegisterActivity.this, "주소를 입력하세요", Toast.LENGTH_SHORT).show();
            registerAddr.requestFocus();
            return false;
        }else if(registerDetailAddrString.equals("")){
            Toast.makeText(ErrandRegisterActivity.this, "상세 주소를 입력하세요", Toast.LENGTH_SHORT).show();
            registerDetailAddr.requestFocus();
            return false;
        }
        errandTimeString = registerDate.getText().toString() + " " + registerTime.getText().toString();
        try {
            Date upTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(errandTimeString);
            Date currentTime = new Date();
            if (upTime.getTime() < currentTime.getTime()) {
                Toast.makeText(ErrandRegisterActivity.this, "현재 시간보다 원하는 날짜&시간이 빠릅니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }


    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    public String getRealImagePath(Uri uriPath) {
        String[] proj = { MediaStore.Images.Media.DATA };

        CursorLoader cursorLoader = new CursorLoader(this, uriPath, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    /**
     * 주소 검색 이후 처리 / 앨범 검색 이후 처리
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 1) {
            registerImage.setImageURI((intent.getData()));
            imgPath = getRealImagePath(intent.getData());
        } else if (requestCode == SEARCH_ADDRESS_ACTIVITY) {
            String data = intent.getExtras().getString("data");
            if (data != null) {
                mapView.removeAllPOIItems();
                // data = 주소:위도:경도
                registerAddr.setText(data.split(":")[0]);
                latitude = data.split(":")[1];
                longitude = data.split(":")[2];
            }
        }


    }

}



