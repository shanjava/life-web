package interceptor;

import model.SysUser;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    private final Logger log = Logger.getLogger(AdminFilter.class);

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain filterChain) throws IOException, ServletException {


        // TODO Auto-generated method stub
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        log.info("SessionId:" + httpRequest.getSession().getId());

        if (httpRequest.getRequestURL().indexOf(".css") > 0) {
            filterChain.doFilter(req, resp);
            return;
        }
        if (httpRequest.getRequestURL().indexOf(".js") > 0) {
            filterChain.doFilter(req, resp);
            return;
        }
        if (httpRequest.getRequestURL().indexOf(".png") > 0) {
            filterChain.doFilter(req, resp);
            return;
        }
        if (httpRequest.getRequestURL().indexOf(".jpg") > 0) {
            filterChain.doFilter(req, resp);
            return;
        }
        if (httpRequest.getRequestURL().indexOf(".gif") > 0) {
            filterChain.doFilter(req, resp);
            return;
        }


        HttpSession httpSession = httpRequest.getSession(false);
        if (httpSession == null) {
            if (httpRequest.getRequestURL().indexOf("login") > 0) {
                filterChain.doFilter(req, resp);
            } else {
                log.info("sendRedirect >>>" + httpRequest.getRequestURL());
                //httpServletResponse.sendRedirect("/admin/login.html");
                httpServletResponse.getWriter().write("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"></head><body>"
                        + "<script>top.location.href  =\"/life-web/login.html\";</script>"
                        + "</body></html>");
                httpServletResponse.getWriter().close();
                //httpServletResponse.reset();
            }
        } else {

            SysUser admin = (SysUser) httpSession.getAttribute("loginUser");
            if (admin == null) {
                if (httpRequest.getRequestURL().indexOf("login") != -1) {
                    filterChain.doFilter(req, resp);
                } else {
                    log.info("sendRedirect >>>" + httpRequest.getRequestURL());
                    httpServletResponse.getWriter().write("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"></head><body>"
                            + "<script>top.location.href  =\"/life-web/login.html\";</script>"
                            + "</body></html>");
                    httpServletResponse.getWriter().close();
                    //httpServletResponse.sendRedirect("/admin/login.html");
                }
            } else {
                filterChain.doFilter(req, resp);
            }
        }


    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
