package com.example.myspringproject.service;

import com.example.myspringproject.config.Encoder;
import com.example.myspringproject.model.Role;
import com.example.myspringproject.model.UserActivityEntity;
import com.example.myspringproject.model.UserEntity;
import com.example.myspringproject.repository.UserActivityRepository;
import com.example.myspringproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.Optional;
import java.util.Set;

@Service
public class UserActivityService {

    @Autowired
    private UserActivityRepository userActivityRepository;

    public UserActivityEntity findByUserIdAndActivityId(Long userId, Long activityId) {
        return userActivityRepository.findByUserIdAndActivityId(userId, activityId).orElseThrow(IllegalArgumentException::new);
    }

    public Set<UserActivityEntity> findByUserId(Long userId) {
        return userActivityRepository.findAllByUserId(userId);
    }

}
