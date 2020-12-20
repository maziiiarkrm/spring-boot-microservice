package com.boarz.auth.exception;

import org.springframework.validation.FieldError;

import java.util.List;

public class BadInputException extends RuntimeException {
    private List<FieldError> fieldErrors;

    public BadInputException(Object... objects) {
        if (objects.length != 0)
            this.fieldErrors = (List<FieldError>) objects[0];
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
