package com.example.myspringproject.repository;

import com.example.myspringproject.model.ActivityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.myspringproject.model.RequestsEntity;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import com.example.myspringproject.model.Type;

public interface RequestRepository extends CrudRepository<RequestsEntity, Long> {

    Optional<RequestsEntity> findByUserIdAndActivityIdAndHoursAndType(Long userId, Long activityId, String hours, Type type);

    Optional<RequestsEntity> findByUserIdAndActivityIdAndType(Long userId, Long activityId, Type type);


    @Query(value = "select r.id, r.user_id, r.activity_id, r.hours, r.type, u.login, a.name as activity_name from requests_entity r join user_entity u on u.id = r.user_id join activity_entity a on a.id = r.activity_id", nativeQuery = true)
    List<RequestsEntity> findAllJoin();
}
