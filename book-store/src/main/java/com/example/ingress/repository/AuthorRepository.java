package com.example.ingress.repository;

import com.example.ingress.domain.AuthorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

}
