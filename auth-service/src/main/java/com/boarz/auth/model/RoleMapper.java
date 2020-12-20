package com.boarz.auth.model;

import com.boarz.auth.entity.Role;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleMapper {

  public static RoleResponse convert(Role role) {
    if (role == null){
      return null;
    }
    return new RoleResponse(role.getId(), role.getName());
  }

  public static List<RoleResponse> convertAll(List<Role> roles) {

    List<RoleResponse> responses = new ArrayList<>();
    if (roles == null){
      return responses;
    }
    for (Role role : roles) {
      responses.add(convert(role));
    }
    return responses;
  }
}
