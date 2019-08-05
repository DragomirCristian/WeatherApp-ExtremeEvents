package com.dragomircristian.extremeevents.controllers;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.entities.Location;
import com.dragomircristian.extremeevents.entities.Weather;
import com.dragomircristian.extremeevents.services.ExtremeEventsService;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;
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

    @RequestMapping(value = "/county/{county}?p={pageNumber}&s={pageSize}", method = RequestMethod.GET)
    public ResponseEntity<String> findAllExtremeEvents(@PathVariable("county") String county, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
//        List<ExtremeEvent> events = extremeEventsService.findAllByCounty(county, new PageRequest(pageNumber, pageSize));
        return new ResponseEntity<>("haha", HttpStatus.OK);
    }


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
        ExtremeEvent extremeEvent = new ExtremeEvent(location, "title", "description", new Weather(), "djnadinadna link");
        extremeEventsService.setCityAndCountry(extremeEvent);
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

        StringBuilder sb=new StringBuilder();
        File file=new File("D:\\\\Programare\\\\InternshipNTT\\\\extremeWeather\\\\WeatherApp-ExtremeEvents\\\\test.mp4");
        InputStream inStream = null;
        BufferedInputStream bis = null;
        inStream = new FileInputStream(file);
        bis = new BufferedInputStream(inStream);
        while(bis.available()>0) {
           // System.out.println(Integer.toBinaryString(bis.read()));
            sb.append(Integer.toBinaryString(bis.read()));
        }
        System.out.println(sb);
    }
}
