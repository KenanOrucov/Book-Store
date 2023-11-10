package com.example.ingress.repository;

import com.example.ingress.domain.StudentEntity;
import com.example.ingress.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserEntityByEmail(String email);
}
