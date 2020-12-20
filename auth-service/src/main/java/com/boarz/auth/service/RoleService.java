package com.boarz.auth.service;

import com.boarz.auth.entity.Role;

import java.util.List;

public interface RoleService {


    Role add(String name, String description, String token);

    Role update(long id, String name, String description, String token);

    Role findOne(long id, String token);

    List<Role> findAll(String token);

    boolean delete(long id, String token);

}
