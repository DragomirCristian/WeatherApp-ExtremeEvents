package com.dragomircristian.extremeevents.repository;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExtremeEventsRepository extends CrudRepository<ExtremeEvent, Integer>, PagingAndSortingRepository<ExtremeEvent, Integer> {
}
