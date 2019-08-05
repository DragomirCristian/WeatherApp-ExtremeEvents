package com.dragomircristian.extremeevents.utilities;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sun.font.StrikeCache;

import javax.xml.ws.Response;

public class UtilsFunctions {
    public boolean validPageNumberAndPageSize(int pageNumber, int pageSize) {
        if (pageNumber < 0 || pageSize < 0)
            return false;
        return true;
    }

    public ResponseEntity<String> respond(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(message, httpStatus);
    }
}
