package webRoutes;

import com.jfinal.config.Routes;
import controller.front.FrontController;

/**
 * 网站前端路由
 * Created by Mac on 2017/5/1.
 */
public class FrontRoutes extends Routes {
    public void config() {

        this.add("/text", FrontController.class);
    }
}
