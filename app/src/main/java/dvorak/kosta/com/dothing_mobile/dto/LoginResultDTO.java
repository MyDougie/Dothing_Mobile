package dvorak.kosta.com.dothing_mobile.dto;

/**
 * Created by Administrator on 2017-07-10.
 * @brief : 로그인 결과 데이터를 저장 및 전달에 사용하는 Class
 */

public class LoginResultDTO {
    private String result;

    /**
     * @brief : 로그인 결과 값을 가져오는 메소드
     * @return : String
     */
    public String getResult() {
        return result;
    }

    /**
     * @brief : 로그인 결과를 저장해주는 메소드
     * @param : String result
     */
    public void setResult(String result) {
        this.result = result;
    }
}
