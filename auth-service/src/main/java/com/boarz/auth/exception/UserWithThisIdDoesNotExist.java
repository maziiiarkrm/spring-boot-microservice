package com.boarz.auth.exception;

import com.boarz.auth.handler.CustomException;
import org.springframework.http.HttpStatus;

public class UserWithThisIdDoesNotExist extends CustomException {

    public UserWithThisIdDoesNotExist() {
        super("User with this ID does not exist", HttpStatus.NOT_FOUND);
    }
}
