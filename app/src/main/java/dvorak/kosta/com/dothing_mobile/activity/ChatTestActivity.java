package dvorak.kosta.com.dothing_mobile.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.HttpClient;
import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.ChatViewAdapter;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.network.ChatMapNetworkTask;
import dvorak.kosta.com.dothing_mobile.network.EvalNetworkTask;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

public class ChatTestActivity extends AppCompatActivity {
    Toolbar chatTool;
    ChatViewAdapter chatViewAdapter;
    ListView listView;
    private String path = "C:\\dothing_chat";//저장 경로
    BufferedWriter fileBw = null;
    BufferedReader fileBr, br = null;
    Socket sk;
    PrintWriter pw;
    String urlPath1, urlPath2, errandsNum;
    String meImg, youImg, you, youName;
    EditText inputChat;
    ImageView sendImg;
    boolean isRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_test);
        chatTool = (Toolbar) findViewById(R.id.chatTool);
        listView = (ListView) findViewById(R.id.listView);
        inputChat = (EditText) findViewById(R.id.inputChat);
        sendImg = (ImageView) findViewById(R.id.sendImg);
        chatViewAdapter = new ChatViewAdapter(this);
        listView.setAdapter(chatViewAdapter);
        Map<String, String> params = new HashMap<>();
        Intent intent = getIntent();
        errandsNum = intent.getExtras().getString("errandsNum");
        you = intent.getExtras().getString("you");
        isRequest = intent.getExtras().getBoolean("isRequest");
        params.put("errandsNum", errandsNum);
        urlPath1 = intent.getExtras().getString("userImgPath");
        urlPath2 = intent.getExtras().getString("userImgPath2");
        youName = intent.getExtras().getString("youName");
        chatTool.setTitle(youName + "님과의 대화");
        setSupportActionBar(chatTool);
        if (urlPath1.equals(MemberInfo.selfImgUrlPath)) {
            meImg = urlPath1;
            youImg = urlPath2;
        } else {
            meImg = urlPath2;
            youImg = urlPath1;
        }
        NetworkTask networkTask = new NetworkTask();
        networkTask.execute(params);

        sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = System.currentTimeMillis();
                SimpleDateFormat dayTime = new SimpleDateFormat("MM/dd hh시 mm분");

                String str = dayTime.format(new Date(time));
                String message = MemberInfo.userId + ":" + inputChat.getText() + ":" + str + ":" + you + ":" + meImg.substring(meImg.lastIndexOf("/") + 1, meImg.length()); // 보내는사람(나) : 채팅내용 : 시간 : 상대방
                Log.e("보내는 메세지", ":" + message);
                pw.println(message);
                inputChat.setText(null);

            }
        });
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sk = new Socket("192.168.0.3", 8888);
                    pw = new PrintWriter(new OutputStreamWriter(sk.getOutputStream(), "UTF-8"), true);
                    br = new BufferedReader(
                            new InputStreamReader(sk.getInputStream(), "UTF-8"));
                    pw.println(MemberInfo.userId + ":" + errandsNum);//초기 내 아이디 + 방번호 전송
                    //받는스레드
                    new Thread() {
                        public void run() {
                            try {
                                String data = "";
                                while ((data = br.readLine()) != null) {
                                    Log.e("받은 데이터: ", data);
                                    final String dataArr[] = data.split(":");
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String imgPath = "";
                                            if (dataArr[0].trim().equals(MemberInfo.userId)) {
                                                chatViewAdapter.addItemRight(dataArr[0], dataArr[1], meImg, dataArr[2]);
                                            } else {
                                                chatViewAdapter.addItemLeft(dataArr[0], dataArr[1], youImg, dataArr[2]);
                                            }
                                            chatViewAdapter.notifyDataSetChanged();
                                            listView.setSelection(chatViewAdapter.getCount() - 1);
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        ;
                    }.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();

    }

    public class NetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
        @Override
        protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

            HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/loadChat");
            http.addAllParameters(maps[0]);
            HttpClient post = http.create();
            post.request();

            int statusCode = post.getHttpStatusCode();
            String body = post.getBody();

            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            chatViewAdapter.removeItem();
            Log.d("JSON_RESULT", s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String[] arr = jsonArray.getString(i).split("#/separator/#");

                    String[] restArr = arr[1].split("/#separator#/");
//                    restArr[0] = restArr[0].replace("\n", "");

                    if (restArr[0].equals("")) {
                        restArr[0] = " ";
                    }
                    if (arr[0].trim().equals(MemberInfo.userId)) {
                        chatViewAdapter.addItemRight(arr[0], restArr[0], meImg, restArr[1]);
                    } else {
                        chatViewAdapter.addItemLeft(arr[0], restArr[0], youImg, restArr[1]);
                    }

                }
                listView.setSelection(chatViewAdapter.getCount() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        pw.println("EXIT:" + MemberInfo.userId);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_location:
                ChatMapNetworkTask chatMapNetworkTask = new ChatMapNetworkTask(this);
                Map<String, String> params = new HashMap<>();
                params.put("errandsNum", errandsNum);
                params.put("id", you);
                chatMapNetworkTask.execute(params);
                break;
            case R.id.action_eval:
                EvalNetworkTask evalNetworkTask = new EvalNetworkTask(this, isRequest, youImg, youName, errandsNum, you);
                Map<String, String> evalParams = new HashMap<>();
                evalParams.put("errandsNum", errandsNum);
                evalParams.put("isRequest", isRequest + "");
                evalNetworkTask.execute(evalParams);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
