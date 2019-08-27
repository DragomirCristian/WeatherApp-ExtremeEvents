package com.dragomircristian.extremeevents.services;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageComponents;
import com.byteowls.jopencage.model.JOpenCageResponse;
import com.byteowls.jopencage.model.JOpenCageReverseRequest;
import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.entities.Location;
import com.dragomircristian.extremeevents.entities.Weather;
import com.dragomircristian.extremeevents.repository.ExtremeEventsRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Configuration
public class ExtremeEventsService {
    @Value("${url.openCageApiUrl}")
    private String openCageApiUrl;

    @Value("${url.pretty}")
    private String pretty;

    @Value("${key.openCageApiKey}")
    private String openCageApiKey;

    @Value("${url.weatherAppApiUrl}")
    private String weatherAppApiUrl;

    @Autowired
    ExtremeEventsRepository extremeEventsRepository;

    public ExtremeEvent getExtremeEventById(String id) {
        return extremeEventsRepository.findById(id).get();
    }

    public Page<ExtremeEvent> getAllExtremeEvents(PageRequest pageRequest) {
        return extremeEventsRepository.findAll(pageRequest);
    }

    public Page<ExtremeEvent> findAllByCity(String city, PageRequest pageRequest) {
        return extremeEventsRepository.findAllByCity(city, pageRequest);
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

    // needs to be completed
    public void addExtremeEvent(Location location, String title, String description, String email, String link) {
        ExtremeEvent extremeEvent = new ExtremeEvent();
        extremeEvent.setLocation(location);
        extremeEvent.setTitle(title);
        extremeEvent.setDescription(description);
        extremeEvent.setUser(email);
        extremeEvent.addLink(link);
        setCityAndCountry(extremeEvent);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(weatherAppApiUrl + extremeEvent.getCity(), String.class);
        Gson gson = new Gson();
        Weather weather = gson.fromJson(response.getBody(), Weather.class);
        extremeEvent.setWeather(weather);

        this.insertExtremeEvent(extremeEvent);
    }

    public void setCityAndCountry(ExtremeEvent extremeEvent) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(openCageApiKey);
        JOpenCageReverseRequest request = new JOpenCageReverseRequest(Double.parseDouble(extremeEvent.getLocation().getLatitude()), Double.parseDouble(extremeEvent.getLocation().getLongitude()));
        request.setLanguage("en");
        request.setNoDedupe(true);
        request.setLimit(5);
        request.setNoAnnotations(true);

        JOpenCageResponse response = jOpenCageGeocoder.reverse(request);

        JOpenCageComponents components = response.getResults().get(0).getComponents();
        String city = components.getCity();
        String country = components.getCountry();

        extremeEvent.setCity(StringUtils.stripAccents(city));
        extremeEvent.setCountry(StringUtils.stripAccents(country));
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
                return type + "/" + extension;
            }
        }
        return null;
    }

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
            if (this.getFileType(filePath, allowedExtensions, "image") != null)
                fileType = this.getFileType(filePath, allowedExtensions, "image");
            else
                fileType = this.getFileType(filePath, allowedVideoExtensions, "video");
            String[] arr = filePath.split("\\\\", 0);
            String name = arr[arr.length - 1];
            String link = this.uploadImage(name, filePath, fileType, credentials, storage);
            return link;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
