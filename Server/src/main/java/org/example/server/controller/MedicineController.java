package org.example.server.controller;

import org.example.server.entity.Medicine;
import org.example.server.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital_stocks/medicines")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    /**
     * method to get all the medicines
     * @return response
     */
    @GetMapping
    public ResponseEntity<List<Medicine>> getMedicines() {
        try {
            List<Medicine> medicines = medicineService.getAllMedicines();
            return ResponseEntity.ok(medicines);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to get a medicine by id
     * @param id to be used in request
     * @return response
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Integer id) {
        try{
            Medicine medicine = medicineService.getMedicineById(id);
            if(medicine != null) {
                return ResponseEntity.ok(medicine);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to create new medicine
     * @param medicine to be added in the database
     * @return response
     */
    @PostMapping
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) {
        try {
            System.out.println(medicine);
            Medicine medicineCreated = medicineService.createMedicine(medicine);
            return  ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to updateMedicine
     * @param id to be used in the update
     * @param medicine to be put at the id id
     * @return response
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Integer id, @RequestBody Medicine medicine) {
        try {
            Medicine medicineUpdated = medicineService.updateMedicine(id, medicine);
            return  ResponseEntity.status(202).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * delete by name request
     * @param name to be used in  the request to get the data from database
     * @return response
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Medicine> deleteMedicine(@PathVariable String name) {
        try {

            Integer id = medicineService.findByName(name);
            System.out.println(id);
            if (id != null) {
                medicineService.deleteMedicine(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
