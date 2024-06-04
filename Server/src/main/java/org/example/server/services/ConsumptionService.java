package org.example.server.services;

import org.example.server.entity.Consumption;
import org.example.server.repository.ConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsumptionService {

    @Autowired
    private ConsumptionRepository consumptionRepository;

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

    public List<String> getMostConsumedMedicinesLastWeek(Integer departmentId) {
        List<Consumption> consumptionLastWeek = findConsumptionLastWeek(departmentId);

        Map<String, Integer> totalConsumptionMap = new HashMap<>();
        for (Consumption consumption : consumptionLastWeek) {
            String medicineName = consumption.getMedicine().getName();
            totalConsumptionMap.put(medicineName, totalConsumptionMap.getOrDefault(medicineName, 0) + consumption.getQuantity());
        }

        List<String> mostConsumedMedicinesLastWeek = totalConsumptionMap.entrySet().stream()
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue()))
                .map(entry -> entry.getKey() + ": " + entry.getValue() + " units")
                .collect(Collectors.toList());

        return mostConsumedMedicinesLastWeek;
    }

    public List<Consumption> findConsumptionLastWeek(Integer departmentId) {
        LocalDate startDate = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);

        LocalDate endDate = LocalDate.now().minusWeeks(1).with(DayOfWeek.SUNDAY);

        return consumptionRepository.findByDepartmentIdAndConsumptionDateBetween(departmentId, startDate, endDate);
    }

    public Integer findConsumptionIdsByMedicineAndDepartment(String medicineName, String departmentName) {
        return consumptionRepository.findIdsByMedicineNameAndDepartmentName(medicineName, departmentName);
    }
}
