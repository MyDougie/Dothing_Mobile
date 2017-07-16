package dvorak.kosta.com.dothing_mobile.item;

/**
 * Created by YTK on 2017-07-12.
 */

public class ErrandsItem {
    private String errandTitle;
    private String errandPrice;
    private String addr;
    private String latitude;
    private String longitude;
    private String errandTime;
    private int click;

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
