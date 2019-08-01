package com.dragomircristian.extremeevents.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ExtremeEvent {
    @Value("${url.openCageApiUrl}")
    private String openCageApiUrl;

    @Value("${key.openCageApiUrl}")
    private String openCageApiKey;

    @Value("${url.pretty}")
    private String pretty;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Location location;
    private String title;
    private String description;
    private String city;
    private String country;
    private Date timestamp;
    private Weather weather;
    private String img_link;
    private String vid_link;

    public ExtremeEvent(Location location, String title, String description, Date timestamp, Weather weather, String img_link, String vid_link) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.weather = weather;
        this.img_link = img_link;
        this.vid_link = vid_link;
    }

    public ExtremeEvent(Location location, String title, String description, Date timestamp, Weather weather, String img_link) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.weather = weather;
        this.img_link = img_link;
    }

    public ExtremeEvent(String vid_link, Location location, String title, String description, Date timestamp, Weather weather) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.weather = weather;
        this.vid_link = vid_link;
    }

    public ExtremeEvent(Location location, String title, String description, Date timestamp, Weather weather) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.weather = weather;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getVid_link() {
        return vid_link;
    }

    public void setVid_link(String vid_link) {
        this.vid_link = vid_link;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCityAndCountry() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(openCageApiUrl + this.location.getLatitude() + "%2C" + this.location.getLongitude() + "&key=" + openCageApiKey + pretty, String.class);

        // TO DO  see the response and set the city and country
        System.out.println(response);

    }
}
