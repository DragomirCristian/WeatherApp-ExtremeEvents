package com.dragomircristian.extremeevents.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPageSizeException extends ResponseStatusException {
    public InvalidPageSizeException() {
        super(HttpStatus.BAD_REQUEST, "The page size is invalid, it can't be 0 or less.");
    }
}
