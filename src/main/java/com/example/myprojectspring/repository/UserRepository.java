package com.example.myprojectspring.repository;

import com.example.myprojectspring.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
