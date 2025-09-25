package edu.cit.cordova.johannanthony.campusequipmentload.Service;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.EquipmentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Entity.LoanEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Repository.EquipmentRepository;
import edu.cit.cordova.johannanthony.campusequipmentload.Repository.LoanRepository;
import edu.cit.cordova.johannanthony.campusequipmentload.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class LoanService {

    private final LoanRepository loanRepo;
    private final StudentRepository studentRepo;
    private final EquipmentRepository equipmentRepo;

    @Autowired
    public LoanService(LoanRepository loanRepo, StudentRepository studentRepo, EquipmentRepository equipmentRepo) {
        this.loanRepo = loanRepo;
        this.studentRepo = studentRepo;
        this.equipmentRepo = equipmentRepo;
    }


    public LoanEntity saveLoan(LoanEntity loan) {
        return loanRepo.save(loan);
    }


    public List<LoanEntity> getAllLoans() {
        return loanRepo.findAll();
    }

    public LoanEntity returnLoan(Long id, Map<String, String> body) {
        LoanEntity loan = loanRepo.findById(id).orElse(null);

        if (loan != null && !"RETURNED".equalsIgnoreCase(loan.getStatus())) {
            loan.setReturnDate(LocalDate.now());
            loan.setStatus("RETURNED");

            LocalDate dueDate = loan.getDueDate();
            LocalDate returnDate = loan.getReturnDate();

            LateFeeStrategy strategy = new DefaultLateFeeStrategy();
            double penalty = strategy.calculateLateFee(dueDate, returnDate);
            loan.setPenalty(penalty);

            return loanRepo.save(loan);
        }
        return loan;
    }
    public List<LoanEntity> getActiveLoans(Long studentId) {
        return loanRepo.findByStudentIdAndStatus(studentId, "ONGOING");
    }

    public ResponseEntity<?> getStudentLoanHistory(Long studentId) {
        if (!studentRepo.existsById(studentId)) {
            return ResponseEntity.badRequest().body("Student not found");
        }
        List<LoanEntity> loans = loanRepo.findByStudentId(studentId);
        return ResponseEntity.ok(Map.of("studentId", studentId, "loans", loans));
    }

    public List<LoanEntity> getLoansByStatus(String status) {
        return loanRepo.findByStatus(status);
    }

    public ResponseEntity<?> createLoan(Map<String, Object> loanRequest) {
        try {
            Map<String, Object> studentMap = (Map<String, Object>) loanRequest.get("student");
            Map<String, Object> equipmentMap = (Map<String, Object>) loanRequest.get("equipment");

            if (studentMap == null || equipmentMap == null) {
                return ResponseEntity.badRequest().body("Student and Equipment must be provided");
            }

            Long studentId = Long.valueOf(studentMap.get("id").toString());
            Long equipmentId = Long.valueOf(equipmentMap.get("id").toString());

            StudentEntity student = studentRepo.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            EquipmentEntity equipment = equipmentRepo.findById(equipmentId)
                    .orElseThrow(() -> new RuntimeException("Equipment not found"));

            LoanEntity loan = new LoanEntity();
            loan.setStudent(student);
            loan.setEquipment(equipment);

            LocalDate startDate = LocalDate.parse((String) loanRequest.get("startDate"));
            LocalDate dueDate = LocalDate.parse((String) loanRequest.get("dueDate"));
            loan.setStartDate(startDate);
            loan.setDueDate(dueDate);

            String returnDateStr = (String) loanRequest.get("returnDate");
            LocalDate returnDate = null;
            if (returnDateStr != null && !returnDateStr.isEmpty()) {
                returnDate = LocalDate.parse(returnDateStr);
                loan.setReturnDate(returnDate);
            }

            String status = (String) loanRequest.get("status");
            loan.setStatus(status);

            // Automatically calculate overdue and penalty
            boolean overdue = false;
            double penalty = 0.0;
            final int PENALTY_PER_DAY = 50;

            if (returnDate != null && returnDate.isAfter(dueDate)) {
                overdue = true;
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, returnDate);
                penalty = daysLate * PENALTY_PER_DAY;
            } else if (returnDate == null && LocalDate.now().isAfter(dueDate)) {
                // Loan still ongoing but past due date, mark overdue and calculate penalty till today
                overdue = true;
                long daysLate = java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
                penalty = daysLate * PENALTY_PER_DAY;
            }

            loan.setOverdue(overdue);
            loan.setPenalty(penalty);

            LoanEntity savedLoan = loanRepo.save(loan);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedLoan);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating loan: " + e.getMessage());
        }
    }

    // Late fee calculation strategy interface
    public interface LateFeeStrategy {
        double calculateLateFee(LocalDate dueDate, LocalDate returnDate);
    }

    // Default late fee strategy: $50 per day late
    public static class DefaultLateFeeStrategy implements LateFeeStrategy {
        private static final double DAILY_PENALTY = 50.0;

        @Override
        public double calculateLateFee(LocalDate dueDate, LocalDate returnDate) {
            if (returnDate == null || !returnDate.isAfter(dueDate)) {
                return 0.0;
            }
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            return daysLate * DAILY_PENALTY;
        }
    }

    public LoanEntity returnLoan(Long id, LocalDate returnDate) {
        LoanEntity loan = loanRepo.findById(id).orElse(null);

        if (loan == null || "RETURNED".equalsIgnoreCase(loan.getStatus())) {
            return null;
        }

        loan.setReturnDate(returnDate);
        loan.setStatus("RETURNED");

        LateFeeStrategy strategy = new DefaultLateFeeStrategy();
        double penalty = strategy.calculateLateFee(loan.getDueDate(), returnDate);
        loan.setPenalty(penalty);

        return loanRepo.save(loan);
    }
}
