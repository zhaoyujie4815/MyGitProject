package com.zhaoyujie.shiro;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: ShiroConfig
 * @Description: Shiro 配置类
 * @author zhaoyujie
 * @date 2020-06-20 20:09:26
 */
@Configuration
public class ShiroConfig {

    /**
     * @MethodName: shiroFilterChainDefinition
     * @Description: TODO
     * @author zhaoyujie
     * @return ShiroFilterChainDefinition
     * @date 2020-06-20 20:09:41
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
//        definition.addPathDefinition("/login", "anon");
//        definition.addPathDefinition("/**", "authc");
        return definition;
    }

    /**
     * @MethodName: securityManager
     * @Description: 配置核心安全事务管理器
     * @author zhaoyujie
     * @param myRealm
     * @return DefaultWebSecurityManager
     * @date 2020-06-20 20:09:45
     */
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("myRealm") MyRealm myRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 关联 Realm
        manager.setRealm(myRealm);
        return manager;
    }

    /**
     * @MethodName: getMyRealm
     * @Description: 配置自定义的权限登录器
     * @author zhaoyujie
     * @return MyRealm
     * @date 2020-06-20 20:09:54
     */
    @Bean(name = "myRealm")
    public MyRealm getMyRealm() {
        return new MyRealm();
    }

}
