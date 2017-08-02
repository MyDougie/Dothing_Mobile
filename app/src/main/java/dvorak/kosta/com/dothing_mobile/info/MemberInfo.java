package dvorak.kosta.com.dothing_mobile.info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YTK on 2017-07-16.
 * 로그인 한 User의 정보를 저장한 class
 */

public class MemberInfo {
    public static String userId;
    public static String name;
    public static String password;
    public static String sex;
    public static String preAddr;
    public static String detailAddr;
    public static int auth;
    public static String selfImgUrlPath;
    public static String ssnImg;
    public static String joinDate;
    public static String introduce;
    public static String latitude;
    public static String longitude;
    public static String currentPoint;
    public static int averageGPA;
    public static List<Integer> responseAccuracy = new ArrayList<>();
    public static List<Integer> responseSpeed = new ArrayList<>();
    public static List<Integer> responseKindness = new ArrayList<>();
    public static List<Integer> responseManners = new ArrayList<>();
    public static List<String> hashList = new ArrayList<>();

}
