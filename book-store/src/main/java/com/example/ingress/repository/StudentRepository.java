package com.example.ingress.repository;

import com.example.ingress.domain.StudentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    @Query("SELECT s FROM StudentEntity s JOIN s.bookEntities b WHERE b.id = :bookId")
    List<StudentEntity> getStudentsWithBookId(Long bookId);

    StudentEntity findStudentByEmail(String email);
}
