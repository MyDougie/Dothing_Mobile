package dvorak.kosta.com.dothing_mobile.item;

/**
 * Created by Administrator on 2017-07-24.
 * Reply에 필요한 데이터를 저장하고 전달하는 Class
 */

public class ReplyItem {
    private int replyNum;
    private ErrandsItem errands;
    private String replyContent;
    private String arrivalTime;
    private String replyDate;
    private int responserAvgRating;
    private Member user;
    private int click;



    @Override
    public String toString() {
        return "ErrandsReplyDTO [replyNum=" + replyNum + ", errands=" + errands + ", replyContent=" + replyContent
                + ", arrivalTime=" + arrivalTime + ", replyDate=" + replyDate + ", user=" + user + "]";
    }
    public int getReplyNum() {
        return replyNum;
    }
    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }
    public ErrandsItem getErrands() {
        return errands;
    }
    public void setErrands(ErrandsItem errands) {
        this.errands = errands;
    }
    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public String getReplyDate() {
        return replyDate;
    }
    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }
    public Member getUser() {
        return user;
    }
    public void setUser(Member user) {
        this.user = user;
    }
    public int getResponserAvgRating() {
        return responserAvgRating;
    }
    public void setResponserAvgRating(int responserAvgRating) { this.responserAvgRating = responserAvgRating; }
    public int getClick() { return click; }
    public void setClick(int click) { this.click = click;  }
}
