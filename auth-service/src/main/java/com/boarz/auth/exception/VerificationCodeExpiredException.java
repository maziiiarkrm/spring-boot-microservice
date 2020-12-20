package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class VerificationCodeExpiredException extends CustomException {

  public VerificationCodeExpiredException() {
    super("Your verification code is expired", HttpStatus.NOT_ACCEPTABLE);
  }
}
