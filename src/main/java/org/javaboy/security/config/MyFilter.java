package org.javaboy.security.config;

import org.javaboy.security.bean.Menu;
import org.javaboy.security.bean.Role;
import org.javaboy.security.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;


/*
*本类作用：
* 去数据库动态加载权限访问数据
* 另外一个类:根据你需要的角色，
*
* */
@Component
public class MyFilter implements FilterInvocationSecurityMetadataSource {
    /*
    *根据请求的地址，分析出访问该地址需要那些角色
    * */
@Autowired
    MenuService menuService;
    AntPathMatcher antPathMatcher=new AntPathMatcher();

    /**
     *     每次请求都会调用本方法,每次请求的地址与数据库中的规则进行匹配，
     *     算出需要什么角色，匹配上了，返回角色
     */


    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求的地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        List<Menu> allMenus=menuService.getAllMenus();
        for (Menu menu : allMenus) {
            //如果规则(数据库中的规则pattern与请求地址跟哪个能匹配的上)
            if (antPathMatcher.match(menu.getPattern(),requestUrl)){
                //匹配上了，看需要什么角色
                List<Role> roles=menu.getRoles();
                String[] rolesStr=new String[roles.size()];
                for (int i=0;i<roles.size();i++) {
                    rolesStr[i]=roles.get(i).getName();
                }
                return SecurityConfig.createList(rolesStr);
            }
        }
        //如果路径不匹配
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
