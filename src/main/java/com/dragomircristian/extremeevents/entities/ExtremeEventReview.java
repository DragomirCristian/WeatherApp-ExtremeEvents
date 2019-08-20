package com.dragomircristian.extremeevents.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class ExtremeEventReview {
    @Id
    private String id;
    private int like;
    private int dislike;
    private Comment comments;
    private ExtremeEvent extremeEvent;
}
