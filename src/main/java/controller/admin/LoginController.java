package controller.admin;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Controller;
import com.jfinal.json.Json;
import controller.baseController;
import entity.Code;
import model.SysUser;
import service.SysUserService;

import java.util.List;

/**
 * 登录模块
 * Created by Mac on 2017/5/1.
 */
public class LoginController extends baseController {
    public void index() {

        render("login.html");

    }

    public void logvalidatein() {
        boolean success = validateCaptcha("captcha");
        int code = 1;
        if (success) {

            String loginName = getPara("loginName");
            String pwd = getPara("pwd");
            SysUserService userService = new SysUserService();

            renderJson(JSON.toJSON(userService.login(loginName,pwd)));


        }else {

            renderJson(ErrorCode(Code.CAPTCHFAIL));

        }

    }
}