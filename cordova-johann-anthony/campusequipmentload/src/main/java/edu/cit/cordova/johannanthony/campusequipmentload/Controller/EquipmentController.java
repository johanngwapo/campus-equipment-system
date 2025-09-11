package edu.cit.cordova.johannanthony.campusequipmentload.Controller;

import edu.cit.cordova.johannanthony.campusequipmentload.Entity.EquipmentEntity;
import edu.cit.cordova.johannanthony.campusequipmentload.Service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/equipment")
public class EquipmentController {

    private final EquipmentService equipServ;

    @Autowired
    public EquipmentController(EquipmentService equipServ) {
        this.equipServ = equipServ;
    }

    @PostMapping
    public EquipmentEntity saveEquipment(@RequestBody EquipmentEntity equipment) {
        return equipServ.saveEquipment(equipment);
    }

    @GetMapping
    public List<EquipmentEntity> getAllEquipment() {
        return equipServ.getAllEquipment();
    }

    @GetMapping("/available")
    public List<EquipmentEntity> getAvailableEquipment() {
        return equipServ.getAvailableEquipment();
    }
}