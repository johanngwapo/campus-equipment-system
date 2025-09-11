package edu.cit.cordova.johannanthony.campusequipmentload.Controller;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studServ;

    @Autowired
    public StudentController(StudentService studServ) {
        this.studServ = studServ;
    }

    @PostMapping
    public StudentEntity postStudent(@RequestBody StudentEntity stud) {
        return studServ.postStudent(stud);
    }
}