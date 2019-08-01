package com.dragomircristian.extremeevents.repository;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import org.springframework.data.repository.CrudRepository;

public interface ExtremeEventsRepository extends CrudRepository<ExtremeEvent, Integer> {
}
