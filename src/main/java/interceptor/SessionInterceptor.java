package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import controller.BaseController;
import entity.Code;
import model.SysUser;
import util.HttpRequest;
import util.cookie.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Mac on 2017/2/24.
 */
public class SessionInterceptor extends BaseController implements Interceptor {

    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        SysUser admin = controller.getSessionAttr("loginUser");
        HttpServletRequest request = controller.getRequest();
        HttpServletResponse response = controller.getResponse();
        HttpSession session = request.getSession();

        String model = request.getMethod();
        String SeesionToken = controller.getSessionAttr("token");
        System.out.println("SeesionToken = " + SeesionToken);
        System.out.println("model = " + model);
        System.out.println("sessionID-------------" + request.getSession().getId());
        if (request.getSession().isNew()) {
            System.out.println("new");

        }
        String cookieToken = "";
        Cookie cookie = CookieUtil.getCookieByName(request, "token");
        if (cookie != null) {
            cookieToken = cookie.getValue();

        }
        System.out.println("cookieToken = " + cookieToken);
        if (admin != null && StrKit.notBlank(admin.getLoginName()) && cookieToken.equals(SeesionToken)) {

            inv.invoke();

            session.removeAttribute("token");
            CookieUtil.delCookie(request, response, "token");
            String token = HashKit.generateSalt(16);

            session.setAttribute("token", token);
            CookieUtil.addCookie(response, "token", token, 30 * 60 * 1000);

        } else {
            System.out.println("inv = [" + inv + "]" + admin);
//
            controller.renderJson(ErrorCode(Code.SESSIONFAIL));

        }

    }

}

