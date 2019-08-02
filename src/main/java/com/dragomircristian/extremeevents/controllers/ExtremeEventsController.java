package com.dragomircristian.extremeevents.controllers;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.entities.Location;
import com.dragomircristian.extremeevents.entities.Weather;
import com.dragomircristian.extremeevents.services.ExtremeEventsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("/extreme-events")
public class ExtremeEventsController {

    @Autowired
    ExtremeEventsService extremeEventsService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList> getAllExtremeEvents() {
        ArrayList<ExtremeEvent> events = extremeEventsService.getAllExtremeEvents();

//        Gson gson = new Gson();
//        String eventsJson = gson.toJson(events);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/ceva", method = RequestMethod.GET)
    public ResponseEntity<ExtremeEvent> something() {
        Location location = new Location("45.631910", "27.533800");
        ExtremeEvent extremeEvent = new ExtremeEvent(location, "title", "description", new Timestamp(new Date().getTime()), new Weather(), "djnadinadna link");
        extremeEventsService.setCityAndCountry(extremeEvent);
        return new ResponseEntity<>(extremeEvent, HttpStatus.OK);
    }

}
