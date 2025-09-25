package edu.cit.cordova.johannanthony.campusequipmentload.Config;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Service.StudentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final StudentService studentService;

    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public StudentEntity register(@RequestBody StudentEntity student) {
        return studentService.registerStudent(student);
    }

    // login handled automatically by Spring Security at /api/auth/login

    @GetMapping("/me")
    public String me() {
        return "You are logged in as student!";
    }
}
