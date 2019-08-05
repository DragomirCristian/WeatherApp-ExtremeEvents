package com.dragomircristian.extremeevents.controllers;

import com.dragomircristian.extremeevents.Exceptions.InvalidPageNumberException;
import com.dragomircristian.extremeevents.Exceptions.InvalidPageSizeException;
import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.entities.Location;
import com.dragomircristian.extremeevents.entities.Weather;
import com.dragomircristian.extremeevents.services.ExtremeEventsService;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/extreme-events")
public class ExtremeEventsController {

    @Autowired
    ExtremeEventsService extremeEventsService;

    @RequestMapping(value = "/county/{county}", params = {"p", "s"}, method = RequestMethod.GET)
    public ResponseEntity<Page<ExtremeEvent>> findAllByCounty(@PathVariable("county") String county, @RequestParam("p") int pageNumber, @RequestParam("s") int pageSize) throws InvalidPageNumberException, InvalidPageSizeException {
        if (pageNumber < -0)
            throw new InvalidPageNumberException();
        if (pageSize <= 0)
            throw new InvalidPageSizeException();
        return new ResponseEntity<>(extremeEventsService.findAllByCounty(county, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
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

    @RequestMapping(value = "/ceva", method = RequestMethod.GET)
    public ResponseEntity<ExtremeEvent> something() {
        Location location = new Location("45.631910", "27.533800");
        ExtremeEvent extremeEvent = new ExtremeEvent(location, "title", "description", new Weather(), "djnadinadna link");
        extremeEventsService.setCityAndCountry(extremeEvent);
        extremeEventsService.insertExtremeEvent(extremeEvent);
        return new ResponseEntity<>(new ExtremeEvent(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteExtremeEvent(@PathVariable("id") String id) {
        extremeEventsService.deleteExtremeEventById(id);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() throws Exception {
//        BufferedImage image = ImageIO.read(new File("D:\\Programare\\InternshipNTT\\extremeWeather\\WeatherApp-ExtremeEvents\\test.jpg"));
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(image, "jpg", outputStream);
//        Base64 base64 = new Base64();
//        String encodedString = new String(Base64.encodeBase64(outputStream.toByteArray()));
//        System.out.println(encodedString);

        StringBuilder sb = new StringBuilder();
        File file = new File("D:\\\\Programare\\\\InternshipNTT\\\\extremeWeather\\\\WeatherApp-ExtremeEvents\\\\test.mp4");
        InputStream inStream = null;
        BufferedInputStream bis = null;
        inStream = new FileInputStream(file);
        bis = new BufferedInputStream(inStream);
        while (bis.available() > 0) {
            // System.out.println(Integer.toBinaryString(bis.read()));
            sb.append(Integer.toBinaryString(bis.read()));
        }
        System.out.println(sb);
    }
}
