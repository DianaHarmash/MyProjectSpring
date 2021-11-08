package com.example.myspringproject.repository;

import com.example.myspringproject.model.ActivityEntity;
import com.example.myspringproject.model.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActivityPageRepository extends PagingAndSortingRepository<ActivityEntity, Long> {

}
