package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class NotActiveUserException extends CustomException {

  public NotActiveUserException() {
    super("Not active user", HttpStatus.LOCKED);
  }
}
