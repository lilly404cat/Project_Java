package org.example.server.services;

import org.example.server.entity.Consumption;
import org.example.server.entity.Stock;
import org.example.server.repository.ConsumptionRepository;
import org.example.server.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConsumptionService {

    @Autowired
    private ConsumptionRepository consumptionRepository;
    @Autowired
    private StockService stockService;


    public List<Consumption> getAllConsumptions() {
        try {
            return consumptionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all consumptions", e);
        }
    }

    public Consumption getConsumptionById(Integer id) {
        try {
            Optional<Consumption> consumption = consumptionRepository.findById(id);
            return consumption.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting consumption by id", e);
        }
    }

    public Consumption createConsumption(Consumption consumption) {
        try {
            return consumptionRepository.save(consumption);
        } catch (Exception e) {
            throw new RuntimeException("Error creating consumption", e);
        }
    }

    public Consumption updateConsumption(Integer id, Integer quantity) {
        try {
            Consumption consumption = consumptionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Consumption not found by id " + id));

            if (quantity != null) {
                consumption.setQuantity(quantity);
                consumption.setConsumptionDate(LocalDate.now());
            }
            return consumptionRepository.save(consumption);
        } catch (Exception e) {
            throw new RuntimeException("Error updating consumption", e);
        }
    }

    public void deleteConsumption(Integer id) {
        try {
            consumptionRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting consumption", e);
        }
    }

    /**
     * Returns the most consumed medicines in the last week
     * @param departmentId the id of the department
     * @return Map
     */
    public Map<String, Integer> getMostConsumedMedicinesByDepartment(Integer departmentId) {
        List<Consumption> consumptions = consumptionRepository.findByDepartmentId(departmentId);

        Map<String, Integer> totalConsumptionMap = new HashMap<>();
        for (Consumption consumption : consumptions) {
            String medicineName = consumption.getMedicine().getName();
            totalConsumptionMap.put(medicineName, totalConsumptionMap.getOrDefault(medicineName, 0) + consumption.getQuantity());
        }

        return totalConsumptionMap.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Method that returns the medicines that will run out sooner
     * @param departmentId the id of the department
     * @return Map
     */
    public Map<String, Integer> getMostConsumedAndLowStockMedicines(Integer departmentId) {
        Map<String, Integer> mostConsumedMedicines = getMostConsumedMedicinesByDepartment(departmentId);
        List<Consumption> allConsumptions = consumptionRepository.findByDepartmentId(departmentId);

        Map<String, Integer> stockMap = new HashMap<>();
        for (Consumption consumption : allConsumptions) {
            String medicineName = consumption.getMedicine().getName();
            stockMap.put(medicineName, stockMap.getOrDefault(medicineName, 0) + consumption.getQuantity());
        }

        Map<String, Integer> predictionMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : mostConsumedMedicines.entrySet()) {
            String medicineName = entry.getKey();
            Integer consumedQuantity = entry.getValue();
            Integer stockQuantity = stockMap.getOrDefault(medicineName, 0);

            if (stockQuantity <= consumedQuantity) {
                predictionMap.put(medicineName, stockQuantity);
            }
        }

        return predictionMap;
    }

    /**
     * Estimates the number of days until the stocks will run out
     * @param departmentId the id of the department
     * @return Map
     */
    public Map<String, Integer> getMedicinesRunningOutSoon(Integer departmentId) {

        List<Consumption> allConsumptions = consumptionRepository.findByDepartmentId(departmentId);


        Map<String, Integer> stockMap = stockService.getMedicineQuantitiesByDepartment(departmentId);

        Map<String, Integer> totalConsumptionLastWeek = new HashMap<>();
        for (Consumption consumption : allConsumptions) {

            if (consumption.getConsumptionDate().isAfter(LocalDate.now().minusDays(7))) {
                String medicineName = consumption.getMedicine().getName();
                totalConsumptionLastWeek.put(medicineName, totalConsumptionLastWeek.getOrDefault(medicineName, 0) + consumption.getQuantity());
            }
        }

        Map<String, Double> avgDailyConsumptionMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : totalConsumptionLastWeek.entrySet()) {
            String medicineName = entry.getKey();
            Integer totalConsumption = entry.getValue();
            Double avgDailyConsumption = totalConsumption / 7.0;
            avgDailyConsumptionMap.put(medicineName, avgDailyConsumption);
        }

        Map<String, Integer> predictionMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : stockMap.entrySet()) {
            String medicineName = entry.getKey();
            Integer stockQuantity = entry.getValue();
            Double avgDailyConsumption = avgDailyConsumptionMap.getOrDefault(medicineName, 0.0);

            int daysToRunOut;
            if (avgDailyConsumption > 0) {
                daysToRunOut = (int) Math.ceil(stockQuantity / avgDailyConsumption);
            } else {
                daysToRunOut = 7;
            }
            predictionMap.put(medicineName, daysToRunOut);
        }

        return predictionMap;
    }

    public Integer findConsumptionIdsByMedicineAndDepartment(String medicineName, String departmentName) {
        return consumptionRepository.findIdsByMedicineNameAndDepartmentName(medicineName, departmentName);
    }
}
