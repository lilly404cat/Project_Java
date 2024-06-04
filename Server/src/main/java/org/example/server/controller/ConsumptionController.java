package org.example.server.controller;

import org.example.server.entity.Consumption;
import org.example.server.services.ConsumptionService;
import org.example.server.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/hospital_stocks/consumptions")
public class ConsumptionController {
    @Autowired
    private ConsumptionService consumptionService;
    @Autowired
    private StockService stockService;

    private Integer stockId;

    /**
     * get request to get all the consumption
     * @return response
     */
    @GetMapping
    public ResponseEntity<List<Consumption>> getConsumptions() {
        try {
            List<Consumption> consumptions = consumptionService.getAllConsumptions();
            return ResponseEntity.ok(consumptions);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to get a consumption by id
     * @param id of the rquest
     * @return response
     */
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

    /**
     * post request
     * @param consumption the consumption to be set in the database
     * @return response
     */
    @PostMapping
    public ResponseEntity<Consumption> createConsumption(@RequestBody Consumption consumption) {
        try {
            Consumption consumptionCreated = consumptionService.createConsumption(consumption);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * delete request from the database
     * @param id
     * @return
     */
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

    /**
     * get request
     * @param departmentId from the rquest
     * @return response
     */
    @GetMapping("/department/{departmentId}/most-consumed")
    public ResponseEntity<Map<String, Integer>> getMostConsumedMedicinesByDepartment(@PathVariable Integer departmentId) {
        try {
            Map<String, Integer> mostConsumedMedicines = consumptionService.getMostConsumedMedicinesByDepartment(departmentId);
            return ResponseEntity.ok(mostConsumedMedicines);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * get request for the consumption based on 2 params
     * @param medicine to be used in the query
     * @param department to be used in the query
     * @return response
     */
    @GetMapping("/findIdsByMedicineAndDepartment")
    public ResponseEntity<Integer> getConsumptionIdsByMedicineAndDepartment(
            @RequestParam String medicine,
            @RequestParam String department
    ) {
        try {
            System.out.println(department + " " + medicine);
            Integer consumptionIds = consumptionService.findConsumptionIdsByMedicineAndDepartment(medicine, department);

            return ResponseEntity.ok(consumptionIds);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * put request mapping to update the consumption quantity and date (in service classs)
     * @param id from the query
     * @param quantity to be set
     * @return response
     */
    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<Consumption> updateConsumptionQuantity(
            @PathVariable Integer id,
            @RequestParam Integer quantity
    ) {
        try {
            System.out.println(id +  quantity);
            Consumption updatedConsumption = consumptionService.updateConsumption(id, quantity);
            return ResponseEntity.ok(updatedConsumption);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * method for the get request for the most consumed and low stock
     * @param departmentId to be searched by
     * @return response of request
     */
    @GetMapping("/department/{departmentId}/mostConsumedLowStock")
    public ResponseEntity<Map<String, Integer>> getMostConsumedAndLowStockMedicines(@PathVariable Integer departmentId) {
        try {
            Map<String, Integer> prediction = consumptionService.getMostConsumedAndLowStockMedicines(departmentId);
            return ResponseEntity.ok(prediction);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * method to get request the medicines running out soon
     * @param departmentId to be searched by
     * @return response of thew request
     */
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
