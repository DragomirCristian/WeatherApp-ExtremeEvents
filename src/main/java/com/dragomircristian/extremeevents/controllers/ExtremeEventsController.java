package com.dragomircristian.extremeevents.controllers;

import com.dragomircristian.extremeevents.exceptions.InvalidPageNumberException;
import com.dragomircristian.extremeevents.exceptions.InvalidPageSizeException;
import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.entities.Location;

import com.dragomircristian.extremeevents.forms.CommentForm;
import com.dragomircristian.extremeevents.forms.ExtremeEventForm;
import com.dragomircristian.extremeevents.services.ExtremeEventsService;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/extreme-events")
public class ExtremeEventsController {
    @Value("${url.urlOverHttps")
    String urlOverHttps;
    @Autowired
    ExtremeEventsService extremeEventsService;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExtremeEventsController.class);

    @RequestMapping(value = "/city/{city}", params = {"pageNumber", "pageSize"}, method = RequestMethod.GET)
    public ResponseEntity<Page<ExtremeEvent>> findAllByCity(@PathVariable("city") String city, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) throws InvalidPageNumberException, InvalidPageSizeException {
        if (pageNumber < -0)
            throw new InvalidPageNumberException();
        if (pageSize <= 0)
            throw new InvalidPageSizeException();
        return new ResponseEntity<>(extremeEventsService.findAllByCity(city, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @RequestMapping(value = "/country/{country}", params = {"pageNumber", "pageSize"}, method = RequestMethod.GET)
    public ResponseEntity<Page<ExtremeEvent>> findAllByCountry(@PathVariable("country") String country, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) throws InvalidPageNumberException, InvalidPageSizeException {
        if (pageNumber < 0)
            throw new InvalidPageNumberException();
        if (pageSize <= 0)
            throw new InvalidPageSizeException();
        return new ResponseEntity<>(extremeEventsService.findAllByCountry(country, PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @RequestMapping(value = "/all", params = {"pageNumber", "pageSize"}, method = RequestMethod.GET)
    public ResponseEntity<Page<ExtremeEvent>> getAllExtremeEvents(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize) throws InvalidPageNumberException, InvalidPageSizeException {
        if (pageNumber < 0)
            throw new InvalidPageNumberException();
        if (pageSize <= 0)
            throw new InvalidPageSizeException();
        return new ResponseEntity<>(extremeEventsService.getAllExtremeEvents(PageRequest.of(pageNumber, pageSize)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteExtremeEvent(@PathVariable("id") String id) {
        extremeEventsService.deleteExtremeEventById(id);
    }

    @RequestMapping(value = "/upload-extreme-event", method = RequestMethod.POST)
    public ResponseEntity<?> upload(@RequestHeader(value = "Authorization") String access_token, @RequestBody ExtremeEventForm extremeEventForm) throws GeneralSecurityException {
        System.out.println(access_token);
        LOGGER.info("sunt in endpoint");
        TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                .setConnectionManager(connectionManager).build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        String authorizationHeader = access_token;
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", authorizationHeader);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
        ResponseEntity<String> response = restTemplate
                .exchange(urlOverHttps, HttpMethod.GET, requestEntity, String.class);

        String link = extremeEventsService.upload();
        extremeEventsService.addExtremeEvent(new Location(extremeEventForm.getLatitude(), extremeEventForm.getLongitude()), extremeEventForm.getTitle(), extremeEventForm.getDescription(), response.getBody(), link);
        return new ResponseEntity<>("Extreme event added succesfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/vote/{idExtremeEvent}/{vote}", method = RequestMethod.POST)
    public ResponseEntity<String> vote(@PathVariable("idExtremeEvent") String idExtremeEvent, @PathVariable("vote") String vote, @RequestBody(required = false) CommentForm comment) {
        switch (vote) {
            case "like":
                try {
                    extremeEventsService.like(extremeEventsService.getExtremeEventById(idExtremeEvent), comment.getComment());
                } catch (NullPointerException e) {
                    extremeEventsService.like(extremeEventsService.getExtremeEventById(idExtremeEvent));
                }
                if (comment == null) {
                    return new ResponseEntity<String>("You just liked the event with id " + idExtremeEvent + ".", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("You just liked and commented the event with id " + idExtremeEvent + ".", HttpStatus.OK);
                }
            case "dislike":
                try {
                    extremeEventsService.dislike(extremeEventsService.getExtremeEventById(idExtremeEvent), comment.getComment());
                } catch (NullPointerException e) {
                    extremeEventsService.dislike(extremeEventsService.getExtremeEventById(idExtremeEvent));
                }
                if (comment == null) {
                    return new ResponseEntity<String>("You just disliked the event with id " + idExtremeEvent + ".", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("You just disliked and commented the event with id " + idExtremeEvent + ".", HttpStatus.OK);
                }
            default:
                return new ResponseEntity<>("{vote} should be either like or dislike.", HttpStatus.BAD_REQUEST);
        }
    }

}
