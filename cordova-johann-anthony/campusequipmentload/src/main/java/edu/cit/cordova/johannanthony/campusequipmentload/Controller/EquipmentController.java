package edu.cit.cordova.johannanthony.campusequipmentload.Controller;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.EquipmentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    // Create new equipment
    @PostMapping
    public EquipmentEntity addEquipment(@RequestBody EquipmentEntity equipment) {
        return equipmentService.saveEquipment(equipment);
    }

    // Get all equipment
    @GetMapping
    public List<EquipmentEntity> getAllEquipment() {
        return equipmentService.getAllEquipment();
    }

    // Get available equipment
    @GetMapping("/available")
    public List<EquipmentEntity> getAvailableEquipment() {
        return equipmentService.getAvailableEquipment();
    }
}
