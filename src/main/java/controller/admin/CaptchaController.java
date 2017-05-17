package controller.admin;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

public class CaptchaController extends Controller {
    public void index(){
        renderJsp("index.jsp");
    }
    /**
     * 提供生成验证码的图片流
     */
    public void img(){

        renderCaptcha();
    }
    /**
     * 验证
     */
    public void validate(){
        String captcha=getPara("captcha");
        if(StrKit.isBlank(captcha)){
            setAttr("msg", "验证码不能为空");
        }else{
            boolean success=validateCaptcha("captcha");
            setAttr("msg", success?"验证成功":"验证码输入有误");
        }

        renderJson();
    }}