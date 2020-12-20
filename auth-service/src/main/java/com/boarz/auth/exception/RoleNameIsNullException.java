package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class RoleNameIsNullException extends CustomException {

    public RoleNameIsNullException() {
        super("Role's name must not be null or empty", HttpStatus.BAD_REQUEST);
    }

}
