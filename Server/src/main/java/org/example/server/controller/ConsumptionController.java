package org.example.server.controller;

import org.example.server.entity.Consumption;
import org.example.server.services.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospital_stocks/consumptions")
public class ConsumptionController {
    @Autowired
    private ConsumptionService consumptionService;

    @GetMapping
    public ResponseEntity<List<Consumption>> getConsumptions() {
        try {
            List<Consumption> consumptions = consumptionService.getAllConsumptions();
            return ResponseEntity.ok(consumptions);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumption> getConsumptionById(@PathVariable Integer id) {
        try {
            Consumption consumption = consumptionService.getConsumptionById(id);
            if (consumption != null) {
                return ResponseEntity.ok(consumption);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Consumption> createConsumption(@RequestBody Consumption consumption) {
        try {
            Consumption consumptionCreated = consumptionService.createConsumption(consumption);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consumption> updateConsumption(@PathVariable Integer id, @RequestBody Consumption consumption) {
        try {
            Consumption consumptionUpdated = consumptionService.updateConsumption(id, consumption);
            return ResponseEntity.status(202).build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumption(@PathVariable Integer id) {
        try {
            consumptionService.deleteConsumption(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
