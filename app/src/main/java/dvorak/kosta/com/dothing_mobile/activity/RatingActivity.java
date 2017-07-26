package dvorak.kosta.com.dothing_mobile.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.network.EvalFinishNetworkTask;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by YTK on 2017-07-24.
 */

public class RatingActivity extends Activity implements View.OnClickListener{
    String errandsNum;
    RatingBar responseAccuracy, responseSpeed, responseKindness, requestManners;
    EditText requestEvalHash, responseEvalHash;
    boolean isRequest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        isRequest = getIntent().getExtras().getBoolean("isRequest");
        String userId = getIntent().getExtras().getString("userId");
        String name = getIntent().getExtras().getString("name");
        String imgPath = getIntent().getExtras().getString("imgPath");
        errandsNum = getIntent().getExtras().getString("errandsNum");
        if(isRequest) {
            setContentView(R.layout.rating_system_response);
            TextView responseEvalName = (TextView)findViewById(R.id.responseEvalName);
            ImageView responseImg = (ImageView)findViewById(R.id.responseEvalImg);
            Button requestEvalOkBtn = (Button)findViewById(R.id.responseEvalOkBtn);
            Button requestEvalCancelBtn = (Button)findViewById(R.id.responseEvalCancelBtn);

            responseAccuracy = (RatingBar)findViewById(R.id.responseAccuracy);
            responseKindness = (RatingBar)findViewById(R.id.responseKindness);
            responseSpeed = (RatingBar)findViewById(R.id.responseSpeed);
            responseEvalHash = (EditText)findViewById(R.id.responseEvalHash);
            requestEvalOkBtn.setOnClickListener(this);
            requestEvalCancelBtn.setOnClickListener(this);
            responseEvalName.setText(name);
            Glide.with(this).load(imgPath).bitmapTransform(new CropCircleTransformation(this)).into(responseImg);
        }else{
            setContentView(R.layout.rating_system_request);
            TextView requestEvalName = (TextView)findViewById(R.id.requestEvalName);
            ImageView requestImg = (ImageView)findViewById(R.id.requestEvalImg);
            Button resonseEvalOkBtn = (Button)findViewById(R.id.requestEvalOkBtn);
            Button responseEvalCancelBtn = (Button)findViewById(R.id.requestEvalCancelBtn);

            requestManners = (RatingBar)findViewById(R.id.requestManners);
            requestEvalHash = (EditText)findViewById(R.id.requestEvalHash);
            resonseEvalOkBtn.setOnClickListener(this);
            responseEvalCancelBtn.setOnClickListener(this);
            requestEvalName.setText(name);
            Glide.with(this).load(imgPath).bitmapTransform(new CropCircleTransformation(this)).into(requestImg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.requestEvalOkBtn:
                Map<String, String> params = new HashMap<>();
                params.put("requestManners", ((int)requestManners.getRating()) + "");
                params.put("errandsNum", errandsNum);
                params.put("isRequest", isRequest +"");
                params.put("hashContext", requestEvalHash.getText() + "");
                EvalFinishNetworkTask evalFinishNetworkTask = new EvalFinishNetworkTask(this);
                evalFinishNetworkTask.execute(params);
                break;
            case R.id.responseEvalOkBtn:
                Map<String, String> responseParams = new HashMap<>();
                responseParams.put("responseKindness", ((int)responseKindness.getRating()) + "");
                responseParams.put("responseSpeed", ((int)responseSpeed.getRating()) + "");
                responseParams.put("responseAccuracy", ((int)responseAccuracy.getRating()) + "");
                responseParams.put("errandsNum", errandsNum);
                responseParams.put("isRequest", isRequest +"");
                responseParams.put("hashContext", responseEvalHash.getText() +"");
                EvalFinishNetworkTask responseEvalFinishNetworkTask = new EvalFinishNetworkTask(this);
                responseEvalFinishNetworkTask.execute(responseParams);
                break;
            case R.id.requestEvalCancelBtn:
            case R.id.responseEvalCancelBtn:
                finish();
                break;
        }
    }
}
