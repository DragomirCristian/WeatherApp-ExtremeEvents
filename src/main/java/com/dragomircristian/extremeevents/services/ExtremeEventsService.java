package com.dragomircristian.extremeevents.services;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.repository.ExtremeEventsRepository;
import com.google.auth.Credentials;
import com.google.cloud.storage.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public Page<ExtremeEvent> getAllExtremeEvents(PageRequest pageRequest) {
        return extremeEventsRepository.findAll(pageRequest);
    }

    public Page<ExtremeEvent> findAllByCounty(String county, PageRequest pageRequest) {
        return extremeEventsRepository.findAllByCounty(county, pageRequest);
    }

    public Page<ExtremeEvent> findAllByCountry(String country, PageRequest pageRequest) {
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


    public String uploadImage(String fileName, String filePath, String fileType, Credentials credentials, Storage storage) throws IOException {
        Bucket bucket = getBucket("weather-app-bucket", credentials);
        InputStream inputStream = new FileInputStream(new File(filePath));
        Blob blob = bucket.create(fileName, inputStream, fileType);
        String name = blob.getName();
        BlobId blobId = BlobId.of("weather-app-bucket", name);
        Acl acl = storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        return blob.getMediaLink();
    }


    private Bucket getBucket(String bucketName, Credentials credentials) throws IOException {
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Bucket bucket = storage.get(bucketName);
        if (bucket == null) {
            throw new IOException("Bucket not found:" + bucketName);
        }
        return bucket;
    }

    public String getFileType(String filePath, String[] extensions, String type) {
        for (String extension : extensions) {
            if (filePath.endsWith(extension)) {
                return type +"/"+ extension;
            }
        }
        return null;
    }
}
