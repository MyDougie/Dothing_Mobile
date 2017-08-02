package dvorak.kosta.com.dothing_mobile.item;

/**
 * Created by YTK on 2017-07-12.
 * @brief : 채팅 리스트에 필요한 데이터를 저장 및 전달 하는 Class
 */

public class ChatListItem {
    private String chatTitle;
    private String chatName;
    private String you;
    private String erradsNum;
    private String userImgPath;
    private String userImgPathTwo;
    private boolean isRequest;


    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public String getYou() {
        return you;
    }

    public void setYou(String you) {
        this.you = you;
    }

    public String getUserImgPath() {
        return userImgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    public String getUserImgPathTwo() {
        return userImgPathTwo;
    }

    public void setUserImgPathTwo(String userImgPathTwo) {
        this.userImgPathTwo = userImgPathTwo;
    }

    public String getErradsNum() {
        return erradsNum;
    }

    public void setErradsNum(String erradsNum) {
        this.erradsNum = erradsNum;
    }

    public String getChatTitle() {
        return chatTitle;
    }

    public void setChatTitle(String chatTitle) {
        this.chatTitle = chatTitle;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }
}
