package com.dragomircristian.extremeevents.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class ExtremeEventReview {
    @Id
    private String id;
    private ExtremeEvent extremeEvent;
    private int likes;
    private int dislikes;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments;

    public ExtremeEventReview(ExtremeEvent extremeEvent) {
        this.extremeEvent = extremeEvent;
        this.comments = new ArrayList<>();
        this.likes = 0;
        this.dislikes = 0;
    }
}
