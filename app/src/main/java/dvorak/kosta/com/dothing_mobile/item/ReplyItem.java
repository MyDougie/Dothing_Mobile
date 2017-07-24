package dvorak.kosta.com.dothing_mobile.item;

/**
 * Created by Administrator on 2017-07-24.
 */

public class ReplyItem {
    private int replyNum;
    private ErrandsItem errands;
    private String replyContent;
    private String arrivalTime;
    private String replyDate;
    private Member user;


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
}
