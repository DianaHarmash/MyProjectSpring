package com.example.myspringproject.repository;

import com.example.myspringproject.model.ActivityEntity;
import org.springframework.data.repository.CrudRepository;
import com.example.myspringproject.model.RequestsEntity;
import java.util.Optional;
import com.example.myspringproject.model.Type;

public interface RequestRepository extends CrudRepository<RequestsEntity, Long> {

    Optional<RequestsEntity> findByUserIdAndActivityIdAndHoursAndType(Long userId, Long activityId, String hours, Type type);

    Optional<RequestsEntity> findByUserIdAndActivityIdAndType(Long userId, Long activityId, Type type);

}
