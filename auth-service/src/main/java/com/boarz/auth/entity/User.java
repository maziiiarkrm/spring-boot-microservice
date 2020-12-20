package com.boarz.auth.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_auth")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "phone_number", unique = true)
  private String phoneNumber;

  @Column(name = "type")
  private UserUsernameType type;

  @Column(name = "verification_code")
  private String verificationCode;

  @Column(name = "verification_code_date")
  private long verificationCodeDate;

  @Column(name = "forget_password_code")
  private String forgetPasswordCode;

  @Column(name = "forget_password_code_date")
  private long forgetPasswordCodeDate;

  @Column(name = "is_verified")
  private boolean isVerified;

  @Column(name = "is_active")
  private boolean isActive;

  @ManyToMany(cascade = CascadeType.DETACH)
  @JoinTable(
      name = "user_role_auth",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<Role> roles;

  @Column(name = "creation_date")
  private long creationDate;

  @Column(name = "update_date")
  private long updateDate;

  public User(
      String username,
      String email,
      String phoneNumber,
      UserUsernameType type,
      String verificationCode,
      long verificationCodeDate,
      String forgetPasswordCode,
      long forgetPasswordCodeDate,
      boolean isVerified,
      boolean isActive,
      List<Role> roles) {
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.type = type;
    this.verificationCode = verificationCode;
    this.verificationCodeDate = verificationCodeDate;
    this.forgetPasswordCode = forgetPasswordCode;
    this.forgetPasswordCodeDate = forgetPasswordCodeDate;
    this.isVerified = isVerified;
    this.isActive = isActive;
    this.roles = roles;
  }

  public User(
      long id,
      String username,
      String email,
      String phoneNumber,
      UserUsernameType type,
      String verificationCode,
      long verificationCodeDate,
      String forgetPasswordCode,
      long forgetPasswordCodeDate,
      boolean isVerified,
      boolean isActive,
      List<Role> roles) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.type = type;
    this.verificationCode = verificationCode;
    this.verificationCodeDate = verificationCodeDate;
    this.forgetPasswordCode = forgetPasswordCode;
    this.forgetPasswordCodeDate = forgetPasswordCodeDate;
    this.isVerified = isVerified;
    this.isActive = isActive;
    this.roles = roles;
  }

  public User() {}

  @PrePersist
  public void prePersist() {
    creationDate = new Date().getTime();
  }

  @PreUpdate
  public void preUpdate() {
    updateDate = new Date().getTime();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public String getVerificationCode() {
    return verificationCode;
  }

  public void setVerificationCode(String verificationCode) {
    this.verificationCode = verificationCode;
  }

  public long getVerificationCodeDate() {
    return verificationCodeDate;
  }

  public void setVerificationCodeDate(long verificationCodeDate) {
    this.verificationCodeDate = verificationCodeDate;
  }

  public String getForgetPasswordCode() {
    return forgetPasswordCode;
  }

  public void setForgetPasswordCode(String forgetPasswordCode) {
    this.forgetPasswordCode = forgetPasswordCode;
  }

  public long getForgetPasswordCodeDate() {
    return forgetPasswordCodeDate;
  }

  public void setForgetPasswordCodeDate(long forgetPasswordCodeDate) {
    this.forgetPasswordCodeDate = forgetPasswordCodeDate;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public void setVerified(boolean verified) {
    isVerified = verified;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(long creationDate) {
    this.creationDate = creationDate;
  }

  public long getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(long updateDate) {
    this.updateDate = updateDate;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return id == user.id && Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username);
  }
}
