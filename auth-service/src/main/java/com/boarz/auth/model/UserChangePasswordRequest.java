package com.boarz.auth.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserChangePasswordRequest {

  @NotBlank
  @NotEmpty
  private String username;
  @NotBlank
  @NotEmpty
  @Size(min = 8)
  private String lastPassword;
  @NotBlank
  @NotEmpty
  @Size(min = 8)
  private String newPassword;

  public UserChangePasswordRequest() {
  }

  public UserChangePasswordRequest(String username, String lastPassword, String newPassword) {
    this.username = username;
    this.lastPassword = lastPassword;
    this.newPassword = newPassword;
  }

  public String getUsername() {
    return username;
  }

  public String getLastPassword() {
    return lastPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }
}
