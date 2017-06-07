package config;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import controller.admin.UserController;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import interceptor.SessionInterceptor;
import model._MappingKit;
import webRoutes.AdminRoutes;
import webRoutes.FrontRoutes;

/**
 * Created by Mac on 2017/5/1.
 */
public class WebConfig extends JFinalConfig {
    public void configConstant(Constants constants) {
        constants.setDevMode(true);
        constants.setEncoding("utf-8");
        PropKit.use("config.properties");
        constants.setJsonFactory(FastJsonFactory.me());




    }

    public void configRoute(Routes routes) {
        routes.add(new FrontRoutes());

        routes.add(new AdminRoutes());
        routes.add("/usr", UserController.class);



    }

    public void configEngine(Engine engine) {

    }

    public void configPlugin(Plugins plugins) {
        /**配置druid数据连接池插件**/
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
        /**配置druid监控**/
        dp.addFilter(new StatFilter());
        WallFilter wall = new WallFilter();
        wall.setDbType("mysql");
        dp.addFilter(wall);
        plugins.add(dp);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        plugins.add(arp);
        arp.setShowSql(true);
       _MappingKit.mapping(arp);

//最先创建的RedisPlugin 对象所持有的 Cache 对象将成为主缓存对象，主缓存对象可通过 Redis.use()直接获取，否则需要提供 cacheName 参数才能获取，例如：Redis.use(“other”)
        RedisPlugin redis= new RedisPlugin("userCache","localhost");plugins.add(redis);

      //   添加cache 配置
        plugins.add(new EhCachePlugin());

    }

    public void configInterceptor(Interceptors interceptors) {
        interceptors.add(new SessionInterceptor());

    }

    public void configHandler(Handlers handlers) {

    }

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 2222, "/");
    }
}
