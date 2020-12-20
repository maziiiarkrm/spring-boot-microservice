package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class NoTypeForUserTypeException extends CustomException {

    public NoTypeForUserTypeException() {
        super("No type for user defined", HttpStatus.BAD_REQUEST);
    }

}
