package dvorak.kosta.com.dothing_mobile.item;

/**
 * Created by Administrator on 2017-07-24.
 * @brief :  멤버에 필요한 데이터를 저장 및 전달 하는 Class
 */

public class Member {
    private String id;
    private String name;
    private String userImgPath;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserImgPath() {
        return userImgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }


}
