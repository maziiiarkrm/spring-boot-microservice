package com.boarz.auth.exception;

//import com.Prana.Latifi.handler.CustomException;
import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {

  public NotFoundException(String entity) {
    super(entity + ": Not Found", HttpStatus.NOT_FOUND);
  }
}
