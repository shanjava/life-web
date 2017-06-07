package interceptor;


import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Mac on 2017/3/28.
 */
public class EncodingFilter implements Filter {

    private String charset;

    public void init(FilterConfig filterConfig) throws ServletException {
        //从web.xml中的filter的配制信息中取得字符集
        this.charset = filterConfig.getInitParameter("charset");
    }



    public void destroy() {
        // TODO Auto-generated method stub
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        //用init方法取得的charset覆盖被拦截下来的request对象的charset
        request.setCharacterEncoding(this.charset);
        //将请求移交给下一下过滤器，如果还有的情况下。
        chain.doFilter(request, response);
    }



}
