package edu.cit.cordova.johannanthony.campusequipmentload.Repository;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Long> {
    EquipmentEntity findByName(String name);
    List<EquipmentEntity> findByAvailabilityTrue();
}