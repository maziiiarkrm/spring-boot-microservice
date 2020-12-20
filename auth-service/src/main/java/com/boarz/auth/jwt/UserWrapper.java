package com.boarz.auth.jwt;


import com.boarz.auth.entity.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserWrapper {

  static final String USERNAME = "username";
  static final String ROLES = "roles";
  private String username;
  private List<RoleWrapper> roles;

  public UserWrapper(String username, List<Role> roles) {
    this.roles = new ArrayList<>();
    this.username = username;
    setRoles(roles);
  }

  public UserWrapper(Map<String, Object> token) {
    setMap(token);
  }

  public static String getUSERNAME() {
    return USERNAME;
  }

  public static String getROLES() {
    return ROLES;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<RoleWrapper> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    for (com.boarz.auth.entity.Role role : roles) {
      this.roles.add(new RoleWrapper(role.getId(), role.getName()));
    }
  }

  public UserWrapper setMap(Map<String, Object> map) {
    roles = (List<RoleWrapper>) map.get(ROLES);
    username = (String) map.get(USERNAME);
    return this;
  }


}
