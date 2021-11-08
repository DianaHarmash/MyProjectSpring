package com.example.myspringproject.repository;

import com.example.myspringproject.model.UserEntity;
import org.springframework.data.repository.CrudRepository;
import com.example.myspringproject.model.UserEntity;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByLoginAndPassword(String login, String password);

    Optional<UserEntity> findUserEntityByLogin(String login);
}
