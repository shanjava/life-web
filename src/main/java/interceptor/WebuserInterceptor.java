package interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class WebuserInterceptor implements Interceptor {

	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		


//		if(inv.getController().getRequest().getRequestURL().indexOf("findByPostId") != -1){
//			inv.invoke();
//		}else{
//			Controller   controller   =  inv.getController();
//			User user =  controller.getSessionAttr("user");
//			if(user == null){
//				controller.redirect301("/web/front/jch/login.html");  // redirect("/admin/login.html");
//			}else{
//				inv.invoke();
//			}
//		}
	}

}
