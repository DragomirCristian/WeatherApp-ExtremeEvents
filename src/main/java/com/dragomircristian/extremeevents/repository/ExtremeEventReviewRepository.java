package com.dragomircristian.extremeevents.repository;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.entities.ExtremeEventReview;
import org.apache.catalina.LifecycleState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExtremeEventReviewRepository extends MongoRepository<ExtremeEventReview, String> {
    List<ExtremeEventReview> findAllByExtremeEvent();
    ExtremeEventReview findByExtremeEvent(ExtremeEvent extremeEvent);
}
