package edu.cit.cordova.johannanthony.campusequipmentload.Service;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.EquipmentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipRepo;

    @Autowired
    public EquipmentService(EquipmentRepository equipRepo) {
        this.equipRepo = equipRepo;
    }

    public EquipmentEntity saveEquipment(EquipmentEntity equipment) {
        return equipRepo.save(equipment);
    }

    public List<EquipmentEntity> getAllEquipment() {
        return equipRepo.findAll();
    }

    public List<EquipmentEntity> getAvailableEquipment() {
        return equipRepo.findByAvailabilityTrue();
    }
}