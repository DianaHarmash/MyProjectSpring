package com.example.myspringproject.repository;

import com.example.myspringproject.model.UserActivityEntity;
import com.example.myspringproject.model.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserActivityRepository extends CrudRepository<UserActivityEntity, Long> {

    Optional<UserActivityEntity> findByUserIdAndActivityId(Long userId, Long activityId);

    Set<UserActivityEntity> findAllByUserId(Long userId);

//    @Modifying
//    @Query(value = "update user_activity_entity set hours = :hours where uae.activity_id = :activity_id and uae.user_id = :user_id", nativeQuery = true)
//    void editActivity(@Param("hours") String hours,@Param("activity_id") Long activity_id, @Param("user_id") Long user_id);
    @Modifying
    @Query(value = "update user_activity_entity uae set hours = :hours where uae.activity_id = :activity_id and uae.user_id = :user_id", nativeQuery = true)
    void editActivity(@Param("hours") String hours,@Param("activity_id") Long activity_id, @Param("user_id") Long user_id);


}
