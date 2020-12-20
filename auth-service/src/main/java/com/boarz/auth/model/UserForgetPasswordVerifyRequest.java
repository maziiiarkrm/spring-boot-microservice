package com.boarz.auth.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserForgetPasswordVerifyRequest {

  @NotBlank
  @NotEmpty
  private String username;
  @NotBlank
  @NotEmpty
  @Size(min = 6, max = 6)
  private String forgetPasswordCode;
  @NotBlank
  @NotEmpty
  @Size(min = 8)
  private String password;

  public UserForgetPasswordVerifyRequest() {
  }

  public UserForgetPasswordVerifyRequest(String username, String forgetPasswordCode,
      String password) {
    this.username = username;
    this.forgetPasswordCode = forgetPasswordCode;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getForgetPasswordCode() {
    return forgetPasswordCode;
  }

  public String getPassword() {
    return password;
  }
}
