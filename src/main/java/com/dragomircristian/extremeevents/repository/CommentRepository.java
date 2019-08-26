package com.dragomircristian.extremeevents.repository;

import com.dragomircristian.extremeevents.entities.Comment;
import com.google.type.MoneyOrBuilder;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
