package org.javaboy.security.mapper;

import org.javaboy.security.bean.Role;
import org.javaboy.security.bean.User;

import java.util.List;

public interface UserMapper {
    User loadUserByUsername(String s);

    List<Role> getRolesById(Integer id);
}
