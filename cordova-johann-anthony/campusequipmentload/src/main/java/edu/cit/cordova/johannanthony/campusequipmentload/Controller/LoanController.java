package edu.cit.cordova.johannanthony.campusequipmentload.Controller;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.LoanEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanServ;

    @Autowired
    public LoanController(LoanService loanServ) {
        this.loanServ = loanServ;
    }

    @PostMapping
    public LoanEntity saveLoan(@RequestBody LoanEntity loan) {
        return loanServ.saveLoan(loan);
    }

    @GetMapping
    public List<LoanEntity> getAllLoans() {
        return loanServ.getAllLoans();
    }

    @PostMapping("/{id}/return")
    public LoanEntity returnLoan(@PathVariable Long id) {
        return loanServ.returnLoan(id);
    }
}