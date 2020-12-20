package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class WrongPasswordForChanginPasswordException extends CustomException {

  public WrongPasswordForChanginPasswordException() {
    super("Wrong last password", HttpStatus.FORBIDDEN);
  }
}
