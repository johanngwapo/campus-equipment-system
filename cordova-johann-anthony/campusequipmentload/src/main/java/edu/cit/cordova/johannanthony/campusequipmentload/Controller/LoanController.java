package edu.cit.cordova.johannanthony.campusequipmentload.Controller;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.EquipmentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Entity.LoanEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Repository.StudentRepository;
import edu.cit.cordova.johannanthony.campusequipmentload.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanServ;


    @Autowired
    public LoanController(LoanService loanServ) {
        this.loanServ = loanServ;
    }

    // Create a loan - pass in a custom DTO or LoanEntity
    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Map<String, Object> loanRequest) {
        return loanServ.createLoan(loanRequest);
    }

    // Get all loans
    @GetMapping
    public List<LoanEntity> getAllLoans() {
        return loanServ.getAllLoans();
    }

    // Return a loan by id, optionally passing return date in body
    @PostMapping("/{id}/return")
    public ResponseEntity<?> returnLoan(@PathVariable Long id, @RequestBody(required = false) Map<String, String> body) {
        LocalDate returnDate = LocalDate.now();

        if (body != null && body.containsKey("returnDate")) {
            try {
                returnDate = LocalDate.parse(body.get("returnDate"));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Invalid returnDate format, expected yyyy-MM-dd");
            }
        }

        LoanEntity updatedLoan = loanServ.returnLoan(id, returnDate);

        if (updatedLoan == null) {
            return ResponseEntity.badRequest().body("Loan not found or already returned.");
        }

        return ResponseEntity.ok(updatedLoan);
    }

    // Optional: get loans by status
    @GetMapping("/status/{status}")
    public List<LoanEntity> getLoansByStatus(@PathVariable String status) {
        return loanServ.getLoansByStatus(status);
    }

    // Optional: get loan history for a student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getStudentLoanHistory(@PathVariable Long studentId) {
        return loanServ.getStudentLoanHistory(studentId);
    }
}
