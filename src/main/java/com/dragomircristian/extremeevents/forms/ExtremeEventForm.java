package com.dragomircristian.extremeevents.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ExtremeEventForm {
    private String latitude;
    private String longitude;
    private String title;
    private String description;

    @Override
    public String toString() {
        return "ExtremeEventForm{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
