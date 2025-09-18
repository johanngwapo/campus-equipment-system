package edu.cit.cordova.johannanthony.campusequipmentload.Repository;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.LoanEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    List<LoanEntity> findByStatus(String status);

    List<LoanEntity> findByStudentId(Long id);
    List<LoanEntity> findByStudentIdAndStatus(Long studentId, String status); // ✅ FIXED

    Optional<LoanEntity> findByStudentStudentNoAndEquipmentEquipmentIdAndStatus(String studentNo, Long equipmentId, String status);

    long countByStudentAndStatus(StudentEntity student, String status);
}

