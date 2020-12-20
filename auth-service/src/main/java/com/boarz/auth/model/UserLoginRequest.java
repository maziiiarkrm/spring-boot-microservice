package com.boarz.auth.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserLoginRequest {

  @NotBlank
  @NotEmpty
  private String username;
  @NotBlank
  @NotEmpty
  @Size(min = 8)
  private String password;

  public UserLoginRequest() {
  }

  public UserLoginRequest(String username, String password) {
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
