package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class TokenNotValidateWithThisSecretPass extends CustomException {

  public TokenNotValidateWithThisSecretPass() {
    super("Token not validated", HttpStatus.FORBIDDEN);
  }
}
