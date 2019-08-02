package com.dragomircristian.extremeevents.controllers;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.services.ExtremeEventsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/extreme-events")
public class ExtremeEventsController {

    @Autowired
    ExtremeEventsService extremeEventsService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ArrayList> getAllExtremeEvents() {
        ArrayList<ExtremeEvent> events = extremeEventsService.getAllExtremeEvents();

        Gson gson = new Gson();
        String eventsJson = gson.toJson(events);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
