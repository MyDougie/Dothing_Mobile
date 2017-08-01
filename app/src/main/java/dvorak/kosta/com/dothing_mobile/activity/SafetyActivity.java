package dvorak.kosta.com.dothing_mobile.activity;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.network.UploadDataNetworkTask;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

public class SafetyActivity extends Activity {
    Button safeBtn, safeSubmitBtn;
    ImageView safeImg;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);
safeSubmitBtn = (Button)findViewById(R.id.safeSubmitBtn);
        safeBtn = (Button) findViewById(R.id.safeBtn);
        safeImg = (ImageView) findViewById(R.id.safeImg);
        safeBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakeAlbumAction();
            }
        });
        safeSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgPath == null){
                    Toast.makeText(SafetyActivity.this, "이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, Object> params = new HashMap<>();
                params.put("ssnImgFile", new File(imgPath));
                params.put("userId", MemberInfo.userId);
                UploadDataNetworkTask uploadDataNetworkTask = new UploadDataNetworkTask(ConstantUtil.ipAddr + "androidMember/submitSafety", SafetyActivity.this);
                uploadDataNetworkTask.execute(params);
                Toast.makeText(SafetyActivity.this, "등록 완료되었습니다.", Toast.LENGTH_SHORT);
                MemberInfo.ssnImg = "OK";
                finish();
            }
        });
    }

    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    public String getRealImagePath(Uri uriPath) {
        String[] proj = {MediaStore.Images.Media.DATA};

        CursorLoader cursorLoader = new CursorLoader(this, uriPath, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) return;
        if (requestCode == 1) {
            safeImg.setImageURI((intent.getData()));
            imgPath = getRealImagePath(intent.getData());
        }
    }


}

