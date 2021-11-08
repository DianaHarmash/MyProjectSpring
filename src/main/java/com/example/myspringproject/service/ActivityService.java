package com.example.myspringproject.service;

import com.example.myspringproject.model.ActivityEntity;
import com.example.myspringproject.model.UserEntity;
import com.example.myspringproject.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.*;
import com.example.myspringproject.controller.MainController;

import javax.transaction.Transactional;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    public Optional<ActivityEntity> saveActivity(String name, String category) {
        try {
            ActivityEntity activity = new ActivityEntity(name, category);
            activityRepository.save(activity);
            return Optional.of(activity);
        } catch (Throwable ex) {
            MainController.logger.log(Level.WARNING, ex.getMessage());
        }
        return null;
    }

    @Transactional
    public void deleteActivityFromUser(Long id) {
        ActivityEntity activity =  activityRepository.findById(id).orElseThrow(NoSuchElementException::new);
        activity.getUsers().forEach(user -> user.getActivities().remove(activity));
    }

    @Transactional
    public void deleteActivity(Long id) {
        deleteActivityFromUser(id);
        activityRepository.delete(activityRepository.findById(id).orElseThrow(NoSuchElementException::new));
    }

    public ActivityEntity getActivity(Long id) {
        return activityRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void updateActivity(Long id, String name, String category) {
        //activityRepository.updateActivity(name, category, id);
        ActivityEntity activity = activityRepository.findById(id).orElseThrow(NoSuchElementException::new);
        activity.setName(name);
        activity.setCategory(category);
        activityRepository.save(activity);
    }

    public List<ActivityEntity> getWholeActivities() {
        return (List) activityRepository.findAll();
    }
}
