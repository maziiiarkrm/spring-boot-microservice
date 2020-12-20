package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class ForbiddenForYou extends CustomException {

    public ForbiddenForYou() {
        super("This operation is forbidden for you!", HttpStatus.FORBIDDEN);
    }
}
