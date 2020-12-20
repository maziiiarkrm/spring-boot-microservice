package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class ForgetPasswordCodeExpiredException extends CustomException {

  public ForgetPasswordCodeExpiredException() {
    super("Forget password code expired", HttpStatus.NOT_ACCEPTABLE);
  }
}
