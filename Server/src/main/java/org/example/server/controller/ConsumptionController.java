package org.example.server.controller;

import org.example.server.entity.Consumption;
import org.example.server.services.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/department/{departmentId}/most-consumed")
    public ResponseEntity<Map<String, Integer>> getMostConsumedMedicinesByDepartment(@PathVariable Integer departmentId) {
        try {
            Map<String, Integer> mostConsumedMedicines = consumptionService.getMostConsumedMedicinesByDepartment(departmentId);
            return ResponseEntity.ok(mostConsumedMedicines);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/department/{departmentId}/mostConsumedLowStock")
    public ResponseEntity<Map<String, Integer>> getMostConsumedAndLowStockMedicines(@PathVariable Integer departmentId) {
        try {
            Map<String, Integer> prediction = consumptionService.getMostConsumedAndLowStockMedicines(departmentId);
            return ResponseEntity.ok(prediction);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/department/{departmentId}/running-out-soon")
    public ResponseEntity<Map<String, Integer>> getMedicinesRunningOutSoon(@PathVariable Integer departmentId) {
        try {
            Map<String, Integer> prediction = consumptionService.getMedicinesRunningOutSoon(departmentId);
            return ResponseEntity.ok(prediction);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
