package dvorak.kosta.com.dothing_mobile.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dvorak.kosta.com.dothing_mobile.network.HttpClient;
import dvorak.kosta.com.dothing_mobile.R;
import dvorak.kosta.com.dothing_mobile.adapter.ChatListViewAdapter;
import dvorak.kosta.com.dothing_mobile.info.MemberInfo;
import dvorak.kosta.com.dothing_mobile.item.ChatListItem;
import dvorak.kosta.com.dothing_mobile.util.ConstantUtil;

/**
 * 채팅 리스트를 보여주는 activity
 */
public class ChatListActivity extends AppCompatActivity {
    ListView chatListView;
    ChatListViewAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        chatListView = (ListView)findViewById(R.id.chatListView);
        adapter = new ChatListViewAdapter();
        chatListView.setAdapter(adapter);
        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatListItem item = (ChatListItem) parent.getItemAtPosition(position);
                Intent chatIntent = new Intent(ChatListActivity.this, ChatTestActivity.class);
                chatIntent.putExtra("errandsNum", item.getErradsNum());
                chatIntent.putExtra("you", item.getYou());
                chatIntent.putExtra("youName", item.getChatName());
                chatIntent.putExtra("userImgPath", item.getUserImgPath());
                chatIntent.putExtra("userImgPath2", item.getUserImgPathTwo());
                chatIntent.putExtra("isRequest", item.isRequest());
                startActivity(chatIntent);
            }
        });
    }

    /**
     * 자신의 userId를 서버에 전송하는 역할
     */
    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> params = new HashMap<>();
        params.put("userId", MemberInfo.userId);
        NetworkTask networkTask = new NetworkTask();
        networkTask.execute(params);
    }

    /**
     * parameter를 받아서 서버에 전송해주는 클래스
     */
    public class NetworkTask extends AsyncTask<Map<String, String>, Integer, String> {

        /**
         * 파라미터를 받아서 서버로 전송
         * @param : userId 정보
         * @return : 응답 본문
         */
        @Override
        protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

// Http 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST", ConstantUtil.ipAddr + "androidErrand/selectChatList");

// Parameter 를 전송한다.
            http.addAllParameters(maps[0]);


//Http 요청 전송
            HttpClient post = http.create();
            post.request();

// 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

// 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        /**
         * 서버에서 response를 받아 처리하는 역할
         * @param : 서버에서 보낸 정보
         */
        @Override
        protected void onPostExecute(String s) {
            adapter.removeItem();
            Log.d("JSON_RESULT", s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i=0; i < jsonArray.length(); i++){
                    String name = "";
                    String youId="";
                    boolean isRequest = false;
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONObject user = object.getJSONObject("requestUser");
                    JSONObject user2 = object.getJSONObject("responseUser");
                    if(user.getString("userId").equals(MemberInfo.userId)){
                        name = user2.getString("name");
                        youId = user2.getString("userId");
                        isRequest = true;
                    }else{
                        name = user.getString("name");
                        youId = user.getString("userId");
                    }

                    ChatListItem chatListItem = new ChatListItem();
                    chatListItem.setChatName(name);
                    chatListItem.setChatTitle(object.getString("title"));
                    chatListItem.setErradsNum(object.getString("errandsNum"));
                    chatListItem.setUserImgPath(ConstantUtil.ipAddr + "users/" + user.getString("userId") + "/" + user.getString("selfImg"));
                    chatListItem.setUserImgPathTwo(ConstantUtil.ipAddr + "users/" + user2.getString("userId") + "/" + user2.getString("selfImg"));
                    chatListItem.setYou(youId);
                    chatListItem.setRequest(isRequest);

                    adapter.addItem(chatListItem) ;
                }
                adapter.notifyDataSetChanged();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
