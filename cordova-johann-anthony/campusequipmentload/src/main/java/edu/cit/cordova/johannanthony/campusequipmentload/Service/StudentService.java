package edu.cit.cordova.johannanthony.campusequipmentload.Service;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studRepo;

    @Autowired
    public StudentService(StudentRepository studRepo) {
        this.studRepo = studRepo;
    }

    public StudentEntity postStudent(StudentEntity stud) {
        return studRepo.save(stud);
    }

    public StudentEntity getStudentById(Long id) {
        return studRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }
}