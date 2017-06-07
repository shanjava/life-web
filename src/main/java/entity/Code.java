package entity;

/**
 * Created by Mac on 2017/5/16.
 */
public enum Code {
//通用

    SUCCESS("0", "获取成功"),
    FAIL("1", "获取信息失败"),

//    用户   100-199
    LOGINSUCCESS("100","登录成功"),
    LOGINFAIL("102", "账号密码错误"),
    CAPTCHFAIL("103", "验证码错误"),
    TOKENFAIL("104","token验证失败"),



    SESSIONFAIL("1001","/login.html"),

//  视频 200-299
    PULLSUCCESS("200",""),
    PUSHSUCCESS("",""),
    PULLFAIL("",""),
    PUSHFAIL("","")

    ;




    private String code;
    private String message;


    private Code(String code, String message) {
        this.code = code;
        this.message = message;


    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
