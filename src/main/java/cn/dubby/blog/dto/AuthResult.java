package cn.dubby.blog.dto;

/**
 * Created by yangzheng03 on 2018/1/3.
 */
public class AuthResult {

    public AuthResult(String authCode, String captchaURL) {
        this.authCode = authCode;
        this.captchaURL = captchaURL;
    }

    public AuthResult() {
    }

    private String authCode;

    private String captchaURL;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getCaptchaURL() {
        return captchaURL;
    }

    public void setCaptchaURL(String captchaURL) {
        this.captchaURL = captchaURL;
    }
}
