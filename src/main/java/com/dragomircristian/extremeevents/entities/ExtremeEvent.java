package com.dragomircristian.extremeevents.entities;

import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "extremeevents")
public class ExtremeEvent {
    @Id
    private Integer id;
    private Location location;
    private String title;
    private String description;
    private String county;
    private String country;
    private Timestamp timestamp;
    private Weather weather;
    private String img_link;
    private String vid_link;

    public ExtremeEvent(Location location, String title, String description, Timestamp timestamp, Weather weather, String img_link, String vid_link) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.weather = weather;
        this.img_link = img_link;
        this.vid_link = vid_link;
    }

    public ExtremeEvent(Location location, String title, String description, Timestamp timestamp, Weather weather, String img_link) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.weather = weather;
        this.img_link = img_link;
    }

    public ExtremeEvent(String vid_link, Location location, String title, String description, Timestamp timestamp, Weather weather) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
        this.weather = weather;
        this.vid_link = vid_link;
    }

    public ExtremeEvent(Location location, String title, String description, Timestamp timestamp, Weather weather) {
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
