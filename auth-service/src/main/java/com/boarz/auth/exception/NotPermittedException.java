package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class NotPermittedException extends CustomException {

    public NotPermittedException() {
        super("You are not permitted for this action", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
