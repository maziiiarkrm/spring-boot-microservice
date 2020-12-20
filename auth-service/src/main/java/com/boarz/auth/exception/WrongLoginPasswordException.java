package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class WrongLoginPasswordException extends CustomException {

  public WrongLoginPasswordException() {
    super("Wrong login password", HttpStatus.FORBIDDEN);
  }
}
