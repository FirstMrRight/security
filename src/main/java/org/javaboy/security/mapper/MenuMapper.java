package org.javaboy.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.javaboy.security.bean.Menu;

import java.util.List;


public interface MenuMapper {
    public List<Menu> getAllMenus();
}
