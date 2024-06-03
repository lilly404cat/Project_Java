package org.example.server.services;

import org.example.server.entity.Department;
import org.example.server.entity.Medicine;
import org.example.server.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines() {
        try {
            return medicineRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all medicines " ,e);
        }
    }

    public Medicine getMedicineById(Integer id) {
        try{
            Optional<Medicine> medicine = medicineRepository.findById(id);
            return medicine.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting medicine by id" ,e);
        }
    }

    public Medicine createMedicine(Medicine medicine) {
        try{
            return medicineRepository.save(medicine);
        } catch (Exception e) {
            throw new RuntimeException("Error creating medicine " ,e);
        }
    }

    public Medicine updateMedicine(Integer id, Medicine medicineInfo) {
        try {
            Medicine medicine = medicineRepository.findById(id).
                    orElseThrow(() -> new RuntimeException("Medicine not found by id" + id));
            if (medicineInfo.getName() != null) {
                medicine.setName(medicineInfo.getName());
            }
            if (medicineInfo.getDescription() != null) {
                medicine.setDescription(medicineInfo.getDescription());
            }
            if (medicineInfo.getUnit() != null) {
                medicine.setUnit(medicineInfo.getUnit());
            }
            if (medicineInfo.getPricePerUnit() != null) {
                medicine.setPricePerUnit(medicineInfo.getPricePerUnit());
            }
            return medicineRepository.save(medicine);
        } catch (Exception e) {
            throw new RuntimeException("Error updating medicine " ,e);
        }
    }

    public void deleteMedicine(Integer id) {
        try {
            medicineRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting medicine " ,e);
        }
    }

    public Integer findByName(String name) {
        try {
            Medicine medicine = medicineRepository.findByName(name);
            return medicine != null ? medicine.getId() : null;
        } catch (Exception e) {
            throw new RuntimeException("Error getting department by name", e);
        }
    }
}
