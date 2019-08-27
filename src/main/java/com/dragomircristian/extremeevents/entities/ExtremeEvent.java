package com.dragomircristian.extremeevents.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

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
    private String city;
    private String country;
    private Date timestamp;
    private Weather weather;
    private String imgLink;
    private String vidLink;
    private String user;
    private List<String> links;

    public ExtremeEvent() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        this.timestamp = new Date(ts.getTime());
        this.links=new ArrayList<>();
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
    public void addLink(String link){
        links.add(link);
    }
    @Override
    public String toString() {
        return "ExtremeEvent{" +
                "id='" + id + '\'' +
                ", location=" + location +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", timestamp=" + timestamp +
                ", weather=" + weather +
                ", imgLink='" + imgLink + '\'' +
                ", vidLink='" + vidLink + '\'' +
                '}';
    }
}
