package com.boarz.auth.controller;

import com.boarz.auth.exception.BadInputException;
import com.boarz.auth.helper.ResponseHelper;
import com.boarz.auth.model.*;
import com.boarz.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

  @Autowired private UserService userService;

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity register(@RequestBody UserRegisterRequest request, BindingResult result) {
    if (result.hasErrors()) throw new BadInputException(result.getFieldErrors());
    return ResponseHelper.response(
        userService.register(
            request.getEmail(),
            request.getPhoneNumber(),
            request.getType(),
            request.getIntroduceCode()));
  }

  @RequestMapping(value = "/verify", method = RequestMethod.POST)
  public ResponseEntity verify(@RequestBody UserVerificationRequest request, BindingResult result) {
    if (result.hasErrors()) throw new BadInputException(result.getFieldErrors());
    return ResponseHelper.response(
        userService.verify(
            request.getUsername(), request.getVerificationCode(), request.getPassword()));
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity login(@RequestBody UserLoginRequest request, BindingResult result) {
    if (result.hasErrors()) throw new BadInputException(result.getFieldErrors());
    return ResponseHelper.response(userService.login(request.getUsername(), request.getPassword()));
  }

  @RequestMapping(value = "/forget-password/request", method = RequestMethod.POST)
  public ResponseEntity forgetPasswordRequest(@RequestBody UserForgetPasswordRequest request, BindingResult result) {
    if (result.hasErrors()) throw new BadInputException(result.getFieldErrors());
    return ResponseHelper.response(userService.forgetPasswordRequest(request.getUsername()));
  }

  @RequestMapping(value = "/forget-password/complete", method = RequestMethod.POST)
  public ResponseEntity forgetPasswordVerifying(
      @RequestBody UserForgetPasswordVerifyRequest request, BindingResult result) {
    if (result.hasErrors()) throw new BadInputException(result.getFieldErrors());
    return ResponseHelper.response(
        userService.forgetPasswordVerifying(
            request.getUsername(), request.getForgetPasswordCode(), request.getPassword()));
  }

  @RequestMapping(value = "/change-password", method = RequestMethod.POST)
  public ResponseEntity changePassword(@RequestBody UserChangePasswordRequest request, BindingResult result) {
    if (result.hasErrors()) throw new BadInputException(result.getFieldErrors());
    return ResponseHelper.response(
        userService.changePassword(
            request.getUsername(), request.getLastPassword(), request.getNewPassword()));
  }
}
