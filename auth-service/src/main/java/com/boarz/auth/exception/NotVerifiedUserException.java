package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class NotVerifiedUserException extends CustomException {

  public NotVerifiedUserException() {
    super("Not verified user", HttpStatus.LOCKED);
  }
}
