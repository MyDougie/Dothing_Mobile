package dvorak.kosta.com.dothing_mobile.item;

import android.graphics.drawable.Drawable;

/**
 * Created by YTK on 2017-07-12.
 */

public class ErrandsItem {
    private Drawable errandImg;
    private String errandTitle;
    private String errandContent;
    private String productPrice;
    private String errandPrice;
    private String addr;
    private String latitude;
    private String longitude;
    private int click;

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



    public Drawable getErrandImg() {
        return errandImg;
    }

    public void setErrandImg(Drawable errandImg) {
        this.errandImg = errandImg;
    }

    public String getErrandTitle() {
        return errandTitle;
    }

    public void setErrandTitle(String errandTitle) {
        this.errandTitle = errandTitle;
    }

    public String getErrandContent() {
        return errandContent;
    }

    public void setErrandContent(String errandContent) {
        this.errandContent = errandContent;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
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
