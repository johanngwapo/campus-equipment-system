package edu.cit.cordova.johannanthony.campusequipmentload.Service;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class StudentService {
    private final StudentRepository studRepo;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studRepo, PasswordEncoder passwordEncoder) {
        this.studRepo = studRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // register student
    public StudentEntity registerStudent(StudentEntity student) {
        student.setPassword(passwordEncoder.encode(student.getPassword())); // hash password
        return studRepo.save(student);
    }

    // get student by id
    public StudentEntity getStudentById(Long id) {
        return studRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    // find by email (used by Spring Security during login)
    public StudentEntity getByEmail(String email) {
        return studRepo.findByEmail(email)
                .orElseThrow(() ->   new RuntimeException("Student not found with email " + email));
    }
}