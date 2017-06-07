package service;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.redis.Cache;
import entity.Code;
import entity.JsonEntity;
import model.SysUser;
import com.jfinal.plugin.redis.Redis;
import util.cookie.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Created by Mac on 2017/5/16.
 */
public class SysUserService extends BaseService {


    public JsonEntity login(String loginName, String pwd, HttpServletRequest request, HttpServletResponse response) {

        SysUser user= SysUser.dao.findFirstByCache("userCache","user"+loginName,"select * from sys_user where " +
                "loginName =? and password = ? ",loginName,pwd);
        if(user== null){
            ErrorCode(Code.LOGINFAIL);
        }else {
            Cache userCache= Redis.use();
            String token = userCache.get(user.getUserNo());
            json.setResultCode("0");
            json.setMessage("登录成功");
            json.setData(user);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser",user );

            if (StrKit.isBlank(token)) {
                token = HashKit.generateSalt(16);
                userCache.set(user.getUserNo(),token);
                json.setExt(token);

            } else {
                json.setExt(token);
            }
            System.out.println("token = " + token);
            session.setAttribute("token",token);
            CookieUtil.addCookie(response,"token",token,30*60*1000);
            return json;



        }


    return json;

    }
}
