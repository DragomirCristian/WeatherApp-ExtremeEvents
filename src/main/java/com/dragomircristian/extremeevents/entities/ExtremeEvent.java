package com.dragomircristian.extremeevents.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.sql.Timestamp;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Setter
@Getter
@Document(collection = "extremeevents")
public class ExtremeEvent {
    @Id
    private String id;
    private Location location;
    private String title;
    private String description;
    private String county;
    private String country;
    private Date timestamp;
    private Weather weather;
    private String imgLink;
    private String vidLink;

    public ExtremeEvent() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.timestamp = new Date(ts.getTime());
    }

    public ExtremeEvent(Location location, String title, String description, Weather weather, String imgLink, String vidLink) {
        this.location = location;
        this.title = title;
        this.description = description;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.timestamp = new Date(ts.getTime());
        this.weather = weather;
        this.imgLink = imgLink;
        this.vidLink = vidLink;
    }

    public ExtremeEvent(Location location, String title, String description, Weather weather, String imgLink) {
        this.location = location;
        this.title = title;
        this.description = description;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.timestamp = new Date(ts.getTime());
        this.weather = weather;
        this.imgLink = imgLink;
    }

    public ExtremeEvent(String vidLink, Location location, String title, String description, Weather weather) {
        this.location = location;
        this.title = title;
        this.description = description;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.timestamp = new Date(ts.getTime());
        this.weather = weather;
        this.vidLink = vidLink;
    }

    public ExtremeEvent(Location location, String title, String description, Weather weather) {
        this.location = location;
        this.title = title;
        this.description = description;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.timestamp = new Date(ts.getTime());
        this.weather = weather;
    }
}
