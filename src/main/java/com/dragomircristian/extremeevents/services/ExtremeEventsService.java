package com.dragomircristian.extremeevents.services;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.repository.ExtremeEventsRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
@Configuration
public class ExtremeEventsService {
    @Value("${url.openCageApiUrl}")
    private String openCageApiUrl;

    @Value("${url.pretty}")
    private String pretty;

    @Value("${key.openCageApiKey}")
    private String openCageApiKey;

    @Autowired
    ExtremeEventsRepository extremeEventsRepository;

    public ExtremeEvent getExtremeEventById(String id) {
        return extremeEventsRepository.findById(id).get();
    }

    public ArrayList<ExtremeEvent> getAllExtremeEvents() {
        ArrayList<ExtremeEvent> list = new ArrayList<>();
        for (ExtremeEvent extremeEvent : extremeEventsRepository.findAll()) {
            list.add(extremeEvent);
        }
        return list;
    }

    public List<ExtremeEvent> findAllByCounty(String county, PageRequest pageRequest) {
        return extremeEventsRepository.findAllByCounty(county, pageRequest);
    }

    public List<ExtremeEvent> findAllByCountry(String country, PageRequest pageRequest) {
        return extremeEventsRepository.findAllByCountry(country, pageRequest);
    }

    public void deleteExtremeEventById(String id) {
        extremeEventsRepository.deleteById(id);
    }

    public void updateExtremeEvent(ExtremeEvent extremeEvent) {
        this.extremeEventsRepository.save(extremeEvent);
    }

    public void insertExtremeEvent(ExtremeEvent extremeEvent) {
        this.extremeEventsRepository.save(extremeEvent);
    }

    public boolean exists(String id) {
        return extremeEventsRepository.findById(id).isPresent();
    }

    public void setCityAndCountry(ExtremeEvent extremeEvent) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openCageApiKey);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        String url = openCageApiUrl + extremeEvent.getLocation().getLatitude() + "%2C" + extremeEvent.getLocation().getLongitude() + "&key=" + openCageApiKey + "&" + pretty;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        Gson gson = new Gson();
        JsonObject responseObj = gson.fromJson(response.getBody(), JsonObject.class);
        String country;
        String county;
        try {
            country = responseObj.get("results").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("country").getAsString();
        } catch (NullPointerException e) {
            country = "-";
        }
        try {
            county = responseObj.get("results").getAsJsonArray().get(0).getAsJsonObject().get("components").getAsJsonObject().get("county").getAsString();
        } catch (NullPointerException e) {
            county = "-";
        }

        extremeEvent.setCounty(county);
        extremeEvent.setCountry(country);
    }

}
