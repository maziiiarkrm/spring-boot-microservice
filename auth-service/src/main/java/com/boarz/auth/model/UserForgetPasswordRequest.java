package com.boarz.auth.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserForgetPasswordRequest {

  @NotBlank
  @NotEmpty
  private String username;

  public UserForgetPasswordRequest() {
  }

  public UserForgetPasswordRequest(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
