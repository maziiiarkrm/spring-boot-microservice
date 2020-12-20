package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class WrongForgetPasswordCode extends CustomException {

  public WrongForgetPasswordCode() {

    super("Wrong forget password code", HttpStatus.NOT_ACCEPTABLE);
  }
}
