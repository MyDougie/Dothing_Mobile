package dvorak.kosta.com.dothing_mobile.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.ListViewAdapter;
import dvorak.kosta.com.dothing_mobile.item.ErrandsItem;
import dvorak.kosta.com.dothing_mobile.network.ErrandListSearchNetworkTask;

import static dvorak.kosta.com.dothing_mobile.R.id.listBackBtn;

/**
 * Created by YTK on 2017-07-25.
 */

public class ErrandsListActivity extends AppCompatActivity implements View.OnClickListener{
    ErrandListSearchNetworkTask errandListSearchNetworkTask;
    ListViewAdapter listViewAdapter;
    ListView listView;
    public static ProgressBar progressBar;
    EditText keywordInput;
    ImageView magnifier, sortImage, listBack;
    String sort = "1";
    int selection = 0, preSelection;
    FloatingActionButton writeBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_errand_list);
        progressBar = (ProgressBar)findViewById(R.id.listProgess);
        writeBtn = (FloatingActionButton)findViewById(R.id.writeListBtn);
        magnifier = (ImageView)findViewById(R.id.magnifierBtn);
        sortImage = (ImageView)findViewById(R.id.sortBtn);
        listBack = (ImageView)findViewById(listBackBtn);
        keywordInput = (EditText)findViewById(R.id.inputKeyword);
        listViewAdapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.errandListBogi);
        listView.setAdapter(listViewAdapter);


        errandListSearchNetworkTask = new ErrandListSearchNetworkTask(listViewAdapter);

        Map<String ,String> params = new HashMap<>();
        params.put("sort", sort);

        progressBar.setVisibility(View.VISIBLE);
        errandListSearchNetworkTask.execute(params);

        magnifier.setOnClickListener(this);
        sortImage.setOnClickListener(this);
        listBack.setOnClickListener(this);
        writeBtn.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                view.setSelected(true);
                ErrandsItem item = (ErrandsItem)parent.getItemAtPosition(position);


                    Intent intent = new Intent(getApplicationContext(), DetailViewActivity.class);
                    intent.putExtra("errandNum",item.getErrandNum()+"");
                    startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.magnifierBtn:
                progressBar.bringToFront();
                progressBar.setVisibility(View.VISIBLE);
                errandListSearchNetworkTask = new ErrandListSearchNetworkTask(listViewAdapter);
                Map<String ,String> params = new HashMap<>();
                params.put("sort", sort);
                String keyword = keywordInput.getText() + "";
                if(keyword.trim().length() > 0) {
                    params.put("keyword", keywordInput.getText() + "");
                }
                errandListSearchNetworkTask.execute(params);
                break;
            case R.id.sortBtn:
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("정렬 설정");
                final String preSort = sort;
                preSelection = selection;
                String items[] = {"날짜순", "높은가격순", "낮은가격순"};
                ab.setSingleChoiceItems(items, selection,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                switch(whichButton){
                                    case 0: sort = "1";break;
                                    case 1: sort = "2";break;
                                    case 2: sort = "3";break;
                                }
                                selection = whichButton;
                            }
                        }).setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                selection = preSelection;
                                sort = preSort;
                                dialog.dismiss();
                            }
                        });
                ab.show();
                break;
            case R.id.action_errandList:
                startActivity(new Intent(this, ErrandsListActivity.class));
                break;

            case R.id.listBackBtn:
                onBackPressed();
                break;

            case R.id.writeListBtn:
                Intent intent = new Intent(this, ErrandRegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
