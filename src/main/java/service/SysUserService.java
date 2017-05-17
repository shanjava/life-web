package service;

import com.jfinal.kit.HashKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.redis.Cache;
import entity.Code;
import entity.JsonEntity;
import model.SysUser;
import com.jfinal.plugin.redis.Redis;


/**
 * Created by Mac on 2017/5/16.
 */
public class SysUserService extends BaseService {


    public JsonEntity login(String loginName, String pwd) {

        SysUser user= SysUser.dao.findFirstByCache("userCache","user"+loginName,"select userNo from sys_user where " +
                "loginName =? and password = ? ",loginName,pwd);
        if(user== null){
            ErrorCode(Code.LOGINFAIL);
        }else {
            String token ="" ;
            Cache userCache= Redis.use();
            String userToken = userCache.get(user.getUserNo());
            json.setResultCode("0");
            json.setMessage("登录成功");
            json.setData(user);

            if (userToken == null) {
                token = HashKit.generateSalt(16);
                userCache.set(user.getUserNo(),token);
                json.setExt(token);
            } else {

                json.setExt(token);
            }
            return json;



        }


    return json;

    }
}
