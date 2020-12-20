package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class NoUsernameExistInRequestException extends CustomException {

    public NoUsernameExistInRequestException() {
        super("No value for username exists", HttpStatus.BAD_REQUEST);
    }

}
