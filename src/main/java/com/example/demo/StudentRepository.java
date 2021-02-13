package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

    @Transactional
    @Modifying // tells spring that we are just modifying data and NOT getting data back to map to an entity.
    @Query("DELETE FROM Student s WHERE s.id = ?1") // remember this is JPQL not SQL !!
    int deleteStudentById(Long id);
}
