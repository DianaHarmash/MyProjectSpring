package com.example.myspringproject.repository;

import com.example.myspringproject.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPageRepository extends PagingAndSortingRepository<UserEntity, Long> {

}
