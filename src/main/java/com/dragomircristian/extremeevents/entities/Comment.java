package com.dragomircristian.extremeevents.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "comments")
@Setter
@Getter
public class Comment {
    @Id
    private String id;
    private String textComment;
  
    public Comment(String textComment) {
        this.textComment = textComment;
    }
}
