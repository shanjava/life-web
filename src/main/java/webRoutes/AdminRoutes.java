package webRoutes;

import com.jfinal.config.Routes;
import controller.admin.CaptchaController;
import controller.admin.LoginController;
import  controller.admin.TextController;

/**
 * 后台路由
 * Created by Mac on 2017/5/1.
 */
public class AdminRoutes extends Routes {
    public void config() {
       // this.setBaseViewPath("/WEB-INF");
        this.add("/", TextController.class);
        this.add("/captcha", CaptchaController.class);
        this.add("/login", LoginController.class);
//        this.add("/usr", UsrController.class);

    }
}
