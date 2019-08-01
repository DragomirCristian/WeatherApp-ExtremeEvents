package com.dragomircristian.extremeevents.controllers;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.services.ExtremeEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/extreme-events")
public class ExtremeEventsController {

    @Autowired
    ExtremeEventsService extremeEventsService;

    public ResponseEntity<String> getAllExtremeEvents() {
        Iterable<ExtremeEvent> events = extremeEventsService.getAllExtremeEvents();
        return new ResponseEntity<String>("All the extreme events", events);
        // TO DO
    }
}
