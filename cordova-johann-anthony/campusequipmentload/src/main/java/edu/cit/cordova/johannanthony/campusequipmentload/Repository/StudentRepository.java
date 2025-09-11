package edu.cit.cordova.johannanthony.campusequipmentload.Repository;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    StudentEntity findByName(String name);
}