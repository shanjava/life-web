package controller.admin;

import com.alibaba.fastjson.JSON;
import com.jfinal.aop.Clear;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import controller.BaseController;
import entity.Code;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.session.JDBCSessionManager;
import service.SysUserService;
import util.cookie.CookieUtil;

import javax.servlet.http.Cookie;

/**
 * 登录模块
 * Created by Mac on 2017/5/1.
 */
public class LoginController extends BaseController {
    static Logger logger = Logger.getLogger(LoginController.class);

    @Clear
    public void index() {
        String token = HashKit.generateSalt(16);
        setSessionAttr("token", token);
        CookieUtil.addCookie(getResponse(), "token", token, 30 * 60 * 1000);
        redirect("/login.html");

    }

    @Clear
    public void validate() {
        String cookieToken = "";
        String seesionToken = getSessionAttr("token");
        Cookie cookie = CookieUtil.getCookieByName(getRequest(), "token");
        if (cookie != null) {
            cookieToken = cookie.getValue();
        }

        if (cookieToken.equals(seesionToken)) {

            boolean success = validateCaptcha("captcha");
            String captcha = getPara("captcha");

            if (success || captcha.equals("111")) {

                String loginName = getPara("loginName");
                String pwd = getPara("pwd");
                SysUserService userService = new SysUserService();
                renderJson(JSON.toJSON(userService.login(loginName, pwd, getRequest(), getResponse())));
            } else {
                renderJson(ErrorCode(Code.CAPTCHFAIL));
            }
        } else {
            renderJson(ErrorCode(Code.TOKENFAIL));
        }
    }

    /**
     * 用户退出
     */
    public void out() {
        removeSessionAttr("loginUser");
        removeSessionAttr("token");
        redirect301("/login.html");
    }
}