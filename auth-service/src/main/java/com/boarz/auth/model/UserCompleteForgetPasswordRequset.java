package com.boarz.auth.model;

public class UserCompleteForgetPasswordRequset {

  private String username;
  private String password;

  public UserCompleteForgetPasswordRequset() {
  }

  public UserCompleteForgetPasswordRequset(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
