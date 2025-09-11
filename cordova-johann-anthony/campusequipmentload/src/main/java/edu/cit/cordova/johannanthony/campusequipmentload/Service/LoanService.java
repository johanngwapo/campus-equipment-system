package edu.cit.cordova.johannanthony.campusequipmentload.Service;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.LoanEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepo;

    @Autowired
    public LoanService(LoanRepository loanRepo) {
        this.loanRepo = loanRepo;
    }

    public LoanEntity saveLoan(LoanEntity loan) {
        return loanRepo.save(loan);
    }

    public List<LoanEntity> getAllLoans() {
        return loanRepo.findAll();
    }

    public LoanEntity returnLoan(Long id) {
        LoanEntity loan = loanRepo.findById(id).orElse(null);

        if (loan != null && !"RETURNED".equalsIgnoreCase(loan.getStatus())) {
            loan.setReturnDate(new Date()); // set current date as return date
            loan.setStatus("RETURNED");
            return loanRepo.save(loan);
        }
        return loan; // null if not found, or already returned
    }
}