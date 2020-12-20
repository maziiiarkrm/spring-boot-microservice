package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class RoleWithThisNameExistsException extends CustomException {

    public RoleWithThisNameExistsException() {
        super("There is one role with the same name", HttpStatus.BAD_REQUEST);
    }

}
