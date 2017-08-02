package dvorak.kosta.com.dothing_mobile.item;

/**
 * Created by YTK on 2017-07-21.
 * @brief : 채팅에 필요한 데이터를 저장 및 전달을 하는 Class
 */

public class ChatItem {
    private int type;
    private String userId;
    private String userImgPath;
    private String chat;
    private String date;

    /**
     * @brief : 데이터를 String으로 return 해주는 메소드
     * @return : String
     */
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

    /**
     * @brief : type 값을 가져오는 메소드
     * @return : int
     */
    public int getType() {
        return type;
    }

    /**
     * @brief : type를 저장해주는 메소드
     * @param : int type 보낸 사람과 자신의 메세지 레이아웃 구별 변수
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @brief : userId 값을 가져오는 메소드
     * @return : String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @brief : userId 를 저장해주는 메소드
     * @param : String userId userId를 저장하는 변수
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @brief : inmgPath 값을 가져오는 메소드
     * @return : String
     */
    public String getUserImgPath() {
        return userImgPath;
    }

    /**
     * @brief : imgPath를 저장해주는 메소드
     * @param : String imgPath User의 프로필 사진 경로를 저장하는 변수
     */
    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    /**
     * @brief : chat 값을 가져오는 메소드
     * @return : String
     */
    public String getChat() {
        return chat;
    }

    /**
     * @brief : chat를 저장해주는 메소드
     * @param : String chat 채팅 내용을 저장하는 변수
     */
    public void setChat(String chat) {
        this.chat = chat;
    }

    /**
     * @brief : date 값을 가져오는 메소드
     * @return : String
     */
    public String getDate() {
        return date;
    }

    /**
     * @brief : 로그인 결과를 저장해주는 메소드
     * @param : String date 메세지를 보낸 시간과 날짜를 저장하는 변수
     */
    public void setDate(String date) {
        this.date = date;
    }
}
