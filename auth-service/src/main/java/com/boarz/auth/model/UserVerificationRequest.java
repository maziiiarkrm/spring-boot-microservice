package com.boarz.auth.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserVerificationRequest {

  @NotBlank
  @NotEmpty
  private String username;
  @NotBlank
  @NotEmpty
  @Size(min = 8)
  private String password;
  @NotBlank
  @NotEmpty
  @Size(min = 6, max = 6)
  private String verificationCode;

  public UserVerificationRequest() {
  }

  public UserVerificationRequest(String username, String password, String verificationCode) {
    this.username = username;
    this.password = password;
    this.verificationCode = verificationCode;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getVerificationCode() {
    return verificationCode;
  }
}
