package edu.cit.cordova.johannanthony.campusequipmentload.Repository;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByEmail(String email);
    List<StudentEntity> findByName(String name);
}