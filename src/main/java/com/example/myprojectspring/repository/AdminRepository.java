package com.example.myprojectspring.repository;

import com.example.myprojectspring.model.AdminEntity;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<AdminEntity, Long> {
}
