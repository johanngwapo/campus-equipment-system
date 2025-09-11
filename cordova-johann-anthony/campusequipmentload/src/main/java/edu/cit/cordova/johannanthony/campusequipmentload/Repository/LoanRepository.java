package edu.cit.cordova.johannanthony.campusequipmentload.Repository;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    // Find all ongoing loans
    List<LoanEntity> findByStatus(String status);

    // Find all loans by student
    List<LoanEntity> findByStudentStudentId(Long studentId);
}