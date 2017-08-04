package dvorak.kosta.com.dothing_mobile.item;

/**
 * Created by YTK on 2017-07-21.
 * 채팅에 필요한 데이터를 저장 및 전달을 하는 Class
 */

public class ChatItem {
    private int type;
    private String userId;
    private String userImgPath;
    private String chat;
    private String date;

    @Override
    public String toString() {
        return "ChatItem{" +
                "type=" + type +
                ", userId='" + userId + '\'' +
                ", userImgPath='" + userImgPath + '\'' +
                ", chat='" + chat + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImgPath() {
        return userImgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
