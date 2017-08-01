package dvorak.kosta.com.dothing_mobile.item;

/**
 * Created by YTK on 2017-07-12.
 * @brief : 심부름에 필요한 데이터를 저장 및 전달하는 Class
 */

public class ErrandsItem {
    private int errandNum;
    private String errandTitle;
    private String errandPrice;
    private String addr;
    private String latitude;
    private String longitude;
    private String errandTime;
    private String replyNum;
    private String state;
    private int click;
    private String requesterId;

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }




    public String getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(String replyNum) {
        this.replyNum = replyNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getErrandNum() {
        return errandNum;
    }

    public void setErrandNum(int errandNum) {
        this.errandNum = errandNum;
    }

    public String getErrandTime() {
        return errandTime;
    }

    public void setErrandTime(String errandTime) {
        this.errandTime = errandTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }



    public String getErrandTitle() {
        return errandTitle;
    }

    public void setErrandTitle(String errandTitle) {
        this.errandTitle = errandTitle;
    }


    public String getErrandPrice() {
        return errandPrice;
    }

    public void setErrandPrice(String errandPrice) {
        this.errandPrice = errandPrice;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }


}
