package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class VerifiedUserExistsException extends CustomException {

  public VerifiedUserExistsException() {
    super("Verified user exists", HttpStatus.NOT_ACCEPTABLE);
  }
}
