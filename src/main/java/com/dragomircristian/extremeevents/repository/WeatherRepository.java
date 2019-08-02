package com.dragomircristian.extremeevents.repository;

import com.dragomircristian.extremeevents.entities.Weather;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<Weather, String> {
}
