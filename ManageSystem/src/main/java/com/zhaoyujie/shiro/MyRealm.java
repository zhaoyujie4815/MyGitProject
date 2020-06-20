package com.zhaoyujie.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 
 * @ClassName: MyRealm
 * @Description: 自定义 Realm
 * @author zhaoyujie
 * @date 2020-06-20 20:06:48
 */
public class MyRealm extends AuthorizingRealm {

    /**
     * @MethodName: doGetAuthorizationInfo
     * @Description: 执行授权逻辑
     * @author zhaoyujie
     * @param principals
     * @return
     * @date 2020-06-20 20:08:08
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取登录账户
        String account = (String) principals.getPrimaryPrincipal();

        return null;
    }

    /**
     * @MethodName: doGetAuthenticationInfo
     * @Description: 执行认证逻辑
     * @author zhaoyujie
     * @param token
     * @return
     * @throws AuthenticationException
     * @date 2020-06-20 20:08:26
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        return null;
    }

}
