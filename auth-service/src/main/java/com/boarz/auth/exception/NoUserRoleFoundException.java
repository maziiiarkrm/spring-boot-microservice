package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class NoUserRoleFoundException extends CustomException {

    public NoUserRoleFoundException() {
        super("No User Role founded", HttpStatus.NOT_FOUND);
    }

}
