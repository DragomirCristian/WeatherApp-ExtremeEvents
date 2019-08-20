package com.dragomircristian.extremeevents.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPageNumberException extends ResponseStatusException {
    public InvalidPageNumberException() {
        super(HttpStatus.BAD_REQUEST, "The page number is invalid, it can't be less than 0.");
    }
}
