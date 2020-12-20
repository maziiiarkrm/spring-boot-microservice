package com.boarz.auth.service;


import com.boarz.auth.entity.User;
import com.boarz.auth.entity.UserUsernameType;

public interface UserService {

    User register(String email, String phoneNumber, UserUsernameType type, String introduceCode);

    User verify(String username, String verificationCode, String password);

    String login(String username, String password);

    User forgetPasswordRequest(String username);

    User forgetPasswordVerifying(String username, String forgetPasswordCode, String password);

    User changePassword(String username, String lastPassword, String newPassword);

}
