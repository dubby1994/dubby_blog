package cn.dubby.blog.dto;

import java.util.Map;

/**
 * Created by teeyoung on 17/12/12.
 */
public class ModifyResponse {

    public static final int SUCCESS = 0;
    public static final int UN_LOGIN = 1;
    public static final int NOT_FOUND = 2;
    public static final int SYSTEM_ERROR = 3;

    private int code;

    private String message;

    private Map data;

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModifyResponse() {
    }

    public ModifyResponse(int code) {
        this.code = code;
        switch (code) {
            case SUCCESS:
                this.message = "成功";
                break;
            case UN_LOGIN:
                this.message = "没有登录";
                break;
            case NOT_FOUND:
                this.message = "没有找到对应的博客";
                break;
            case SYSTEM_ERROR:
                this.message = "系统错误";
                break;
            default:
                this.message = "未知错误";
                break;
        }
    }

    public ModifyResponse(int code, Map data) {
        this(code);
        this.data = data;
    }

    public static final ModifyResponse MODIFY_SUCCESS = new ModifyResponse(SUCCESS);
    public static final ModifyResponse MODIFY_UN_LOGIN = new ModifyResponse(UN_LOGIN);
    public static final ModifyResponse MODIFY_NOT_FOUND = new ModifyResponse(NOT_FOUND);
    public static final ModifyResponse MODIFY_SYSTEM_ERROR = new ModifyResponse(SYSTEM_ERROR);
}
