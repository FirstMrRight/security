package org.javaboy.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * 获取当前请求对象的信息
     * @param authentication  当前登录用户有哪些角色
     * @param o FilterInvocation对象，相当于MyFilter里面的Object
     * @param collection  MyFilter的返回值,保存了当前请求地址需要的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute attribute:collection){
            //如果需要的角色是login,刚刚的地址没匹配上，是否登录?
            if ("ROLE_login".equals(attribute.getAttribute())){
                //判断是否为匿名用户，是，没登录
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("非法请求");
                }else {
                    //已登录
                    return;
                }
            }

            //当前用户已具备请求所需要的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(attribute.getAttribute())){
                    return;
                }
            }
        }
        //不满足以上两种判断
        throw new AccessDeniedException("非法请求");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
