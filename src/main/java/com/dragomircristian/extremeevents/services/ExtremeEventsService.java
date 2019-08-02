package com.dragomircristian.extremeevents.services;

import com.dragomircristian.extremeevents.entities.ExtremeEvent;
import com.dragomircristian.extremeevents.repository.ExtremeEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ExtremeEventsService {
    @Autowired
    ExtremeEventsRepository extremeEventsRepository;

    public ExtremeEvent getExtremeEventById(Integer id) {
        return extremeEventsRepository.findById(id).get();
    }

    public ArrayList<ExtremeEvent> getAllExtremeEvents() {
        ArrayList<ExtremeEvent> list = new ArrayList<>();
        for (ExtremeEvent extremeEvent : extremeEventsRepository.findAll()) {
            list.add(extremeEvent);
        }
        return list;
    }

    public void deleteExtremeEventById(int id) {
        extremeEventsRepository.deleteById(id);
    }

    public void updateExtremeEvent(ExtremeEvent student) {
        this.extremeEventsRepository.save(student);
    }

    public void insertExtremeEvent(ExtremeEvent student) {
        this.extremeEventsRepository.save(student);
    }

    public boolean exists(int id) {
        return extremeEventsRepository.findById(id).isPresent();
    }

}
