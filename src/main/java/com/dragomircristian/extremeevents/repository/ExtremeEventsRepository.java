package com.dragomircristian.extremeevents.repository;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExtremeEventsRepository extends PagingAndSortingRepository<ExtremeEvent, String> {

    List<ExtremeEvent> findAllByCountry(String country, Pageable pageable);
    List<ExtremeEvent> findAllByCounty(String county, Pageable pageable);
}
