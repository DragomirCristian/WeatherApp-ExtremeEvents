package com.dragomircristian.extremeevents.controllers;

import com.dragomircristian.extremeevents.exceptions.InvalidPageNumberException;
import com.dragomircristian.extremeevents.exceptions.InvalidPageSizeException;
import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.entities.Location;
import com.dragomircristian.extremeevents.entities.Weather;
import com.dragomircristian.extremeevents.forms.ExtremeEventForm;
import com.dragomircristian.extremeevents.services.ExtremeEventsService;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController
@RequestMapping("/extreme-events")
public class ExtremeEventsController {

    @Autowired
    ExtremeEventsService extremeEventsService;

    @RequestMapping(value = "/city/{city}", params = {"p", "s"}, method = RequestMethod.GET)
    public ResponseEntity<Page<ExtremeEvent>> findAllByCity(@PathVariable("city") String city, @RequestParam("p") int pageNumber, @RequestParam("s") int pageSize) throws InvalidPageNumberException, InvalidPageSizeException {
        if (pageNumber < -0)
            throw new InvalidPageNumberException();
        if (pageSize <= 0)
            throw new InvalidPageSizeException();
        return new ResponseEntity<>(extremeEventsService.findAllByCity(city, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @RequestMapping(value = "/country/{country}", params = {"p", "s"}, method = RequestMethod.GET)
    public ResponseEntity<Page<ExtremeEvent>> findAllByCountry(@PathVariable("country") String country, @RequestParam("p") int pageNumber, @RequestParam("s") int pageSize) throws InvalidPageNumberException, InvalidPageSizeException {
        if (pageNumber < 0)
            throw new InvalidPageNumberException();
        if (pageSize <= 0)
            throw new InvalidPageSizeException();
        return new ResponseEntity<>(extremeEventsService.findAllByCountry(country, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", params = {"p", "s"}, method = RequestMethod.GET)
    public ResponseEntity<Page<ExtremeEvent>> getAllExtremeEvents(@RequestParam("p") int pageNumber, @RequestParam("s") int pageSize) throws InvalidPageNumberException, InvalidPageSizeException {
        if (pageNumber < 0)
            throw new InvalidPageNumberException();
        if (pageSize <= 0)
            throw new InvalidPageSizeException();
        return new ResponseEntity<>(extremeEventsService.getAllExtremeEvents(PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @RequestMapping(value = "/test-add", method = RequestMethod.POST)
    public ResponseEntity<String> addExtremeEvent(@RequestBody ExtremeEventForm extremeEventForm) {
        if (extremeEventForm.getDescription() == null || extremeEventForm.getLatitude() == null || extremeEventForm.getLongitude() == null || extremeEventForm.getTitle() == null)
            return new ResponseEntity<>("There are some missing arguments in the body request. \n Needed: latitude, longitude, title, description.", HttpStatus.BAD_REQUEST);
        extremeEventsService.addExtremeEvent(new Location(extremeEventForm.getLatitude(), extremeEventForm.getLongitude()), extremeEventForm.getTitle(), extremeEventForm.getDescription());
        return new ResponseEntity<>("This event was added.", HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteExtremeEvent(@PathVariable("id") String id) {
        extremeEventsService.deleteExtremeEventById(id);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload() {
        Credentials credentials = null;
        try {
            credentials = GoogleCredentials
                    .fromStream(new FileInputStream("C:\\central-insight-236815-fe3ff1a881e4.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId("central-insight-236815").build().getService();
        try {
            String[] allowedExtensions = {"jpg", "png", "jpeg", "gif"};
            String[] allowedVideoExtensions = {"mp4", "wmv", "flv"};
            String filePath = "C:\\Users\\mihai.botez\\Desktop\\git\\ExtremeEvents\\WeatherApp-ExtremeEvents\\test.mp4";
            String fileType = null;
            if (extremeEventsService.getFileType(filePath, allowedExtensions, "image") != null)
                fileType = extremeEventsService.getFileType(filePath, allowedExtensions, "image");
            else
                fileType = extremeEventsService.getFileType(filePath, allowedVideoExtensions, "video");
            String[] arr = filePath.split("\\\\", 0);
            String name = arr[arr.length - 1];
            String link = extremeEventsService.uploadImage(name, filePath, fileType, credentials, storage);
            return link;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
