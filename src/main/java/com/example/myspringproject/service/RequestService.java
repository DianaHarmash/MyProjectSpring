package com.example.myspringproject.service;

import com.example.myspringproject.config.Encoder;
import com.example.myspringproject.model.*;
import com.example.myspringproject.repository.ActivityRepository;
import com.example.myspringproject.repository.RequestRepository;
import com.example.myspringproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.example.myspringproject.repository.UserActivityRepository;
import com.example.myspringproject.model.RequestsEntity;

import javax.transaction.Transactional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserActivityRepository userActivityRepository;

    @Transactional
    private void addActivityToUser(Long userId, Long activityId, String hours) {
        UserEntity user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        ActivityEntity activity = activityRepository.findById(activityId).orElseThrow(NoSuchElementException::new);
        UserActivityEntity userActivity = new UserActivityEntity(user.getId(), activity.getId(), hours);
        user.setActivity(activity);
        Set<UserEntity> users = activity.getUsers();
        users.add(user);
        userActivityRepository.save(userActivity);
        activityRepository.save(activity);
        userRepository.save(user);
    }

    private void editActivity(UserActivityEntity userActivity, String hours) {
        userActivityRepository.editActivity(hours, userActivity.getActivityId(), userActivity.getUserId());
    }

    @Transactional
    private void deleteActivity(UserActivityEntity userActivity, Long userId, Long activityId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        ActivityEntity activity = activityRepository.findById(activityId).orElseThrow(NoSuchElementException::new);
        List<ActivityEntity> activities = user.getActivities();
        activities.remove(activity);
        Set<UserEntity> users = activity.getUsers();
        users.remove(user);
        userActivityRepository.delete(userActivity);
        activityRepository.save(activity);
        userRepository.save(user);
    }

    @Transactional
    public void addRequest(Long userId, Long activityId, Type type) {
        if (!isRequestPresentInDB(userId, activityId, type)) {
            try {
                requestRepository.save(new RequestsEntity(userId, activityId, type));
            } catch (Throwable ex) {}
        }
    }

    private boolean isRequestPresentInDB(Long userId, Long activityId, Type type) {
        return requestRepository.findByUserIdAndActivityIdAndType(userId, activityId, type).isPresent();
    }

    private boolean isRequestPresentInDB(Long userId, Long activityId, String hours, Type type) {
        return requestRepository.findByUserIdAndActivityIdAndHoursAndType(userId, activityId, hours, type).isPresent();
    }

    @Transactional
    public void addRequest(Long userId, Long activityId, String hours, Type type) {
        if (!isRequestPresentInDB(userId, activityId, hours, type)) {
            try {
                requestRepository.save(new RequestsEntity(userId, activityId, hours, type));
            } catch(Throwable ex) {}
        }
    }

    public ArrayList<RequestsEntity> getAllRequests() {
        ArrayList<RequestsEntity> requestsEntities = new ArrayList<>();
        requestRepository.findAll().iterator().forEachRemaining(requestsEntities::add);
        return requestsEntities;
    }

    private RequestsEntity getRequest(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void allowRequest(Long requestId) {
        RequestsEntity requestEntity = getRequest(requestId);
        if (requestEntity.getType().equals(Type.ADD)) {
            addActivityToUser(requestEntity.getUserId(), requestEntity.getActivityId(), requestEntity.getHours());
        } else if (requestEntity.getType().equals(Type.EDIT)) {
            UserActivityEntity userActivity = userActivityRepository.findByUserIdAndActivityId(requestEntity.getUserId(), requestEntity.getActivityId()).orElseThrow(NoSuchElementException::new);
            editActivity(userActivity, requestEntity.getHours());
        } else {
            UserActivityEntity userActivity = userActivityRepository.findByUserIdAndActivityId(requestEntity.getUserId(), requestEntity.getActivityId()).orElseThrow(NoSuchElementException::new);
            deleteActivity(userActivity, requestEntity.getUserId(), requestEntity.getActivityId());
        }
        requestRepository.delete(requestEntity);
    }
}
