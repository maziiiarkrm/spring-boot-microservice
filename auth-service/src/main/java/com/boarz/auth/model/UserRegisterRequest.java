package com.boarz.auth.model;


import com.boarz.auth.entity.UserUsernameType;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;

public class UserRegisterRequest {

  @Email
  private String email;
  @NumberFormat
  private String phoneNumber;
  private UserUsernameType type;
  private String introduceCode;

  public UserRegisterRequest() {
  }

  public UserRegisterRequest(
      String email,
      String phoneNumber,
      UserUsernameType type,
      String introduceCode) {
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.type = type;
    this.introduceCode = introduceCode;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public UserUsernameType getType() {
    return type;
  }

  public void setType(UserUsernameType type) {
    this.type = type;
  }

  public String getIntroduceCode() {
    return introduceCode;
  }

  public void setIntroduceCode(String introduceCode) {
    this.introduceCode = introduceCode;
  }
}
