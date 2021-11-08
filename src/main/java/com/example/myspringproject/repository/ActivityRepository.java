package com.example.myspringproject.repository;

import com.example.myspringproject.model.ActivityEntity;
import com.example.myspringproject.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<ActivityEntity, Long> {

    @Query(value = "update activity_entity set name = ?1, category = ?2 where id = ?3", nativeQuery = true)
    void updateActivity(String name, String category, Long id);
}
