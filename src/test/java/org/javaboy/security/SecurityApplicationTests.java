package org.javaboy.security;

import org.javaboy.security.mapper.MenuMapper;
import org.javaboy.security.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityApplicationTests {

    @Autowired
    MenuService menuService;
    @Test
    void contextLoads() {
        System.out.println(menuService.getAllMenus());
    }

}
