package com.dragomircristian.extremeevents.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class Comment {
    @Id
    private String id;
    private String textComment;
   // private  UserDetails userDetails;

}
